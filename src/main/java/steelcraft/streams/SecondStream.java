package steelcraft.streams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.main.Data;
import steelcraft.main.Main;
import steelcraft.utils.Format;

import java.util.HashMap;
import java.util.Timer;

public class SecondStream extends MainStream {
    final String restartMsg = ChatColor.YELLOW + "Рестарт произойдет через: " + ChatColor.RED;

    long tick = 0;
    long restart = 3600 * 4;
    long warning = 60;

    public SecondStream(int period) {
        super(period);
    }

    @Override
    public void start() {
        timerTask = new SecondStream(period);
        timer = new Timer(false);
        timer.scheduleAtFixedRate(timerTask, 1000, period);
    }

    @Override
    public void run() {
        tick++;

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, ()-> {
            for (SkyPlayer player : Data.onlinePlayers) {
                HashMap<CustomEnchant, Integer> enchants = CustomEnchant.getEnchants(player.livingEntity);

                for (CustomEnchant enc : enchants.keySet()) {
                    enc.time(player, enchants.get(enc));
                }
            }

            long time = getSecondsBeforeRestart();

            if (tick % warning == 0 || time <= 10) {
                Main.allMessage(restartMsg + Format.getTimer( time * 1000 ));
            }

            if (time == 0) {
                Main.reload();
            }
        });
    }

    public long getSecondsBeforeRestart() {
        return restart - tick;
    }

    public boolean isMore(int second) {
        return second > getSecondsBeforeRestart();
    }
}
