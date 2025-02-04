package steelcraft.customEntities;

import org.bukkit.Bukkit;
import steelcraft.main.Main;
import steelcraft.streams.Stream;

public class EntityStream extends Stream {
    CustomEntity entity;
    public EntityStream(CustomEntity entity) {
        super(2000);

        this.entity = entity;
    }

    @Override
    public void run() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, ()-> {
            if (!entity.isAlive) {
                if (isWork) {
                    stop();
                }
                return;
            }

            entity.tick();
        });
    }
}
