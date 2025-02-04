package steelcraft.menu.shop;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopItem {
    public String title;
    public ItemStack item;
    public double buyCost;
    public double sellCost;

    public ItemStack showItem;

    public ShopItem(String title, ItemStack item, double buyCost, double sellCost) {
        this.title = title;
        this.item = item;
        this.buyCost= buyCost;
        this.sellCost = sellCost;

        showItem = new ItemStack(item);
        ItemMeta meta = showItem.getItemMeta();
        if (meta == null) {return;}
        meta.setDisplayName(title);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.BLUE + "Купить: " + ChatColor.GRAY + buyCost + ChatColor.GREEN + "$");
        lore.add(ChatColor.BLUE + "Продать: " + ChatColor.GRAY + sellCost + ChatColor.GREEN + "$");
        meta.setLore(lore);
        showItem.setItemMeta(meta);
    }
}
