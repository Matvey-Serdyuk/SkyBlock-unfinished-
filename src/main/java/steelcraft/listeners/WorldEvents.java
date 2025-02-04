package steelcraft.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import steelcraft.customEntities.CustomEntity;
import steelcraft.main.Data;


public class WorldEvents implements Listener {
    @EventHandler
    public void chunkUnloadEvent(ChunkUnloadEvent event) {
        CustomEntity entity;
        for (Entity ent : event.getChunk().getEntities()) {
            entity = Data.getCustomEntity(ent);
            if (entity == null) { return; }
            entity.deSpawn();
        }
    }

    @EventHandler
    public void worldUnloadEvent(WorldUnloadEvent event) {
        CustomEntity entity;
        for (Entity ent : event.getWorld().getEntities()) {
            entity = Data.getCustomEntity(ent);
            if (entity == null) { return; }
            entity.deSpawn();
        }
    }
}
