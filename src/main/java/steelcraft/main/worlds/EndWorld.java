package steelcraft.main.worlds;

import org.bukkit.*;

public class EndWorld {
    public static World world;
    public static Location spawn;

    public static void set() {
        world = Bukkit.getWorld("world_the_end");
        assert world != null;
        spawn = new Location(world, 0, 100, 0);
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
