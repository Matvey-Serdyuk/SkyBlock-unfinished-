package steelcraft.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import steelcraft.customEnchants.CustomEnchant;

public class Ec extends AbstractCommand {
    public Ec() {
        super("ec");
    }

    @Override
    public boolean execute(CommandSender sender, String str, String[] args) {
        Player p = (Player) sender;
        CustomEnchant enc = CustomEnchant.enchants.get(CustomEnchant.enchants.size() - 1);
        p.getInventory().addItem(enc.getEnchant(enc.maxLevel));
        enc = CustomEnchant.enchants.get(CustomEnchant.enchants.size() - 2);
        p.getInventory().addItem(enc.getEnchant(enc.maxLevel));
        enc = CustomEnchant.enchants.get(CustomEnchant.enchants.size() - 3);
        p.getInventory().addItem(enc.getEnchant(enc.maxLevel));
        return true;
    }
}
