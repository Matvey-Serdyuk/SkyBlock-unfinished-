package steelcraft.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import steelcraft.customEntities.CustomEntity;
import steelcraft.main.Data;
import steelcraft.main.Main;


public class EntityDamageEvents implements Listener {
    @EventHandler
    public void entityDamage(EntityDamageEvent event) {
        CustomEntity entity = Data.getCustomEntity(event.getEntity());
        if (entity == null) { return; }

        entity.wound(event);
    }

    @EventHandler
    public void entityDamageByEntity(EntityDamageByEntityEvent event) {
        CustomEntity entity = Data.getCustomEntity(event.getEntity());
        CustomEntity damager = Data.getCustomEntity(event.getDamager());
        if (entity == null || damager == null) { return; }
        if (entity.pvp && damager.pvp) {
            damager.attack(event, entity, damager);
            entity.wound(event, entity, damager);
            // Main.plugin.getLogger().info("" + event.getDamage() + " " + event.getFinalDamage());
        } else {
            event.setCancelled(true);
        }
    }
}
