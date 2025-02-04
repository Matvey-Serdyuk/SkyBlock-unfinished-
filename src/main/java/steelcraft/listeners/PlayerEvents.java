package steelcraft.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import steelcraft.Case.Case;
import steelcraft.main.Data;
import steelcraft.blocks.Generator;
import steelcraft.blocks.Spawner;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.streams.StreamManager;

import java.util.Arrays;
import java.util.List;

public class PlayerEvents implements Listener {
    final List<Material> confidentBlocks = Arrays.asList(Material.CHEST, Material.CHEST_MINECART, Material.TRAPPED_CHEST,
            Material.FURNACE, Material.FURNACE_MINECART, Material.HOPPER, Material.HOPPER_MINECART, Material.DISPENSER);
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        SkyPlayer player = Data.getSkyPlayer(event.getPlayer().getName());
        if (player == null) {
            player = new SkyPlayer(event.getPlayer());
        }

        if (Bukkit.getOnlinePlayers().size() == 1) {
            StreamManager.start();
        }

        player.join(event.getPlayer());
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event) {
        SkyPlayer player = Data.getSkyPlayer(event.getPlayer().getName());
        if (player == null) {return;}

        if (Bukkit.getOnlinePlayers().size() == 1) {
            StreamManager.stop();
        }

        player.quit();
    }

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event) {
        Location location = event.getFrom(), location1 = event.getTo();
        if (location1 == null) {return;}
        if (location.getX() == location1.getX() && location.getZ() == location1.getZ()) {return;}
        SkyPlayer player = Data.getSkyPlayer(event.getPlayer().getName());
        if (player == null) { return; }
        player.move(event);
    }

    @EventHandler
    public void interactEvent(PlayerInteractEvent event) {
        SkyPlayer player = Data.getSkyPlayer(event.getPlayer().getName());
        if (player == null) {event.setCancelled(true);return;}

        if (event.getClickedBlock() != null) {
            if (Case.action(player, event)) {
                return;
            }

            if (player.islandPlayer == null) {
                if (confidentBlocks.contains(event.getClickedBlock().getType())) {
                    event.setCancelled(true);
                }
                return;
            }
            if (!player.islandPlayer.island.area.checkBlock(event.getClickedBlock().getLocation())) {
                if (confidentBlocks.contains(event.getClickedBlock().getType())) {
                    event.setCancelled(true);
                }
                return;
            }
            if (Spawner.action(player, event)) {
                return;
            }

            Generator.action(player, event);
        }
    }
}
