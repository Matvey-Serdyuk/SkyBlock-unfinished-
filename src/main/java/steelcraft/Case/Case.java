package steelcraft.Case;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import steelcraft.Case.Drops.Drop;
import steelcraft.customEntities.WorldText;
import steelcraft.main.worlds.SpawnWorld;
import steelcraft.menu.InfoMenu;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.utils.ItemTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Case {
    public static Case[] cases;

    public String customName;
    public String name;
    public WorldText text;
    public Location location;
    public Drop[] drops;
    public ItemStack key;

    public static void set() {
        cases = new Case[] {
                new Case(ChatColor.GREEN + "Обычный кейс", "normalCase",
                        new Location(SpawnWorld.world, 0, 0, 0), new Drop[]{

                }),
                new Case(ChatColor.BLUE + "Редкий кейс", "rareCase",
                        new Location(SpawnWorld.world, 0, 0, 0), new Drop[]{

                }),
                new Case(ChatColor.DARK_PURPLE + "Эпический кейс", "epicCase",
                        new Location(SpawnWorld.world, 0, 0, 0), new Drop[]{

                }),
                new Case(ChatColor.RED + "Легендарный кейс", "legendCase",
                        new Location(SpawnWorld.world, 0, 0, 0), new Drop[]{

                }),
                new Case(ChatColor.GREEN + "Кейс с энчантами", "enchantsCase",
                        new Location(SpawnWorld.world, 0, 0, 0), new Drop[]{

                }),
                new Case(ChatColor.DARK_RED + "Кейс с редкими энчантами", "rareEnchantsCase",
                        new Location(SpawnWorld.world, 0, 0, 0), new Drop[]{

                }),
        };
    }

    public Case(String customName, String name, Location location, Drop[] drops) {
        this.customName = customName;
        this.name = name;
        this.location = location;
        this.drops = drops;
        key = ItemTools.createItem(Material.TRIPWIRE_HOOK, ChatColor.YELLOW + "Ключ от " + customName,
                Arrays.asList(ChatColor.BLACK + "KeyFor" + name));

        text = new WorldText(new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getZ()),
                new String[]{customName});
    }

    public void open(SkyPlayer player) {
        int size = 125;
        Drop[] drops = new Drop[size];
        for (int i = 0; i < size; i++) {
            drops[i] = getDrop();
        }
        int spin = (int) Math.round(25 + Math.random() * 80);

        new OpenCase(player, drops, spin);
    }

    public Drop getDrop() {
        float curChance = 0;
        float rand = (float) Math.random() * 100;
        for (Drop item : drops) {
            curChance += item.chance;
            if (rand < curChance) {
                return item;
            }
        }
        return null;
    }

    public static boolean action(SkyPlayer player, PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) {return false;}

        ItemStack item = event.getPlayer().getItemInHand();
        for (Case chest : cases) {
            if (event.getClickedBlock().getLocation().equals(chest.location)) {
                event.setCancelled(true);
                if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    ItemStack itemStack;
                    ItemMeta meta;
                    List<String> lore;
                    String color;
                    HashMap<Integer, ItemStack> items = new HashMap<>();
                    for (int i = 0; i < chest.drops.length; i++) {
                        Drop drop = chest.drops[i];
                        itemStack = new ItemStack(drop.item);
                        meta = itemStack.getItemMeta();
                        if (meta == null) {continue;}
                        lore = meta.getLore();
                        if (lore == null) {
                            lore = new ArrayList<>();
                        }

                        lore.add("");

                        lore.add(ChatColor.GOLD + "Шанс: " + drop.rare.getColor() + drop.chance + ChatColor.GOLD + "%");
                        meta.setLore(lore);
                        itemStack.setItemMeta(meta);
                        items.put(i, itemStack);
                    }
                    new InfoMenu("Содержимое кейса", 27, items).open(player);
                    return true;
                }

                if (item.getItemMeta() == null) {return true;}

                if (event.getAction() == Action.RIGHT_CLICK_BLOCK && item.getItemMeta().equals(chest.key.getItemMeta())) {
                    item.setAmount(item.getAmount() - 1);
                    chest.open(player);
                }
                return true;
            }
        }
        return false;
    }
}
