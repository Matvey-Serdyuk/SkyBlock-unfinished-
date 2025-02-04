package steelcraft.main;

import com.google.gson.Gson;
import org.bukkit.*;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import steelcraft.Case.Case;
import steelcraft.auction.AuctionCommand;
import steelcraft.commands.*;
import steelcraft.economy.RiseCost;
import steelcraft.listeners.Handler;
import steelcraft.blocks.Spawner;
import steelcraft.main.worlds.EndWorld;
import steelcraft.main.worlds.MainWorld;
import steelcraft.main.worlds.SpawnWorld;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.streams.SecondStream;
import steelcraft.streams.StreamManager;

public final class Main extends JavaPlugin {
    public static String serverMessage = "|" + ChatColor.GOLD + "SkyBlock" + ChatColor.WHITE + "|" + ChatColor.GOLD +
            " >> " + ChatColor.GRAY;


    public static Main plugin;
    public static String nameWorld = "world";
    public static Gson gson = new Gson();
    public static boolean spawn = false;

    public static World[] worlds;

    public static SecondStream secondStream = new SecondStream(1000);

    @Override
    public void onEnable() {
        MainWorld.set();
        SpawnWorld.set();
        EndWorld.set();

        worlds = new World[] {MainWorld.world, SpawnWorld.world, EndWorld.world};

        plugin = this;
        Handler.registerEvents();

        new Is();
        new ShopCommand();
        new Ec();
        new TestCommand();
        new Bs();
        new AuctionCommand();

        Case.set();
        RiseCost.setAll();

        Data.load();
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(ChatColor.RED + "Сервер перезагружается!");
        }

        for (Spawner spawner : Spawner.getAllSpawners()) {
            spawner.deSpawn();
            if (spawner.tickStream.isWork) {
                spawner.tickStream.stop();
            }
        }

        for (World world : worlds) {
            for (Entity entity : world.getEntities()) {
                entity.remove();
            }
        }

        if (StreamManager.isWork) {
            StreamManager.stop();
        }

        Data.save();
    }

    public static void reload() {
        extracted();
    }

    private static void extracted() {
        Bukkit.getServer().reloadData();
        Bukkit.getServer().reload();
    }

    public static void allMessage(String msg) {
        for (SkyPlayer player : Data.onlinePlayers) {
            player.sendMessage(msg, true);
        }
    }

    public static void allMessage(String[] msg) {
        for (SkyPlayer player : Data.onlinePlayers) {
            player.sendMessage(msg, true);
        }
    }
}
