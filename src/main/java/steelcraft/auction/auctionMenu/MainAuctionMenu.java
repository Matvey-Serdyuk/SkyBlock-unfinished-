package steelcraft.auction.auctionMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import steelcraft.menu.Menu;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.utils.ItemTools;

import java.util.HashMap;

public class MainAuctionMenu extends Menu {
    static int indPlayerItems = 10;
    static int indAuction = 16;

    public MainAuctionMenu() {
        super("Аукцион меню", 27, new HashMap<>());
        items.put(indPlayerItems, ItemTools.createItem(Material.BARRIER, ""));
        items.put(indAuction, ItemTools.createItem(Material.GOLD_INGOT, ChatColor.GOLD + "Аукцион"));
    }

    @Override
    public void clickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {return;}
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {return;}

        if (event.getSlot() == indPlayerItems) {
            close();
            new AuctionItemsPlayerMenu().open(user);
        } else if (event.getSlot() == indAuction) {
            close();
            new AuctionMenu().open(user);
        }
    }

    public void open(SkyPlayer player) {
        super.open(player);

        ItemStack head = ItemTools.getHead(player.bukkitPlayer);
        ItemMeta meta = head.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Ваши предметы");
        head.setItemMeta(meta);

        items.put(indPlayerItems, head);
        inv.setItem(indPlayerItems, items.get(indPlayerItems));
    }
}
