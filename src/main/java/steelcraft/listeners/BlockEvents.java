package steelcraft.listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.inventory.ItemStack;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.customEnchants.echants.Smash;
import steelcraft.main.Data;
import steelcraft.main.Main;
import steelcraft.blocks.Generator;
import steelcraft.blocks.MagicBlock;
import steelcraft.blocks.Spawner;
import steelcraft.skyPlayer.SkyPlayer;

import java.util.HashMap;

public class BlockEvents implements Listener {
    @EventHandler
    public void blockBreakByPlayerEvent(BlockBreakEvent event) {
        SkyPlayer player = Data.getSkyPlayer(event.getPlayer().getName());
        if (player.bukkitPlayer.isOp() && player.bukkitPlayer.getGameMode() == GameMode.CREATIVE) {return;}
        event.setCancelled(true);
        if (player == null) {return;}
        if (player.islandPlayer == null) {return;}

        if (!breakBlockEvent(player, event)) {return;}
        event.setCancelled(false);

        HashMap<CustomEnchant, Integer> map = CustomEnchant.getEnchants(event.getPlayer());
        ItemStack item;

        Smash smash = (Smash) CustomEnchant.enchants.get(CustomEnchant.enchants.size() - 1);
        for (CustomEnchant enc : map.keySet()) {
            if (enc.name.equals(smash.name) && smash.mine) {smash.mine = false; continue;}
            enc.blockBreak(event, map.get(enc));
        }
    }

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent event) {
        SkyPlayer player = Data.getSkyPlayer(event.getPlayer().getName());
        if (player == null) { return; }
        event.setCancelled(true);
        if (player.islandPlayer == null || !player.islandPlayer.island.area.checkBlock(event.getBlock().getLocation())) {return;}
        event.setCancelled(false);
        boolean haveLore = false;
        if (event.getItemInHand().getItemMeta() != null) {
            if (event.getItemInHand().getItemMeta().getLore() != null) {
                haveLore = true;
            }
        }

        if (Spawner.blockPlace(player, event)) {return;}
        if (Generator.blockPlace(player, event, haveLore)) {return;}
    }

    @EventHandler
    public void blockPistonEvent(BlockPistonExtendEvent event) {
        for (Block block : event.getBlocks()) {
            if (block.getType() == Material.PLAYER_HEAD) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void blockEvent(BlockFromToEvent event) {
        Material type = event.getToBlock().getType();
        if (type == Material.END_PORTAL || type == Material.BEDROCK) {event.setCancelled(true);}
    }

    public boolean breakBlockEvent(SkyPlayer player, BlockBreakEvent event) {
        Block block = event.getBlock();

        if (!player.islandPlayer.island.area.checkBlock(event.getBlock().getLocation())) {return false;}

        if (Spawner.blockBreak(player, event)) {return true;}
        if (Generator.blockBreak(player, event)) {return true;}

        if (Generator.checkBlock(player, event)) {return true;}

        if (block.getLocation().equals(player.islandPlayer.island.center)) {
            final Location location = block.getLocation();
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                MagicBlock.place(location, 0);
            }, 0);
        }
        return true;
    }
}
