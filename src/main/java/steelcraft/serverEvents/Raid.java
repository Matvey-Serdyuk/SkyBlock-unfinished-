package steelcraft.serverEvents;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import steelcraft.Island.Area;
import steelcraft.customEntities.Enemy.EndWarrior;
import steelcraft.customEntities.Enemy.EndWizard;
import steelcraft.customEntities.Enemy.Enemy;
import steelcraft.main.Data;
import steelcraft.main.Main;
import steelcraft.streams.Stream;

import java.util.ArrayList;
import java.util.List;

public class Raid extends Stream {
    final public String path = "Raid";
    final int maxTicks = 25;
    final int maxEnemies = 10;
    final int maxBeforeRestart = 1800;

    public Area area;
    public long delay;

    public int ticks = 0;
    public List<Enemy> enemies = new ArrayList<>();
    public long nextRaid;

    public boolean isStarted = false;

    public Raid(Area area, long delay) {
        super(10000);
        this.area = area;
        this.delay = delay * 1000;

        FileConfiguration fileCon = YamlConfiguration.loadConfiguration(Data.file);

        String str = fileCon.getString(path);

        if (str == null) {
            this.nextRaid = System.currentTimeMillis() + delay;
        } else {
            this.nextRaid = Long.parseLong(str);
        }
    }

    public void start() {
        super.start();
        isStarted = true;
    }

    public void end() {
        nextRaid = System.currentTimeMillis() + delay;
        isStarted = false;
    }

    public void addEnemy() {
        Location location = area.getRandomLocation();
        Enemy enemy;
        if (Math.random() < 0.35) {
            enemy = new EndWizard();
        } else {
            enemy = new EndWarrior();
        }

        enemies.add(enemy);
        enemy.spawn(location);
    }

    public void save() {
        FileConfiguration fileCon = YamlConfiguration.loadConfiguration(Data.file);

        fileCon.set(path, nextRaid);
    }

    @Override
    public void run() {
        if (isStarted) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                if (enemies.size() == maxEnemies) {
                    return;
                }
                if (maxTicks != ticks) {
                    ticks++;
                    addEnemy();
                }
                if (maxTicks == ticks && enemies.size() == 0) {
                    end();
                }
            });
        } else {
            if (getTimeBeforeStart() < 0) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, ()-> {
                    if (Main.secondStream.isMore(maxBeforeRestart)) {
                        start();
                    } else {
                        Main.allMessage(ChatColor.YELLOW + "Рэйд переносится, так как скоро рестарт");
                        nextRaid = Main.secondStream.getSecondsBeforeRestart() * 1000 + 60000;
                    }
                });
            }
        }
    }

    public long getTimeBeforeStart() {
        return nextRaid - System.currentTimeMillis();
    }
}
