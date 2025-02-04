package steelcraft.customEntities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import steelcraft.main.Main;

public class Summon extends CustomEntity {
    public int lifeTime;

    public Summon(EntityType type, String name, String tag, double HP, double damage, double regen, double armor,
                  double speed, ItemStack[] itemArmors, ItemStack itemInHand, boolean AI, int lifeTime) {
        super(type, name, tag, HP, damage, regen, armor, speed, itemArmors, itemInHand, AI);

        this.lifeTime = lifeTime;
    }

    public Summon copy() {
        return new Summon(type, name, tag, HP, damage, regen, armor, speed, itemArmors, itemInHand, AI, lifeTime);
    }

    public void spawn(Location location) {
        super.spawn(location);

        Runnable run = this::remove;

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, run, lifeTime);
    }
}
