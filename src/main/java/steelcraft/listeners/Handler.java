package steelcraft.listeners;

import org.bukkit.Bukkit;
import steelcraft.main.Main;

public class Handler {
    public static void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new InventoryEvents(), Main.plugin);
        Bukkit.getPluginManager().registerEvents(new BlockEvents(), Main.plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerEvents(), Main.plugin);
        Bukkit.getPluginManager().registerEvents(new EntityEvents(), Main.plugin);
        Bukkit.getPluginManager().registerEvents(new EntityDamageEvents(), Main.plugin);
        Bukkit.getPluginManager().registerEvents(new WorldEvents(), Main.plugin);
    }
}
