package steelcraft.Case;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import steelcraft.Case.Drops.Drop;
import steelcraft.main.Main;
import steelcraft.skyPlayer.SkyPlayer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class OpenCase extends TimerTask {
    final int a = 1;
    public OpenCaseMenu menu;

    public Timer timer;
    public SkyPlayer player;
    public Drop[] drops;
    public int spin;


    public OpenCase(SkyPlayer player, Drop[] drops, int spin) {
        this.player = player;
        this.drops = drops;
        this.spin = spin;
        menu = new OpenCaseMenu(this);
        menu.open(player);

        TimerTask timerTask = this;
        timer = new Timer(false);
        timer.schedule(timerTask, 0);
    }

    @Override
    public void run() {
        float delay = 100 + (float) spin * 2;
        float v = 7.5f + (float) (spin) / 100 * 7.5f;
        float a = 1 + (float) (spin) / 100;
        float f = 0.1f + (float) (spin) / 100;
        for (int cur = 4; cur <= spin; cur++) {
            final int current = cur;
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                if (!player.openCase) {
                    timer.cancel();
                    return;
                }
                menu.setCur(current);
            });
            try {
                TimeUnit.MILLISECONDS.sleep((long) delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (v < delay && delay < 800) {
                delay -= v;
            }
            v -= Math.max(a, f);
            a -= f;
        }
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
           if (player.openCase && player.openMenu != null) {
               player.openMenu.close();
           }
        });
    }
}
