package steelcraft.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import steelcraft.menu.shop.ShopItem;
import steelcraft.menu.shop.ShopMenu;
import steelcraft.blocks.Spawner;
import steelcraft.utils.ItemTools;

import java.util.HashMap;

public class Catalog extends Menu {
    public static HashMap<String, ShopMenu> menus = new HashMap<String, ShopMenu>() {{
        put("Блоки", new ShopMenu("Блоки", 27, new HashMap<Integer, ShopItem>() {{
            put(0, new ShopItem(ChatColor.BLUE + "Булыжник", new ItemStack(Material.COBBLESTONE), 0.5, 0.1));
            put(1, new ShopItem(ChatColor.BLUE + "Уголь", new ItemStack(Material.COAL), 1.5, 0.3));
            put(2, new ShopItem(ChatColor.BLUE + "Железо", new ItemStack(Material.IRON_INGOT), 10, 2));
            put(3, new ShopItem(ChatColor.BLUE + "Лазурит", new ItemStack(Material.LAPIS_LAZULI), 5, 0.35));
            put(4, new ShopItem(ChatColor.BLUE + "Редстоун", new ItemStack(Material.REDSTONE), 25, 2.5));
            put(5, new ShopItem(ChatColor.BLUE + "Золото", new ItemStack(Material.GOLD_INGOT), 40, 20));
            put(6, new ShopItem(ChatColor.BLUE + "Алмаз", new ItemStack(Material.DIAMOND), 100, 60));
            put(7, new ShopItem(ChatColor.BLUE + "Изумруд", new ItemStack(Material.EMERALD), 100, 80));
        }}));

        put("Спавнера", new ShopMenu("Спавнера", 27, new HashMap<Integer, ShopItem>() {{
            put(9, new ShopItem(ChatColor.GOLD + "Спавнер свинок", Spawner.getItem(EntityType.PIG), 1000, 200));
            put(10, new ShopItem(ChatColor.GOLD + "Спавнер куриц", Spawner.getItem(EntityType.CHICKEN), 1500, 300));
            put(11, new ShopItem(ChatColor.GOLD + "Спавнер зомби", Spawner.getItem(EntityType.ZOMBIE), 2500, 500));
            put(12, new ShopItem(ChatColor.GOLD + "Спавнер пауков", Spawner.getItem(EntityType.SPIDER), 3500, 700));
            put(13, new ShopItem(ChatColor.GOLD + "Спавнер скелетов", Spawner.getItem(EntityType.SKELETON), 5000, 1000));
            put(14, new ShopItem(ChatColor.GOLD + "Спавнер коров", Spawner.getItem(EntityType.COW), 6500, 1300));
            put(15, new ShopItem(ChatColor.GOLD + "Спавнер криперов", Spawner.getItem(EntityType.CREEPER), 10000, 2000));
            put(16, new ShopItem(ChatColor.GOLD + "Спавнер спрутов", Spawner.getItem(EntityType.SQUID), 20000, 4000));
            put(17, new ShopItem(ChatColor.GOLD + "Спавнер ифритов", Spawner.getItem(EntityType.BLAZE), 25000, 5000));
        }}));
    }};

    int blocksInd = 10;
    int spawnersInd = 16;

    public Catalog() {
        super("Каталог", 27, new HashMap<>());
        this.items.put(blocksInd, ItemTools.createItem(Material.DIRT, ChatColor.GREEN + "Блоки"));
        this.items.put(spawnersInd, ItemTools.createItem(Material.SPAWNER, ChatColor.GOLD + "Спавнер"));
    }

    @Override
    public void clickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {return;}
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {return;}

        if (event.getSlot() == blocksInd) {
            close();
            new ShopMenu(menus.get("Блоки")).open(user);
        } else if (event.getSlot() == spawnersInd) {
            close();
            new ShopMenu(menus.get("Спавнера")).open(user);
        }
    }
}
