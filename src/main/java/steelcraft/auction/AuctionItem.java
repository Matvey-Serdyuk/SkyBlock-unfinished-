package steelcraft.auction;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.utils.Format;

import java.util.ArrayList;
import java.util.List;

public class AuctionItem {
    final long maxTime = 259200000;

    public SkyPlayer player;
    public ItemStack item;
    public double cost;
    public long time;
    public boolean isExposed;

    public ItemStack showItem;

    public AuctionItem(SkyPlayer player, ItemStack item, double cost, long time) {
        this.player = player;
        this.item = item;
        this.cost = cost;
        this.time = time;
        isExposed = true;

        showItem = new ItemStack(item);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) {lore = new ArrayList<>();} else {
            lore.add("");
        }
        lore.add(ChatColor.BLUE + "Стоимость: " + ChatColor.GREEN + cost + ChatColor.DARK_GREEN + "$");
        lore.add(ChatColor.GRAY + "Игрок: " + player.name);

        meta.setLore(lore);
        showItem.setItemMeta(meta);
    }

    public ItemStack getShowItem() {
        ItemStack out = showItem.clone();
        ItemMeta meta = out.getItemMeta();
        List <String> lore = meta.getLore();
        if (!isExposed) {
            lore.add(ChatColor.RED + "Время размещения закончилось");
        } else {
            lore.add(ChatColor.YELLOW + "Оставшиеся время: " + Format.getTimer(maxTime + time - System.currentTimeMillis()));
        }
        meta.setLore(lore);
        out.setItemMeta(meta);
        return out;
    }

    public boolean checkTime() {
        if (maxTime + time - System.currentTimeMillis() < 0) {
            List <AuctionItem> returnItems = Auction.returnItems.get(player);
            if (returnItems == null) {
                returnItems = new ArrayList<>();
            }
            returnItems.add(this);
            Auction.returnItems.put(player, returnItems);
            Auction.items.remove(this);
            isExposed = false;
            return true;
        }
        return false;
    }
}
