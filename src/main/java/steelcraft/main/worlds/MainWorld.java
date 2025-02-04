package steelcraft.main.worlds;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class MainWorld {
    public static World world;

    public static void set() {
        world = Bukkit.getWorld("world");
        assert world != null;
        WorldBorder border = world.getWorldBorder();
        border.setCenter(0, 0);
        border.setSize(100000);
    }
}
