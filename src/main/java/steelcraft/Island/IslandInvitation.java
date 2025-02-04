package steelcraft.Island;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.main.Main;
import steelcraft.streams.Task;

public class IslandInvitation extends Task {
    public Island island;
    public SkyPlayer player;
    public SkyPlayer sendingPlayer;

    public IslandInvitation(SkyPlayer player, SkyPlayer sendingPlayer, Island island, long delay) {
        super(delay);
        this.island = island;
        this.sendingPlayer = sendingPlayer;
        this.player = player;

        player.invitation = this;
        player.sendMessage(ChatColor.YELLOW + "Игрок " + sendingPlayer.name + " " +
                ChatColor.YELLOW + "отправил вам приглашение на остров " + island.name, true);
        player.sendMessage(ChatColor.YELLOW + "Что бы принять приглашение пропишите " +
                ChatColor.BLUE + "/is accept", true);

        sendingPlayer.sendMessage(ChatColor.YELLOW + "Вы отправили приглашение на остров игроку "
                + player.name, true);
    }

    @Override
    public void run() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, ()-> {
            if (player.invitation == this) {
                player.invitation = null;
                if (player.online) {
                    player.sendMessage(ChatColor.YELLOW + "Время приглашения на остров " +
                            island.name + ChatColor.RED + " окончено", true);
                }
            }
        });
    }
}
