package steelcraft.commands;

import org.bukkit.command.CommandSender;
import steelcraft.main.Data;
import steelcraft.menu.BsMenu;
import steelcraft.skyPlayer.SkyPlayer;

public class Bs extends AbstractCommand{
    public Bs() {
        super("bs");
    }

    @Override
    public boolean execute(CommandSender sender, String str, String[] args) {
        SkyPlayer player = Data.getSkyPlayer(sender.getName());
        new BsMenu().open(player);
        return true;
    }
}
