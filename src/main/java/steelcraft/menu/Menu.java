package steelcraft.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.utils.ItemTools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

abstract public class Menu {
    public static ItemStack next = ItemTools.createItem(Material.ARROW, ChatColor.GRAY + "Следующая", Arrays.asList(ChatColor.BLACK + "next"));
    public static ItemStack prev = ItemTools.createItem(Material.ARROW, ChatColor.GRAY + "Предыдущий", Arrays.asList(ChatColor.BLACK + "prev"));
    public static ItemStack back = ItemTools.createItem(Material.BONE, ChatColor.GRAY + "Назад", Arrays.asList(ChatColor.BLACK + "back"));

    public SkyPlayer user;
    public Inventory inv;

    public String title;
    public int size;
    public HashMap<Integer, ItemStack> items;

    public boolean canTake = false;
    public boolean isUpdate = false;

    public Menu(String title, int size, HashMap<Integer, ItemStack> items) {
        this.title = title;
        this.size = size;
        this.items = items;
    }

    public void open(SkyPlayer player) {
        inv = Bukkit.createInventory(player.bukkitPlayer, size, title);

        for (int k : items.keySet()) {
            inv.setItem(k, items.get(k));
        }

        player.bukkitPlayer.openInventory(inv);
        user = player;
        player.openMenu = this;
    }

    public abstract void clickEvent(InventoryClickEvent event);

    public void close() {
        user.openMenu = null;
        user.bukkitPlayer.closeInventory();
    }

    public static Integer getNum(ItemStack item) {
        if (item.getItemMeta() == null) { return null; }
        List<String> lore = item.getItemMeta().getLore();
        if (lore == null) { return null; }
        String str = lore.get(lore.size() - 1);
        int num;
        try {
            num = Integer.parseInt(str.substring(2));
        } catch (Exception e) {
            return null;
        }
        return num;
    }

    public static String getSymbol(ItemStack item) {
        if (item.getItemMeta() == null) { return null; }
        List<String> lore = item.getItemMeta().getLore();
        if (lore == null) { return null; }
        String str = lore.get(lore.size() - 1);
        return str.substring(2);
    }
}
