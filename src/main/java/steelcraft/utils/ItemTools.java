package steelcraft.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class ItemTools {
    public static ItemStack createItem(Material type, String name) {
        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(Material type, String name, List<String> lore) {
        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(Material type, String name, List<String> lore, int amount) {
        ItemStack item = new ItemStack(type);
        item.setAmount(amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getHead(Player player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(player);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createLeatherArmor(Material type, Color color) {
        ItemStack item = new ItemStack(type);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        assert meta != null;
        meta.setColor(color);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createLeatherArmor(Material type, Color color, List <String> lore) {
        ItemStack item = new ItemStack(type);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        assert meta != null;
        meta.setColor(color);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
