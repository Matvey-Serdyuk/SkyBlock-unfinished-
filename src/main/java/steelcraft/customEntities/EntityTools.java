package steelcraft.customEntities;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class EntityTools {
    public static void deleteByTag(World world, String tag) {
        for (Entity entity : world.getEntities()) {
            if (entity.getScoreboardTags().contains(tag)) {
                entity.remove();
            }
        }
    }

    public static boolean damage(LivingEntity livingEntity, double damage) {
        if (livingEntity.getAttribute(Attribute.GENERIC_ARMOR) != null) {
            damage *= (1f - Math.min(20, livingEntity.getAttribute(Attribute.GENERIC_ARMOR).getValue()) / 25);
        } else {
            damage *= 1f - 20f / 25f;
        }
        livingEntity.damage(damage);
        return true;
    }

    public static boolean checkRange(Location loc, Location location, double range) {
        if (Math.abs(loc.getX() - location.getX()) < range && Math.abs(loc.getY() - location.getY()) < range &&
                Math.abs(loc.getZ() - location.getZ()) < range) {
            return true;
        }
        return false;
    }
}
