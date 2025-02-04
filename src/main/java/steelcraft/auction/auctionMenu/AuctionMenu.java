package steelcraft.auction.auctionMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import steelcraft.auction.Auction;
import steelcraft.auction.AuctionItem;
import steelcraft.menu.Menu;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.utils.ItemTools;

import java.util.HashMap;

public class AuctionMenu extends Menu {
    public int page;

    final int itemsInPage = 36;

    final public int updateInd = 49;

    public HashMap <Integer, AuctionItem> auctionItems = new HashMap<>();
    public AuctionMenu() {
        super("Аукцион", 54, new HashMap<>());

        for (int i = 0; i < 9; i++) {
            items.put(i, ItemTools.createItem(Material.GRAY_STAINED_GLASS_PANE, ""));
            items.put(45 + i, ItemTools.createItem(Material.GRAY_STAINED_GLASS_PANE, ""));
        }

        items.put(updateInd, ItemTools.createItem(Material.SUNFLOWER, ChatColor.YELLOW + "Обновить"));
    }

    @Override
    public void clickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {return;}
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {return;}

        if (event.getCurrentItem() == null) {return;}
        if (event.getCurrentItem().getItemMeta() == null) {return;}

        if (event.getCurrentItem().getItemMeta().equals(Menu.next.getItemMeta())) {
            update(1);
            return;
        }

        if (event.getCurrentItem().getItemMeta().equals(Menu.prev.getItemMeta())) {
            update(-1);
            return;
        }

        if (event.getSlot() == updateInd) {
            update(0);
        }

        if (auctionItems.get(event.getSlot()) != null) {
            if (auctionItems.get(event.getSlot()).player == user) {return;}
            close();
            new AuctionTrade(auctionItems.get(event.getSlot())).open(user);
        }
    }

    public void open(SkyPlayer player) {
        super.open(player);
        page = 0;
        update(0);
    }

    public void update(int change) {
        page += change;

        int ind = 9;

        auctionItems = new HashMap<>();
        for (int i = ind; i < 45; i++) {inv.setItem(i, null);}
        inv.setItem(46, ItemTools.createItem(Material.GRAY_STAINED_GLASS_PANE, ""));
        inv.setItem(52, ItemTools.createItem(Material.GRAY_STAINED_GLASS_PANE, ""));

        int itemInd = -1;
        for (int i = ind; i < 45; i++) {
            itemInd = page * itemsInPage + (i-ind);
            if (itemInd >= Auction.items.size()) {break;}

            auctionItems.put(i, Auction.items.get(itemInd));
            inv.setItem(i, Auction.items.get(itemInd).getShowItem());
        }

        if (itemInd == page * itemsInPage && page != 0) {
            update(-1);
            return;
        }

        if (page != 0) {
            inv.setItem(46, Menu.prev);
        }

        if (itemInd < Auction.items.size()) {
            inv.setItem(52, Menu.next);
        }
    }
}

