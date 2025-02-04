package steelcraft.customEntities.Bosses;

import net.minecraft.server.v1_13_R2.EntityEnderDragon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEnderDragon;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import steelcraft.Case.Drops.ItemDrop;
import steelcraft.main.Main;
import steelcraft.main.worlds.EndWorld;

public class Dragon extends Boss {
    public EnderDragon enderDragon;
    public EntityEnderDragon entityEnderDragon;

    public Dragon() {
        super(EntityType.ENDER_DRAGON, "dragon", "enderDragon", 50, 10, 1, 10, 0.35,
                null, null, true, new Location(EndWorld.world, 0, 100, 0), 2500, new ItemDrop[]{},
                10000);
    }

    public void changePhase(EnderDragonChangePhaseEvent event) {}

    public void spawn() {
        super.spawn();
        enderDragon = (EnderDragon) entity;
        enderDragon.setCustomNameVisible(false);

        entityEnderDragon = ((CraftEnderDragon) enderDragon).getHandle();
        if (entityEnderDragon.getEnderDragonBattle() != null) {
            entityEnderDragon.getEnderDragonBattle().bossBattle.setVisible(false);
        }

        enderDragon.setPhase(EnderDragon.Phase.FLY_TO_PORTAL);
    }

    public void death() {
        super.death();
        Main.plugin.getLogger().info(entityEnderDragon.dead + "");
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, ()-> {
            Main.plugin.getLogger().info(entityEnderDragon.dead + "");
        }, 20);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, ()-> {
            Main.plugin.getLogger().info(entityEnderDragon.dead + "");
        }, 150);
    }
}
