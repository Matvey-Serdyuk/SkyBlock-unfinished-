package steelcraft.menu.shop;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import steelcraft.main.Main;
import steelcraft.menu.Menu;

import java.util.HashMap;

public class ShopMenu extends Menu {
    public HashMap<Integer, ShopItem> shopItems;

    public ShopMenu(String title, int size, HashMap<Integer, ShopItem> shopItems) {
        super(title, size, new HashMap<>());
        for (int i : shopItems.keySet()) {
            items.put(i, shopItems.get(i).showItem);
        }

        this.shopItems = shopItems;
    }

    public ShopMenu(ShopMenu menu) {
        super(menu.title, menu.size, menu.items);

        shopItems = menu.shopItems;
    }

    @Override
    public void clickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {return;}
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {return;}

        if (!shopItems.containsKey((event.getSlot()))) { return; }

        ShopItem shopItem = shopItems.get(event.getSlot());
        TradeMenu tradeMenu;
        if (event.getClick() == ClickType.LEFT) {
            tradeMenu = new TradeMenu("Покупка", 6 * 9, shopItem.item, shopItem.buyCost, true);
            close();
            tradeMenu.open(user);
        } else if (event.getClick() == ClickType.RIGHT) {
            tradeMenu = new TradeMenu("Продажа", 6 * 9, shopItem.item, shopItem.sellCost, false);
            close();
            tradeMenu.open(user);
        } else if (event.getClick() == ClickType.MIDDLE) {
            int amount = 0;
            for (ItemStack item : user.bukkitPlayer.getInventory().getContents()) {
                if (item == null) {continue;}
                if (item.getType() == shopItem.item.getType()) {
                    amount += item.getAmount();
                    user.bukkitPlayer.getInventory().remove(item);
                }
            }
            if (amount != 0) {
                user.sendMessage(TradeMenu.sellMessage, true);
                user.changeMoney(amount * shopItem.sellCost, true);
            }
        }
    }
}
