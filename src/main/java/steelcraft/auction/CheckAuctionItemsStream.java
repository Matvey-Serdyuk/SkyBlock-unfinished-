package steelcraft.auction;

import org.bukkit.Bukkit;
import steelcraft.main.Main;
import steelcraft.streams.MainStream;

import java.util.Timer;

public class CheckAuctionItemsStream extends MainStream {
    public CheckAuctionItemsStream(int period) {
        super(period);
    }

    @Override
    public void start() {
        timerTask = new CheckAuctionItemsStream(period);
        timer = new Timer(false);
        timer.scheduleAtFixedRate(timerTask, 1000, period);
    }

    @Override
    public void run() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, ()-> {
            for (int i = 0; i < Auction.items.size(); i++) {
                if (Auction.items.get(i).checkTime()) {
                    i--;
                }
            }
        });
    }
}
