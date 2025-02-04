package steelcraft.auction.data;

import steelcraft.auction.AuctionItem;

import java.util.List;

public class ReturnItemData {
    public String name;
    public List <AuctionItem> items;

    public ReturnItemData(String name, List<AuctionItem> items) {
        this.name = name;
        this.items = items;
    }
}
