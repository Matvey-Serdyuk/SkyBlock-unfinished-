package steelcraft.blocks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import steelcraft.blocks.data.SpawnerData;
import steelcraft.customEntities.CustomEntity;
import steelcraft.customEntities.SpawnerEntity;
import steelcraft.main.Data;
import steelcraft.main.Main;
import steelcraft.menu.Menu;
import steelcraft.menu.ActionMenu.SpawnerMenu;
import steelcraft.Island.Island;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.streams.SpawnerTick;
import steelcraft.utils.ItemTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spawner extends CustomEntity {
    public static SpawnerEntity[] spawnerEntities = {
            new SpawnerEntity(ChatColor.GOLD + "Спавнер свинок", EntityType.PIG, 0, new ItemStack[] {new ItemStack(Material.PORKCHOP)}),
            new SpawnerEntity(ChatColor.GOLD + "Спавнер куриц", EntityType.CHICKEN, 0, new ItemStack[] {new ItemStack(Material.CHICKEN)}),
            new SpawnerEntity(ChatColor.GOLD + "Спавнер зомби", EntityType.ZOMBIE, 4, new ItemStack[] {new ItemStack(Material.ROTTEN_FLESH)}),
            new SpawnerEntity(ChatColor.GOLD + "Спавнер пауков", EntityType.SPIDER, 5, new ItemStack[] {new ItemStack(Material.STRING)}),
            new SpawnerEntity(ChatColor.GOLD + "Спавнер скелетов", EntityType.SKELETON, 5, new ItemStack[] {new ItemStack(Material.BONE), new ItemStack(Material.ARROW)}),
            new SpawnerEntity(ChatColor.GOLD + "Спавнер коров", EntityType.COW, 0, new ItemStack[] {new ItemStack(Material.BEEF), new ItemStack(Material.LEATHER)}),
            new SpawnerEntity(ChatColor.GOLD + "Спавнер криперов", EntityType.CREEPER, 5, new ItemStack[] {new ItemStack(Material.GUNPOWDER)}),
            new SpawnerEntity(ChatColor.GOLD + "Спавнер спрутов", EntityType.SQUID, 0, new ItemStack[] {new ItemStack(Material.INK_SAC)}),
            new SpawnerEntity(ChatColor.GOLD + "Спавнер ифритов", EntityType.BLAZE, 10, new ItemStack[] {new ItemStack(Material.BLAZE_ROD)})};

    final public static int MAX_LEVEL = 5;
    final static int MAX_AMOUNT = 100;

    public int level;
    public int amount;
    public int exp;

    public Location location;
    public Location spawn;
    public ItemStack[] drops;

    public ItemStack item;
    public SpawnerTick tickStream;

    public Spawner(Location location, ItemStack item, Player p) {
        super(EntityType.fromName(Menu.getSymbol(item)), "", "mobFromSpawner", 20, 0, 0, 0,
                0.5, null, null, false);
        if (type == null) {
            p.sendMessage(ChatColor.RED + "Все полетело! Спавнер не был определен! Сообщите разрабу!");
            Main.plugin.getLogger().info(ChatColor.RED + "Everything blow up! Spawner doesn't exist");
            return;
        }

        this.location = location;
        level = 1;

        item.setAmount(1);
        this.item = new ItemStack(item);
        constructor();
        tickStream = new SpawnerTick(this, 10000);
        tickStream.start();
    }

    public Spawner(SpawnerData data) {
        super(data.type, "", "mobFromSpawner", 20, 0, 0, 0,
                0.5, null, null, false);

        location = data.location.getLocation();
        level = data.level;
        item = getItem(type);
        constructor();
    }

    public void constructor() {
        spawn = new Location(location.getWorld(), location.getX()+0.5, location.getY() - 2, location.getZ()+0.5);

        SpawnerEntity spawnerEntity = SpawnerEntity.getSpawnerEntity(type);
        this.drops = spawnerEntity.drop;
        this.exp = spawnerEntity.exp;

        amount = 0;
    }

    public void spawn() {
        super.spawn(spawn);
        livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HP * MAX_AMOUNT);
    }

    public void death() {
        if (amount != 0) {
            amount = 0;
            Main.plugin.getLogger().info(ChatColor.RED + "What? I don't know how this mob die");
        }
        super.death();
    }

    public void deSpawn() {
        super.deSpawn();
        amount = 0;
    }

    public void wound(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {return;}

        if ((amount-1) * 20 >= livingEntity.getHealth() - event.getFinalDamage()) {
            amount--;
            dropItems();
            updateCustomName();
        }
    }

    public void wound(EntityDamageByEntityEvent event) {
        if ((amount-1) * 20 >= livingEntity.getHealth() - event.getFinalDamage()) {
            amount--;
            dropItems();
            updateCustomName();
        }

        if (event.getDamager().getType() == EntityType.PLAYER) {
            Player player = (Player) event.getDamager();
            player.giveExp(exp * level);
        }
    }

    public void tick() {
        if (!spawn.getBlock().getChunk().isLoaded()) {return;}
        if (amount == 0) { amount++; spawn(); }
        else if (amount != MAX_AMOUNT) {
            addHealth(HP);
            amount++;
        }
        updateCustomName();
    }

    public void dropItems() {
        ItemStack item;
        for (ItemStack curItem : drops) {
            item = new ItemStack(curItem);
            item.setAmount(level);
            spawn.getWorld().dropItem(spawn, item);
        }
    }

    public void upgrade(SkyPlayer player) {
        level++;
        player.sendMessage(ChatColor.GOLD + "Вы улучшили спавнер!", true);
    }

    public static boolean blockBreak(SkyPlayer player, BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.SPAWNER) {
            return false;
        }
        for (Spawner spawner : player.islandPlayer.island.spawners) {
            if (spawner.location.equals(event.getBlock().getLocation())) {
                event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), spawner.item);
                event.setExpToDrop(0);

                if (spawner.level == 1) {
                    spawner.deSpawn();
                    spawner.tickStream.stop();
                    player.islandPlayer.island.spawners.remove(spawner);
                } else {
                    event.setCancelled(true);
                    spawner.level--;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean blockPlace(SkyPlayer player, BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.SPAWNER) {
            return false;
        }
        player.islandPlayer.island.spawners.add(new Spawner(event.getBlock().getLocation(), new ItemStack(event.getItemInHand()), event.getPlayer()));
        CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();
        spawner.setSpawnedType(EntityType.fromName(Menu.getSymbol(event.getItemInHand())));
        spawner.setSpawnCount(0);
        spawner.update();
        return true;
    }

    public static boolean action(SkyPlayer player, PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {return false;}
        if (event.getClickedBlock().getType() != Material.SPAWNER) {return false;}
        if (!player.islandPlayer.island.area.checkBlock(event.getClickedBlock().getLocation())) {return false;}
        if (player.bukkitPlayer.isSneaking()) {return true;}

        Location location = event.getClickedBlock().getLocation();
        for (Spawner spawner : player.islandPlayer.island.spawners) {
            if (location.equals(spawner.location)) {
                new SpawnerMenu(spawner).open(player);
                event.setCancelled(true);
                return true;
            }
        }
        return false;
    }

    public static ItemStack getItem(EntityType type) {
        return ItemTools.createItem(Material.SPAWNER, SpawnerEntity.getSpawnerEntity(type).name,
                Arrays.asList(ChatColor.BLACK + type.getName()));
    }

    public static List<Spawner> getAllSpawners() {
        List<Spawner> out = new ArrayList<>();
        for (Island island : Data.islands) {
            out.addAll(island.spawners);
        }
        return out;
    }

    public void updateCustomName() {
        if (amount < 30) {
            entity.setCustomName(ChatColor.GREEN + "" + amount);
        } else if (amount < 75) {
            entity.setCustomName(ChatColor.YELLOW + "" + amount);
        } else {
            entity.setCustomName(ChatColor.RED + "" + amount);
        }
    }
}
