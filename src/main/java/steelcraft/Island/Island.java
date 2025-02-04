package steelcraft.Island;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import steelcraft.blocks.data.GeneratorData;
import steelcraft.blocks.data.SpawnerData;
import steelcraft.main.Data;
import steelcraft.blocks.Generator;
import steelcraft.blocks.Spawner;
import steelcraft.skyPlayer.IslandPlayer;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.main.Main;
import steelcraft.skyPlayer.data.IslandPlayerData;

import java.util.ArrayList;
import java.util.List;

public class Island {
    final public static int startMaxPlayers = 5;
    final public static int startMaxSpawners = 5;
    final public static int startSize = 5;

    final static String islandMessage = "|" + ChatColor.GOLD + "Island" + ChatColor.WHITE + "|" + ChatColor.GOLD +
            " >> " + ChatColor.GRAY;

    public String name;
    public int maxPlayers;
    public int maxSpawners;
    public List<IslandPlayer> players = new ArrayList<>();
    public Location center;
    public Location spawn;

    public Area area;

    public List<IslandPlayer> onlinePlayers = new ArrayList<>();
    public List<Spawner> spawners = new ArrayList<>();
    public List<Generator> generators = new ArrayList<>();

    public Island(SkyPlayer player, Location center, Location spawn) {
        this.name = "Остров " + player.name;
        this.maxPlayers = startMaxPlayers;
        this.maxSpawners = startMaxPlayers;
        this.center = center;
        this.spawn = spawn;
        area = new Area(center, startSize);

        player.sendMessage(ChatColor.GREEN + "Остров успешно создан!", true);

        Data.islands.add(this);
    }

    public Island(IslandData data) {
        name = data.name;
        maxPlayers = data.maxPlayers;
        maxSpawners = data.maxSpawners;
        center = data.center.getLocation();
        spawn = data.spawn.getLocation();
        area = new Area(center, data.size);

        for (IslandPlayerData playerData : data.players) {
            new IslandPlayer(this, playerData);
        }

        for (SpawnerData spawnerData : data.spawners) {
            spawners.add(new Spawner(spawnerData));
        }

        for (GeneratorData generator : data.generators) {
            generators.add(new Generator(generator));
        }

        Data.islands.add(this);
    }

    public void setSpawn(Player p) {
        spawn = p.getLocation();
    }

    public void playerJoinServer(SkyPlayer player) {
        onlinePlayers.add(player.islandPlayer);
        sendMessage(ChatColor.YELLOW + "Игрок " + player.name + ChatColor.YELLOW + " зашел на сервер");
    }

    public void playerQuitServer(SkyPlayer player) {
        onlinePlayers.remove(player.islandPlayer);
        sendMessage(ChatColor.YELLOW + "Игрок " + player.name + ChatColor.YELLOW + " вышел с сервера");
    }

    public void addPlayer(SkyPlayer player) {
        player.islandPlayer = new IslandPlayer(this, player, false);
        if (player.online) {
            onlinePlayers.add(player.islandPlayer);
        }
        sendMessage(ChatColor.GREEN + "Игрок " + player.name + ChatColor.GREEN + " присоединился к острову");
    }

    public void removePlayer(IslandPlayer player) {
        players.remove(player);
        onlinePlayers.remove(player);
        player.skyPlayer.islandPlayer = null;
        if (players.isEmpty()) {
            Data.islands.remove(this);
        } else if (getOwners() == 0) {
            players.get(0).owner = true;
        }
        sendMessage(ChatColor.RED + "Игрок " + player.skyPlayer.name + ChatColor.RED + " вышел с острова");
    }

    public void sendMessage(String[] message) {
        message[0] = islandMessage + message[0];
        for (IslandPlayer player : onlinePlayers) {
            player.skyPlayer.sendMessage(message, false);
        }
    }

    public void sendMessage(String message) {
        message = islandMessage + message;
        for (IslandPlayer player : onlinePlayers) {
            player.skyPlayer.sendMessage(message, false);
        }
    }

    public int getOwners() {
        int out = 0;
        if (players != null) {
            for (IslandPlayer player : players) {
                if (player.owner) {
                    out++;
                }
            }
        }
        return out;
    }

    public boolean isFull() {
        return players.size() == maxPlayers;
    }
}
