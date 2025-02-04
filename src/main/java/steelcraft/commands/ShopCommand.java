package steelcraft.commands;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import steelcraft.main.Data;
import steelcraft.menu.Catalog;
import steelcraft.menu.shop.ShopMenu;

import java.util.List;

public class ShopCommand extends AbstractCommand {

    public ShopCommand() {
        super("shop");
    }

    @Override
    public boolean execute(CommandSender sender, String str, String[] args) {
        Player player = (Player) sender;
        new Catalog().open(Data.getSkyPlayer(player.getName()));
        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        return Lists.newArrayList();
    }
}
