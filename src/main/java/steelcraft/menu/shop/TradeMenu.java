package steelcraft.menu.shop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import steelcraft.economy.Economy;
import steelcraft.menu.Menu;
import steelcraft.utils.ItemTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TradeMenu extends Menu {
    final public static String buyMessage = ChatColor.GREEN + "Вы успешно совершили покупку!";
    final public static String sellMessage = ChatColor.GREEN + "Вы удачно продали предметы!";

    public ItemStack item;
    public double cost;
    public boolean buy;

    final private int start = 9;
    final private int end = 9 * 5 - 1;

    public TradeMenu(String title, int size, ItemStack item, double cost, boolean buy) {
        super(title, size, new HashMap<>());
        items.put(5*9, ItemTools.createItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "-64", Arrays.asList(ChatColor.BLACK + "-64")));
        items.put(5*9+1, ItemTools.createItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "-16", Arrays.asList(ChatColor.BLACK + "-16")));
        items.put(5*9+2, ItemTools.createItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "-8", Arrays.asList(ChatColor.BLACK + "-8")));
        items.put(5*9+3, ItemTools.createItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "-1", Arrays.asList(ChatColor.BLACK + "-1")));
        items.put(5*9+4, ItemTools.createItem(Material.YELLOW_STAINED_GLASS_PANE, ChatColor.GOLD + "Обмен", Arrays.asList(ChatColor.BLACK + "0")));
        items.put(5*9+5, ItemTools.createItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "+1", Arrays.asList(ChatColor.BLACK + "1")));
        items.put(5*9+6, ItemTools.createItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "+8", Arrays.asList(ChatColor.BLACK + "8")));
        items.put(5*9+7, ItemTools.createItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "+16", Arrays.asList(ChatColor.BLACK + "16")));
        items.put(5*9+8, ItemTools.createItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "+64", Arrays.asList(ChatColor.BLACK + "64")));
        for (int i = 0; i < 9; i++) {
            items.put(i, ItemTools.createItem(Material.GRAY_STAINED_GLASS_PANE, " "));
        }
        ItemStack showItem = new ItemStack(item);
        showItem.setAmount(showItem.getMaxStackSize());
        items.put(4, showItem);

        set(item, cost, buy);
    }

    public TradeMenu(TradeMenu menu) {
        super(menu.title, menu.size, menu.items);
        set(menu.item, menu.cost, menu.buy);
    }

    private void set(ItemStack item, double cost, boolean buy) {
        this.item = item;
        this.cost = cost;
        this.buy = buy;
    }

    @Override
    public void clickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {return;}
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {return;}

        ItemStack item = event.getCurrentItem();
        if (item == null) {return;}
        Integer num = getNum(item);
        if (num == null) { return; }

        if (num == 0) {
            deal();
        } else if (num > 0) {
            addItems(num);
        } else {
            lowItems(num * -1);
        }
    }

    public void deal() {
        int amount = 0;
        ItemStack curItem;
        for (int i = start; i < end + 1; i++) {
            curItem = inv.getItem(i);
            if (curItem == null) {
                break;
            }
            if (curItem.getType() == Material.AIR) {
                break;
            }
            amount += curItem.getAmount();
        }

        if (buy) {
            // buy
            ItemStack item;
            int curAmount = amount;
            if (Economy.canBuy(user, curAmount * cost, true)) {
                while (curAmount != 0) {
                    item = new ItemStack(this.item);
                    if (curAmount > item.getMaxStackSize()) {
                        item.setAmount(item.getMaxStackSize());
                        curAmount -= item.getMaxStackSize();
                    } else {
                        item.setAmount(curAmount);
                        curAmount = 0;
                    }
                    user.bukkitPlayer.getInventory().addItem(item);
                }
                user.sendMessage(buyMessage, true);
                user.changeMoney(amount * cost * -1, true);
            }
        } else {
            // sell
            int playerAmount = 0;
            boolean isSuccessfully = false;
            List<ItemStack> items = new ArrayList<>();
            for (ItemStack item : user.bukkitPlayer.getInventory().getContents()) {
                if (item == null) { continue; }
                if (item.getType() == this.item.getType() && item.getItemMeta().equals(this.item.getItemMeta())) {
                    if (playerAmount + item.getAmount() >= amount) {
                        // Go!
                        item.setAmount(playerAmount + item.getAmount() - amount);
                        for (ItemStack popItem : items) {
                            user.bukkitPlayer.getInventory().remove(popItem);
                        }
                        isSuccessfully = true;
                        user.sendMessage(sellMessage, true);
                        user.changeMoney(cost * amount, true);
                        break;
                    } else {
                        items.add(item);
                        playerAmount += item.getAmount();
                    }
                }
            }
            if (!isSuccessfully) {
                user.sendMessage(ChatColor.RED + "У вас недостаточно предметов", true);
            }
        }

        close();
    }

    public void addItems(int amount) {
        ItemStack item = new ItemStack(this.item);
        item.setAmount(amount);
        inv.addItem(item);
    }

    public void lowItems(int amount) {
        ItemStack curItem;
        int ind = end + 1;
        while (amount != 0 && ind != start) {
            ind--;
            curItem = inv.getItem(ind);
            if (curItem == null) { continue; }
            if (curItem.getType() == item.getType()) {
                if (curItem.getAmount() > amount) {
                    curItem.setAmount(curItem.getAmount() - amount);
                    amount = 0;
                } else {
                    amount -= curItem.getAmount();
                    inv.setItem(ind, null);
                }
            }
        }
    }
}
