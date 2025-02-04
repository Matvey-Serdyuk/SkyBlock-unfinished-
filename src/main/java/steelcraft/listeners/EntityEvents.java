package steelcraft.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.PortalCreateEvent;
import steelcraft.customEntities.Bosses.Dragon;
import steelcraft.customEntities.CustomEntity;
import steelcraft.main.Data;
import steelcraft.main.Main;

public class EntityEvents implements Listener {
    @EventHandler
    public void spawnEntity(EntitySpawnEvent event) {
        if (!Main.spawn && !event.getEntity().getScoreboardTags().contains("text") &&
                event.getEntityType() != EntityType.DROPPED_ITEM) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void deathEntity(EntityDeathEvent event) {
        if (event.getEntity().getType() != EntityType.PLAYER) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
        CustomEntity entity = Data.getCustomEntity(event.getEntity());
        if (entity == null) { return; }

        entity.death();
    }

    @EventHandler
    public void dragonEvent(EnderDragonChangePhaseEvent event) {
        CustomEntity entity = Data.getCustomEntity(event.getEntity());
        if (entity == null) {return;}
        if (entity instanceof Dragon) {
            Dragon dragon = (Dragon) entity;
            dragon.changePhase(event);
        }
    }

    @EventHandler
    public void createPortal(PortalCreateEvent event) {
        event.setCancelled(true);
    }
}
