package steelcraft.economy;

import org.bukkit.ChatColor;
import steelcraft.skyPlayer.SkyPlayer;

public class Economy {
    public static boolean canBuy(SkyPlayer player, double cost, boolean send) {
        if (player.money >= cost) {
            if (send) {
                player.sendMessage(ChatColor.GREEN + "Вы совершили покупку", true);
            }
            return true;
        }
        if (send) {
            player.sendMessage(ChatColor.RED + "У вас недостаточно денег!", true);
        }
        return false;
    }
}
