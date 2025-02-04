package steelcraft.auction;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import steelcraft.auction.data.ReturnItemData;
import steelcraft.main.Data;
import steelcraft.main.Main;
import steelcraft.skyPlayer.SkyPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Auction {
    final private static String itemsPath = "AuctionItems";
    final private static String returnItemsPath = "returnItems";

    final public static int maxSlotPlayer = 6;

    final public static String isBuyMessage = ChatColor.RED + "Этот предмет уже купили!";

    public static List<AuctionItem> items = new ArrayList<>();

    public static HashMap<SkyPlayer, List <AuctionItem>> returnItems = new HashMap<>();

    public static void addItem(SkyPlayer player, ItemStack item, double cost) {
        items.add(new AuctionItem(player, item, cost, System.currentTimeMillis()));
    }

    public static void removeItem(AuctionItem item) {
        items.remove(item);
    }

    public static List <AuctionItem> getItemsPlayer(SkyPlayer player) {
        List <AuctionItem> out = new ArrayList<>();
        for (AuctionItem item : items) {
            if (item.player.equals(player)) {
                out.add(item);
            }
        }

        List <AuctionItem> rItems = returnItems.get(player);
        if (rItems == null) {return out;}
        out.addAll(rItems);
        return out;
    }

    public static void save(FileConfiguration fileCon) {
        List<String> auctionItemsList = new ArrayList<>();
        List<String> auctionReturnItemsList = new ArrayList<>();

        for (AuctionItem item : Auction.items) {
            auctionItemsList.add(Main.gson.toJson(item));
        }

        for (SkyPlayer player : returnItems.keySet()) {
            auctionReturnItemsList.add(Main.gson.toJson(new ReturnItemData(player.name, returnItems.get(player))));
        }

        fileCon.set(itemsPath, auctionItemsList);
        fileCon.set(returnItemsPath, auctionReturnItemsList);
    }

    public static void load(FileConfiguration fileCon) {
        List <String> auctionItemsList = fileCon.getStringList(itemsPath);
        List <String> returnItemsList = fileCon.getStringList(returnItemsPath);

        for (String string : auctionItemsList) {
            Auction.items.add(Main.gson.fromJson(string, AuctionItem.class));
        }

        ReturnItemData data;
        for (String string : returnItemsList) {
            data = Main.gson.fromJson(string, ReturnItemData.class);
            returnItems.put(Data.getSkyPlayer(data.name), data.items);
        }
    }
}
