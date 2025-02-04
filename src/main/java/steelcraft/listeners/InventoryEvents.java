package steelcraft.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.main.Data;
import steelcraft.skyPlayer.SkyPlayer;

public class InventoryEvents implements Listener {
    @EventHandler
    public void clickEvent(InventoryClickEvent event) {
        SkyPlayer player = Data.getSkyPlayer(event.getWhoClicked().getName());
        if (player == null) { return; }
        if (player.openMenu == null) {
            if (CustomEnchant.canOverlay(event)) {
                for (CustomEnchant enc : CustomEnchant.enchants) {
                    if (enc.overlay(player, event.getCurrentItem(), player.bukkitPlayer.getItemOnCursor())) {
                        return;
                    };
                }
            }
            return;
        }

        event.setCancelled(!player.openMenu.canTake);
        player.openMenu.clickEvent(event);
    }

    @EventHandler
    public void closeEvent(InventoryCloseEvent event) {
        SkyPlayer player = Data.getSkyPlayer(event.getPlayer().getName());
        if (player.openMenu != null) {
            player.openMenu.close();
        }
    }
}
