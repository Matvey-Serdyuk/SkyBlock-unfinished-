package steelcraft.skyPlayer;

import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import steelcraft.Island.IslandInvitation;
import steelcraft.customEntities.Bosses.Bar;
import steelcraft.customEntities.CustomEntity;
import steelcraft.main.Data;
import steelcraft.main.Main;
import steelcraft.menu.Menu;
import steelcraft.skyPlayer.data.SkyPlayerData;
import steelcraft.utils.Format;

import java.util.ArrayList;
import java.util.List;

public class SkyPlayer extends CustomEntity {
    public String name;
    public Player bukkitPlayer;
    public double money;
    public long generatorBlocks;

    public IslandPlayer islandPlayer = null;

    public List<Bar> bars = new ArrayList<>();
    public Menu openMenu = null;
    public Board board;

    public IslandInvitation invitation = null;

    public boolean openCase = false;
    public boolean online = false;
    public boolean isTicking = false;

    public SkyPlayer(Player player) {
        super(EntityType.PLAYER, player.getName(), "Player", 20, 1, 1, 0, 1,
                null, null, false);
        this.name = player.getName();
        this.bukkitPlayer = player;
        this.money = 0;
        this.generatorBlocks = 0;

        this.pvp = true;

        entity = (Entity) player;
        livingEntity = (LivingEntity) player;

        Data.players.add(0, this);

        board = new Board("SkyBlock");

        board.setScore(" ", 4);
        board.setScore("§7| Статистика", 3);
        board.setScore("§7| Сервер", 1);
    }

    public SkyPlayer(SkyPlayerData data) {
        super(EntityType.PLAYER, data.name, "Player", 20, 1, 0, 0, 1,
                null, null, false);

        name = data.name;
        money = data.money;
        pvp = data.pvp;

        board = new Board("SkyBlock");

        board.setScore(" ", 4);
        board.setScore("§7| Статистика", 3);
        board.setScore("§7| Сервер", 1);

        Data.players.add(this);
    }

    public void updateBoard() {
        int online = Bukkit.getOnlinePlayers().size();
        board.setScore("§7• Деньги  §f>> §a" + Format.pointNumber(money) + "$", 2);

        board.setScore("§7• Онлайн  §f>> §2" + online, 0);
        bukkitPlayer.setPlayerListFooter(ChatColor.GOLD + "Онлайн: " + ChatColor.GRAY + online);
    }

    public void changeMoney(double amount, boolean send) {
        money += amount;

        if (!send) {return;}

        if (amount < 0) {
            sendMessage(ChatColor.RED + "-" + Format.getMoney(amount * -1), true);
        } else {
            sendMessage(ChatColor.GREEN + "+" + Format.getMoney(amount), true);
        }
    }

    public void changeExp(int amount, boolean send) {
        bukkitPlayer.setTotalExperience(bukkitPlayer.getTotalExperience() + amount);

        if (!send) {return;}

        if (amount < 0) {
            sendMessage(ChatColor.RED + "-" + Format.pointNumber(amount) + ChatColor.YELLOW + "exp", true);
        } else {
            sendMessage(ChatColor.GREEN + "+" + Format.pointNumber(amount) + ChatColor.YELLOW + "exp", true);
        }
    }

    public void move(PlayerMoveEvent event) {

    }

    public void teleport(Location location) {
        bukkitPlayer.teleport(location);
    }

    public void death() {
        for (Bar bar : bars) {
            if (bar.type == Bar.BarType.BOSS_BAR) {
                bar.removePlayer(this);
            }
        }
    }

    public void join(Player player) {
        Main.plugin.getLogger().info("" + isTicking);

        this.bukkitPlayer = player;
        entity = (Entity) player;
        livingEntity = (LivingEntity) player;

        Data.onlinePlayers.add(0, this);
        Data.aliveEntities.add(this);

        Data.players.remove(this);
        Data.players.add(0, this);

        online = true;

        if (islandPlayer != null) {
            islandPlayer.island.playerJoinServer(this);
        }

        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
        updateBoard();
        player.setScoreboard(board.getScoreboard());

    }

    public void quit() {
        Data.aliveEntities.remove(this);
        Data.onlinePlayers.remove(this);

        online = false;

        if (islandPlayer != null) {
            islandPlayer.island.playerQuitServer(this);
        }
    }

    public void sendMessage(String[] message, boolean byServer) {
        if (!online) {return;}
        if (byServer) {
            message[0] = Main.serverMessage + message[0];
        }
        bukkitPlayer.sendMessage(message);
    }

    public void sendMessage(String message, boolean byServer){
        if (!online) {return;}
        if (byServer) {
            message = Main.serverMessage + message;
        }
        bukkitPlayer.sendMessage(message);
    }
}
