package steelcraft.streams;

import org.bukkit.Bukkit;
import steelcraft.main.Data;
import steelcraft.main.Main;
import steelcraft.skyPlayer.SkyPlayer;

import java.util.Timer;

public class UpdateBoardStream extends MainStream {
    public UpdateBoardStream(int period) {
        super(period);
    }

    public void start() {
        timerTask = new UpdateBoardStream(period);
        timer = new Timer(false);
        timer.scheduleAtFixedRate(timerTask, 1000, period);
    }

    @Override
    public void run() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, ()-> {
            for (SkyPlayer player : Data.onlinePlayers) {
                player.updateBoard();
            }
        });
    }
}
