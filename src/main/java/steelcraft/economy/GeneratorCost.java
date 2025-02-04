package steelcraft.economy;

import org.bukkit.ChatColor;
import steelcraft.skyPlayer.SkyPlayer;

public class GeneratorCost {
    public double cost;
    public long blockCost;

    public GeneratorCost(double cost, long blockCost) {
        this.cost = cost;
        this.blockCost = blockCost;
    }

    public boolean canBuy(SkyPlayer player) {
        if (!haveEnoughBlocks(player)) {
            player.sendMessage(ChatColor.RED + "У вас не хватает блоков", true);
            return false;
        }
        if (!Economy.canBuy(player, cost, true)) {
            return false;
        }
        return true;
    }

    public boolean haveEnoughBlocks(SkyPlayer player) {
        if (player.generatorBlocks < blockCost) {
            return false;
        }
        return true;
    }
}
