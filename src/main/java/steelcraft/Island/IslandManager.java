package steelcraft.Island;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import steelcraft.main.worlds.MainWorld;
import steelcraft.skyPlayer.IslandPlayer;
import steelcraft.main.Data;
import steelcraft.skyPlayer.SkyPlayer;

public class IslandManager {
    private static int distance = 500;

    public static void createIsland(SkyPlayer player) {
        int n = (int) (Math.sqrt(Data.islands.size())) + 1;
        World world = MainWorld.world;
        int x, y = 100, z;
        Location location;
        boolean next;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                x = 600 + i * distance;
                z = 600 + j * distance;
                location = new Location(world, x, y, z);
                next = false;
                for (Island island : Data.islands) {
                    if (island.center.equals(location)) {
                        next = true;
                        break;
                    }
                }
                if (next) {
                    continue;
                }
                Block block = world.getBlockAt(location);
                block.setType(Material.CHEST);
                Chest chest = (Chest) block.getState();
                chest.getBlockInventory().setItem(12, new ItemStack(Material.LAVA_BUCKET));
                chest.getBlockInventory().setItem(14, new ItemStack(Material.WATER_BUCKET));

                world.getBlockAt(x, y - 1, z).setType(Material.BEDROCK);
                player.islandPlayer = new IslandPlayer(new Island(player, location, new Location(world, x+0.5, y+1, z+0.5)),
                        player, true);
                player.islandPlayer.island.players.add(player.islandPlayer);
                if (player.online) {
                    player.islandPlayer.island.onlinePlayers.add(player.islandPlayer);
                }
                return;
            }
        }
    }
}
