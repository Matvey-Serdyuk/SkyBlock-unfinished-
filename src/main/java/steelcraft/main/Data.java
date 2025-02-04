package steelcraft.main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import steelcraft.auction.Auction;
import steelcraft.Island.IslandData;
import steelcraft.customEntities.Bosses.Boss;
import steelcraft.customEntities.Bosses.Bosses;
import steelcraft.customEntities.CustomEntity;
import steelcraft.Island.Island;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.skyPlayer.data.SkyPlayerData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Data {
    final private static String islandsPath = "Islands";
    final private static String playersPath = "Players";

    public static File file = new File("Data" + File.separator + "data.yml");

    public static List<Island> islands = new ArrayList<>();
    public static List<SkyPlayer> onlinePlayers = new ArrayList<>();
    public static List<SkyPlayer> players = new ArrayList<>();
    public static List<CustomEntity> aliveEntities = new ArrayList<>();

    public static void save() {
        FileConfiguration fileCon = YamlConfiguration.loadConfiguration(file);
        List <String> islandsList = new ArrayList<>();
        List <String> playersList = new ArrayList<>();

        for (Island island : islands) {
            islandsList.add(Main.gson.toJson(new IslandData(island)));
        }

        for (SkyPlayer player : players) {
            if (player.islandPlayer == null) {
                playersList.add(Main.gson.toJson(new SkyPlayerData(player)));
            }
        }

        fileCon.set(islandsPath, islandsList);
        fileCon.set(playersPath, playersList);

        Auction.save(fileCon);

        for (Boss boss : Bosses.list) {
            boss.save(fileCon);
        }

        try {
            fileCon.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void load() {
        FileConfiguration fileCon = YamlConfiguration.loadConfiguration(file);

        List <String> islandsList = fileCon.getStringList(islandsPath);
        List <String> playersList = fileCon.getStringList(playersPath);

        for (String string : islandsList) {
            new Island(Main.gson.fromJson(string, IslandData.class));
        }

        for (String string : playersList) {
            new SkyPlayer(Main.gson.fromJson(string, SkyPlayerData.class));
        }

        Auction.load(fileCon);
    }

    public static void save(FileConfiguration fileCon, String path, String str) {
        fileCon.set(path, str);

        try {
            fileCon.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SkyPlayer getSkyPlayer(String name) {
        for (SkyPlayer player : Data.players) {
            if (player.name.equals(name)) {
                return player;
            }
        }
        return null;
    }

    public static CustomEntity getCustomEntity(Entity entity) {
        if (entity instanceof Player) {
            for (SkyPlayer player : Data.onlinePlayers) {
                if (player.entity.getName().equals(entity.getName())) {
                    return player;
                }
            }
        } else {
            for (CustomEntity curEntity : Data.aliveEntities) {
                if (curEntity.entity.getUniqueId().equals(entity.getUniqueId())) {
                    return curEntity;
                }
            }
        }
        return null;
    }
}
