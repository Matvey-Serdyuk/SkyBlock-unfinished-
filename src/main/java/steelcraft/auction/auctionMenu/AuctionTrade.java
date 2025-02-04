package steelcraft.auction.auctionMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import steelcraft.auction.Auction;
import steelcraft.auction.AuctionItem;
import steelcraft.economy.Economy;
import steelcraft.menu.Menu;
import steelcraft.utils.ItemTools;

import java.util.HashMap;

public class AuctionTrade extends Menu {
    public AuctionItem item;

    final public int iWant = 19;
    final public int iNotWant = 25;
    public AuctionTrade(AuctionItem item) {
        super("Подтверждение", 36, new HashMap<>());
        this.item = item;

        items.put(13, item.showItem);

        items.put(iNotWant, ItemTools.createItem(Material.RED_WOOL, ChatColor.RED + "Я не хочу преобрести это!"));
        items.put(iWant, ItemTools.createItem(Material.GREEN_WOOL, ChatColor.GREEN + "Я хочу преобрести это!"));
    }

    @Override
    public void clickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {return;}
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {return;}

        if (event.getSlot() == iWant) {
            if (!Auction.items.contains(item)) {
                user.sendMessage(Auction.isBuyMessage, true);
                return;
            }
            if (Economy.canBuy(user, item.cost, true)) {
                user.changeMoney(item.cost * -1, true);
                item.player.changeMoney(item.cost, true);
                if (item.item.getItemMeta() != null) {
                    item.player.sendMessage(ChatColor.YELLOW + "У вас купили: " + item.item.getItemMeta().getDisplayName(), true);
                } else {
                    item.player.sendMessage(ChatColor.YELLOW + "У вас купили предмет", true);
                }
                Auction.items.remove(item);
                user.bukkitPlayer.getInventory().addItem(item.item.clone());
            }

            close();
        } else if (event.getSlot() == iNotWant) {
            close();
        }
    }
}
