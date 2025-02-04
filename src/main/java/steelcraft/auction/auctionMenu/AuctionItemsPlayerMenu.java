package steelcraft.auction.auctionMenu;

import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import steelcraft.auction.Auction;
import steelcraft.auction.AuctionItem;
import steelcraft.menu.Menu;
import steelcraft.skyPlayer.SkyPlayer;

import java.util.HashMap;
import java.util.List;

public class AuctionItemsPlayerMenu extends Menu {
    public List<AuctionItem> auctionItems;

    public AuctionItemsPlayerMenu() {
        super("Ваши предметы", 27, new HashMap<>());
    }

    @Override
    public void clickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {return;}
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {return;}

        if (items.containsKey(event.getSlot())) {
            AuctionItem item = auctionItems.get(event.getSlot());

            if (!item.isExposed) {
                Auction.returnItems.get(user).remove(item);
                user.bukkitPlayer.getInventory().addItem(item.item.clone());
                user.sendMessage(ChatColor.YELLOW + "Вы вернули себе предмет", true);
            }
            else if (Auction.items.contains(item)) {
                Auction.removeItem(item);
                user.bukkitPlayer.getInventory().addItem(item.item.clone());
                user.sendMessage(ChatColor.YELLOW + "Вы вернули себе предмет", true);
            } else {
                user.sendMessage(Auction.isBuyMessage, true);
            }

            update();
        }
    }

    public void open(SkyPlayer player) {
        super.open(player);

        update();
    }

    public void update() {
        auctionItems = Auction.getItemsPlayer(user);
        items = new HashMap<>();
        inv.clear();

        for (int i = 0; i < auctionItems.size(); i++) {
            items.put(i, auctionItems.get(i).getShowItem());
            inv.setItem(i, auctionItems.get(i).getShowItem());
        }
    }
}
