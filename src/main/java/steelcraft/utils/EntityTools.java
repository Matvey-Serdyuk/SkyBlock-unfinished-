package steelcraft.utils;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import steelcraft.main.worlds.MainWorld;

public class EntityTools {
    public static void spawnExp(int exp, Location location) {
        if (location.getWorld() == null) {location.setWorld(MainWorld.world);}
        ExperienceOrb orb = (ExperienceOrb) location.getWorld().spawnEntity(location, EntityType.EXPERIENCE_ORB);
        orb.setExperience(exp);
    }
}
