package steelcraft.Island;

import org.bukkit.Location;
import org.bukkit.block.Block;
import steelcraft.main.worlds.MainWorld;

import java.util.ArrayList;
import java.util.List;

public class Area {
    public Location center;
    public int size;

    public Location start;
    public Location end;

    public List<Block> blocks = new ArrayList<>();

    public Area(Location center, int size) {
        this.center = center;
        this.size = size;
        Location start = new Location(center.getWorld(),
                center.getX() - size, center.getY() - size, center.getZ() - size);
        Location end = new Location(center.getWorld(),
                center.getX() + size, center.getY() + size, center.getZ() + size);
        
        set(start, end);
    }

    public Area(Location start, Location end) {
        center = null;
        set(start, end);
    }

    public void set(Location start, Location end) {
        long startX, startY, startZ, endX, endY, endZ;
        if (start.getBlockX() <= end.getBlockX()) {
            startX = start.getBlockX();
            endX = end.getBlockX();
        } else {
            startX = end.getBlockX();
            endX = start.getBlockX();
        }
        if (start.getBlockY() <= end.getBlockY()) {
            startY = start.getBlockY();
            endY = end.getBlockY();
        } else {
            startY = end.getBlockY();
            endY = end.getBlockY();
        }
        if (start.getBlockZ() <= end.getBlockZ()) {
            startZ = start.getBlockZ();
            endZ = end.getBlockZ();
        } else {
            startZ = end.getBlockZ();
            endZ = start.getBlockZ();
        }

        if (start.getWorld() == null) {start.setWorld(MainWorld.world);}
        if (end.getWorld() == null) {end.setWorld(MainWorld.world);}

        this.start = new Location(start.getWorld(), startX, startY, startZ);
        this.end = new Location(end.getWorld(), endX, endY, endZ);

        for (int x = start.getBlockX(); x <= end.getBlockX(); x++) {
            for (int y = start.getBlockY(); y <= end.getBlockY(); y++) {
                for (int z = start.getBlockZ(); z <= end.getBlockZ(); z++) {
                    blocks.add(start.getWorld().getBlockAt(x, y, z));
                }
            }
        }
    }

    public boolean checkBlock(Location location) {
        assert start.getWorld() != null;
        if (!start.getWorld().equals(location.getWorld())) {return false;}
        if (location.getX() >= start.getX() && location.getY() >= start.getY() && location.getZ() >= start.getZ() &&
        location.getX() <= end.getBlockX() && location.getY() <= end.getBlockY() && location.getZ() <= end.getBlockZ()) {
            return true;
        }
        return false;
    }

    public boolean checkArea(Area area) {
        if (!area.start.getWorld().equals(start.getWorld())) {return false;}
        if (start.getBlockX() <= area.start.getBlockX() && end.getBlockX() >= area.end.getBlockX() &&
                start.getBlockY() <= area.start.getBlockY() && end.getBlockY() >= area.end.getBlockY() &&
                start.getBlockZ() <= area.start.getBlockZ() && end.getBlockZ() >= area.end.getBlockZ()) {
            return true;
        }
        return false;
    }

    public Location getRandomLocation() {
        return new Location(start.getWorld(), start.getX() + Math.random() * (end.getX() - start.getX()),
                start.getY() + Math.random() * (end.getY() - start.getY()),
                start.getZ() + Math.random() * (end.getZ() - start.getZ()));
    }
}
