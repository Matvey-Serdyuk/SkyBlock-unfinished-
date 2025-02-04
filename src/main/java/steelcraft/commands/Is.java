package steelcraft.commands;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import steelcraft.Island.IslandInvitation;
import steelcraft.skyPlayer.IslandPlayer;
import steelcraft.main.Data;
import steelcraft.menu.ConfirmMenu;
import steelcraft.menu.UpgradeIslandMenu;
import steelcraft.Island.IslandManager;
import steelcraft.skyPlayer.SkyPlayer;

import java.util.ArrayList;
import java.util.List;

public class Is extends AbstractCommand {
    public static List <String> firstArgs = Lists.newArrayList("create", "upgrade", "invite", "accept", "leave",
            "kick");

    public Is() {
        super("is");
    }

    @Override
    public boolean execute(CommandSender sender, String str, String[] args) {
        Player p = (Player) sender;
        SkyPlayer player = Data.getSkyPlayer(p.getName());
        assert player != null;

        if (args.length == 1) {
            if (args[0].equals("create")) {
                // "is create"
                if (player.islandPlayer == null) {
                    IslandManager.createIsland(player);
                } else {
                    player.sendMessage(ChatColor.RED + "У вас уже есть остров!", true);
                }
                return true;
            }
            if (args[0].equals("accept")) {
                // "is accept"
                if (player.invitation != null) {
                    player.invitation.stop();
                    player.invitation.island.addPlayer(player);
                    player.invitation = null;
                } else {
                    player.sendMessage(ChatColor.RED + "У вас нету приглашений!", true);
                }
                return true;
            }
        }

        if (player.islandPlayer == null) {
            player.sendMessage(ChatColor.RED + "У вас нет острова!", true);
            player.sendMessage(ChatColor.YELLOW + "Cоздайте свой остров или вступите в чей-то.", true);
            return true;
        }

        if (args.length == 0) {
            player.teleport(player.islandPlayer.island.spawn);
            return true;
        }

        if (args.length == 1) {
            if (args[0].equals("upgrade")) {
                // is upgrade
                new UpgradeIslandMenu(player.islandPlayer.island).open(player);
            } else if (args[0].equals("leave")) {
                new ConfirmMenu(ChatColor.YELLOW + "Вы хотите выйти с острова?", ()-> {
                    player.islandPlayer.island.removePlayer(player.islandPlayer);
                }).open(player);
            }
            return true;
        }

        if (args.length == 2) {
            if (!player.islandPlayer.owner) {
                player.sendMessage(notPermission, true);
                return true;
            }
            Player bukkitCurPlayer = Bukkit.getPlayer(args[1]);
            if (bukkitCurPlayer == null) {
                player.sendMessage(ChatColor.RED + "Игрок не был найден!", true);
                return true;
            }

            if (bukkitCurPlayer.getName().equals(player.name)) {
                player.sendMessage(ChatColor.YELLOW + "Что?", true);
            }

            SkyPlayer curPlayer = Data.getSkyPlayer(bukkitCurPlayer.getName());
            assert curPlayer != null;

            if (args[0].equals("invite")) {
                // "is invite"
                if (player.islandPlayer.island.isFull()) {
                    player.sendMessage(ChatColor.RED + "На острове нету свободных мест!", true);
                    return true;
                }
                if (curPlayer.islandPlayer != null) {
                    curPlayer.sendMessage(ChatColor.RED + "У этого игрока уже есть остров!", true);
                    return true;
                }
                new IslandInvitation(curPlayer, player, player.islandPlayer.island, 60000);
            } else if (args[0].equals("kick")) {
                if (curPlayer.islandPlayer == null) {
                    player.sendMessage(ChatColor.RED + "Игрок не был найден!", true);
                } else if (player.islandPlayer.island.players.contains(curPlayer.islandPlayer)) {
                    player.islandPlayer.island.removePlayer(curPlayer.islandPlayer);
                } else {
                    player.sendMessage(ChatColor.RED + "Игрок не был найден!", true);
                }
            }
            return true;
        }

        player.sendMessage(wrong, true);

        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) { return firstArgs; }
        if (args.length == 2 && args[0].equals("invite")) {return null;}
        SkyPlayer player = Data.getSkyPlayer(sender.getName());
        if (player == null) {return null;}
        if (args.length == 2 && args[0].equals("kick")) {
            if (player.islandPlayer == null) {return null;}
            List <String> out = new ArrayList<>();
            for (IslandPlayer islandPlayer : player.islandPlayer.island.players) {
                out.add(islandPlayer.skyPlayer.name);
            }
            return out;
        }
        return Lists.newArrayList();
    }
}
