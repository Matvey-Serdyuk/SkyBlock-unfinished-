package steelcraft.streams;


import org.bukkit.Bukkit;
import steelcraft.main.Main;
import steelcraft.blocks.Spawner;

public class SpawnerTick extends Stream {
    Spawner spawner;

    public SpawnerTick(Spawner spawner, int period) {
        super(period);
        this.spawner = spawner;
    }

    @Override
    public void run() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
           spawner.tick();
        });
    }
}
