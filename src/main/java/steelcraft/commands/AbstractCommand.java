package steelcraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import steelcraft.main.Main;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {
    public String wrong = ChatColor.RED + "Не правильно введена команда!";
    public String notPermission = ChatColor.RED + "У вас недостаточно прав!";

    public AbstractCommand(String command) {
        PluginCommand pluginCommand = Main.plugin.getCommand(command);
        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        }
    }

    public abstract boolean execute(CommandSender sender, String str, String[] args);

    public List <String> complete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
        if (execute(sender, str, args)) {
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String str, String[] args) {
        return filter(complete(sender, args), args);
    }

    private List<String> filter(List <String> list, String[] args) {
        if (list == null) {return null;}
        String last = args[args.length - 1];
        List <String> result = new ArrayList<>();
        for (String arg : list) {
            if (arg.toLowerCase().startsWith(last.toLowerCase())) { result.add(arg); }
        }
        return result;
    }
}
