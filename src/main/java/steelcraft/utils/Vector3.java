package steelcraft.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import steelcraft.main.worlds.MainWorld;

public class Vector3 {
    public String worldName;
    public long x;
    public long y;
    public long z;

    public Vector3(String worldName, long x, long y, long z) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Location location) {
        if (location.getWorld() == null) {location.setWorld(MainWorld.world);}
        this.worldName = location.getWorld().getName();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }
}
