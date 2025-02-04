package steelcraft.main.worlds;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;

public class SpawnWorld {
    public static World world;

    public static void set() {
        WorldCreator worldCreator = new WorldCreator("spawn");
        worldCreator.createWorld();
        world = Bukkit.getWorld("spawn");
        assert world != null;
        WorldBorder border = world.getWorldBorder();
        border.setCenter(0, 0);
        border.setSize(500);
        for (int x = -20; x <= 20; x++) {
            for (int z = -20; z <= 20; z++) {
                world.setChunkForceLoaded(x, z, true);
            }
        }
    }
}
