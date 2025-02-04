package steelcraft.auction;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import steelcraft.auction.auctionMenu.MainAuctionMenu;
import steelcraft.commands.AbstractCommand;
import steelcraft.main.Data;
import steelcraft.skyPlayer.SkyPlayer;

import java.util.List;

public class AuctionCommand extends AbstractCommand {
    public static List<String> firstArgs = Lists.newArrayList("sell");
    public static List<String> ahArgs = Lists.newArrayList("10", "100", "500", "1000", "5000");

    public AuctionCommand() {
        super("ah");
    }

    @Override
    public boolean execute(CommandSender sender, String str, String[] args) {
        SkyPlayer player = Data.getSkyPlayer(sender.getName());
        if (player == null) {return false;}

        if (args.length == 0) {
            new MainAuctionMenu().open(player);
            return true;
        }

        if (args[0].equals(firstArgs.get(0))) {
            if (args.length == 2) {
                if (Auction.getItemsPlayer(player).size() == Auction.maxSlotPlayer) {
                    player.sendMessage(ChatColor.YELLOW + "У вас выставлено максимальное количество предметов!", true);
                    return true;
                }

                ItemStack item = player.bukkitPlayer.getInventory().getItemInMainHand();
                if (item.getType() == Material.AIR) {
                    player.sendMessage(ChatColor.RED + "Возьмите предмет, который хотите продать в руки!", true);
                    return true;
                }

                double cost;

                try {
                    cost = Double.parseDouble(args[1]);
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "Не правильно введена цена!", true);
                    return true;
                }

                Auction.addItem(player, item.clone(), cost);

                player.sendMessage(ChatColor.GREEN + "Вы выставили на аукцион " + item.getItemMeta().getDisplayName() +
                        ChatColor.GREEN + " за " + cost + ChatColor.DARK_GREEN + "$", true);

                item.setAmount(0);
            } else {
                player.sendMessage(wrong, true);
            }
        }

        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) { return firstArgs; }
        if (args[0].equals(firstArgs.get(0)) && args.length == 2) { return ahArgs; }
        return Lists.newArrayList();
    }
}
