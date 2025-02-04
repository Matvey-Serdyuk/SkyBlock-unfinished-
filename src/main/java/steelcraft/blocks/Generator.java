package steelcraft.blocks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import steelcraft.blocks.data.GeneratorData;
import steelcraft.economy.GeneratorCost;
import steelcraft.main.Main;
import steelcraft.main.worlds.MainWorld;
import steelcraft.menu.ActionMenu.GeneratorMenu;
import steelcraft.Island.Area;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.utils.ItemTools;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Generator {
    final public static int MAX_LEVEL = 5;

    final public static GeneratorCost[] cost = {
            new GeneratorCost(250, 500),
            new GeneratorCost(750, 1500),
            new GeneratorCost(2500, 5000),
            new GeneratorCost(6500, 10000),
            new GeneratorCost(25000, 25000)};

    final public static List<HashMap<Material, Float>> materials = Arrays.asList(new HashMap<Material, Float>() {{
        put(Material.COAL_ORE, 2.5f);
        put(Material.STONE, 97.5f);
    }}, new HashMap<Material, Float>() {{
        put(Material.COAL_ORE, 4f);
        put(Material.IRON_ORE, 0.5f);
        put(Material.STONE, 95.5f);
    }}, new HashMap<Material, Float>() {{
        put(Material.COAL_ORE, 6f);
        put(Material.IRON_ORE, 1f);
        put(Material.GOLD_ORE, 0.25f);
        put(Material.LAPIS_ORE, 0.5f);
        put(Material.REDSTONE_ORE, 0.25f);
        put(Material.STONE, 92f);
    }}, new HashMap<Material, Float>() {{
        put(Material.COAL_ORE, 6f);
        put(Material.IRON_ORE, 1.5f);
        put(Material.GOLD_ORE, 0.5f);
        put(Material.LAPIS_ORE, 1f);
        put(Material.REDSTONE_ORE, 0.5f);
        put(Material.DIAMOND_ORE, 0.15f);
        put(Material.EMERALD_ORE, 0.15f);
        put(Material.STONE, 90.2f);
    }}, new HashMap<Material, Float>() {{
        put(Material.COAL_ORE, 6f);
        put(Material.IRON_ORE, 2f);
        put(Material.GOLD_ORE, 0.75f);
        put(Material.LAPIS_ORE, 1.5f);
        put(Material.REDSTONE_ORE, 0.75f);
        put(Material.DIAMOND_ORE, 0.3f);
        put(Material.EMERALD_ORE, 0.3f);
        put(Material.STONE, 88.4f);
    }}, new HashMap<Material, Float>() {{
        put(Material.COAL_ORE, 6f);
        put(Material.IRON_ORE, 2f);
        put(Material.GOLD_ORE, 1f);
        put(Material.LAPIS_ORE, 2f);
        put(Material.REDSTONE_ORE, 1f);
        put(Material.DIAMOND_ORE, 0.5f);
        put(Material.EMERALD_ORE, 0.5f);
        put(Material.STONE, 87f);
    }});

    public static String symbol = ChatColor.BLACK + "Generator";
    public Area area;
    public Location location;
    public ItemStack item;

    public int level;

    public boolean isWork;

    public Generator(Area area, Location location, ItemStack item) {
        this.area = area;
        this.location = location;
        this.item = new ItemStack(item);
        this.item.setAmount(1);
        isWork = true;

        assert item.getItemMeta() != null;
        List <String> lore = item.getItemMeta().getLore();
        level = Integer.parseInt(lore.get(0).split(" ")[1]);
    }

    public Generator(GeneratorData data) {
        Block block = MainWorld.world.getBlockAt(data.location.getLocation());
        area = getArea(block);
        location = block.getLocation();
        isWork = data.isWork;
        level = data.level;
        item = getItem(this);
    }

    public void update() {
        for (Block block : area.blocks) {
            if (block.getType() == Material.AIR) {
                block.setType(Material.STONE);
            }
        }
    }

    public static boolean checkBlock(SkyPlayer player, BlockBreakEvent event) {
        Location location = event.getBlock().getLocation();
        for (Generator generator : player.islandPlayer.island.generators) {
            if (!generator.isWork) {continue;}
            if (generator.area.checkBlock(location)) {
                Material material = Material.AIR;
                float rand = (float) Math.random() * 100;
                float curChance = 0;
                for (Material m : materials.get(generator.level).keySet()) {
                    curChance += materials.get(generator.level).get(m);
                    if (curChance >= rand) {
                        material = m;
                        break;
                    }
                }
                final Material mat = material;

                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                    MainWorld.world.getBlockAt(location).setType(mat);
                }, 0);

                player.generatorBlocks++;
                return true;
            }
        }
        return false;
    }

    public static boolean blockBreak(SkyPlayer player, BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.PLAYER_HEAD) {return false;};
        for (Generator generator : player.islandPlayer.island.generators) {
            if (event.getBlock().getLocation().equals(generator.location)) {
                player.islandPlayer.island.generators.remove(generator);
                event.setDropItems(false);
                MainWorld.world.dropItem(generator.location, getItem(generator));
                return true;
            }
        }
        return false;
    }

    public static boolean blockPlace(SkyPlayer player, BlockPlaceEvent event, boolean haveLore) {
        if (!haveLore) {return false;}
        if (event.getBlock().getType() != Material.PLAYER_HEAD) {return false;};
        List <String> lore = event.getItemInHand().getItemMeta().getLore();
        if (!lore.get(lore.size()-1).equals(symbol)) {return false;}


        Area area = getArea(event.getBlock());

        if (!player.islandPlayer.island.area.checkArea(area)) {
            player.sendMessage(ChatColor.RED + "Площадь действия генератора выходит за ваш остров!", true);
            event.setCancelled(true);
            return true;
        }

        for (Block block : area.blocks) {
            if (block.getType() != Material.AIR) {
                player.sendMessage(ChatColor.RED + "Площадь действия генератора задевает посторонние блоки!", true);
                event.setCancelled(true);
                return true;
            }
        }

        Generator generator = new Generator(area, event.getBlock().getLocation(), event.getItemInHand());

        player.islandPlayer.island.generators.add(generator);

        generator.update();

        return true;
    }

    public static Area getArea(Block block) {
        Skull skull = (Skull) block.getState();
        SkullMeta meta;
        BlockFace face = skull.getRotation();
        int x = 0;
        int z = 0;
        BlockData data = block.getBlockData();
        if (Math.abs(face.getModX()) >= Math.abs(face.getModZ())) {
            if (face.getModX() < 0) {
                x = -1;
                skull.setRotation(BlockFace.EAST);
            } else {
                x = 1;
                skull.setRotation(BlockFace.WEST);
            }
        } else {
            if (face.getModZ() < 0) {
                z = -1;
                skull.setRotation(BlockFace.SOUTH);
            } else {
                z = 1;
                skull.setRotation(BlockFace.NORTH);
            }
        }

        Location location = block.getLocation();
        Area area;

        if (x != 0) {
            area = new Area(new Location(location.getWorld(), location.getBlockX() - x,
                    location.getY(), location.getZ() - 1),
                    new Location(location.getWorld(), location.getBlockX() - x,
                            location.getY()+2, location.getZ()+1));
        } else {
            area = new Area(new Location(location.getWorld(), location.getBlockX() - 1,
                    location.getY(), location.getZ() - z),
                    new Location(location.getWorld(), location.getBlockX() +1,
                            location.getY()+2, location.getZ() - z));
        }

        return area;
    }

    public static boolean action(SkyPlayer player, PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) { return false; }
        if (event.getClickedBlock().getType() != Material.PLAYER_HEAD) { return false; }

        for (Generator generator : player.islandPlayer.island.generators) {
            if (generator.location.equals(event.getClickedBlock().getLocation())) {
                new GeneratorMenu(generator).open(player);
                event.setCancelled(true);
                return true;
            }
        }
        return false;
    }

    public static ItemStack getItem() {
        return ItemTools.createItem(Material.PLAYER_HEAD, ChatColor.GOLD + "Генератор",
                Arrays.asList("Уровень: " + 0, symbol));
    }

    public static ItemStack getItem(Generator generator) {
        return ItemTools.createItem(Material.PLAYER_HEAD, ChatColor.GOLD + "Генератор",
                Arrays.asList("Уровень: " + generator.level, symbol));
    }
}
