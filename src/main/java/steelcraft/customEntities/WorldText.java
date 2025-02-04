package steelcraft.customEntities;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import steelcraft.main.Main;

public class WorldText {
    public Location location;
    public String[] strings;
    public ArmorStand[] stands;

    public WorldText(Location location, String[] strings) {
        this.location = location;
        this.strings = strings;

        spawn();
    }

    public void spawn() {
        stands = new ArmorStand[strings.length];
        for (int i = 0; i < strings.length; i++) {
            assert location.getWorld() != null;
            location.getWorld().getChunkAt(location).setForceLoaded(true);
            Main.spawn = true;
            stands[i] = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            Main.spawn = false;
            stands[i].setVisible(false);
            stands[i].setSmall(true);
            stands[i].setGravity(false);
            stands[i].setAI(false);
            stands[i].setCustomName(strings[i]);
            stands[i].setCustomNameVisible(true);
        }
    }

    public void update(int ind, String str) {
        if (ind >= stands.length) {return;}
        if (stands[ind] == null) {return;}

        stands[ind].setCustomName(str);
    }

    public void deSpawn() {
        for (int i = 0; i < stands.length; i++) {
            stands[i].remove();
            stands[i] = null;
        }
    }
}
