package steelcraft.commands;


import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import steelcraft.Case.Case;
import steelcraft.main.Data;
import steelcraft.blocks.Generator;
import steelcraft.blocks.Spawner;
import steelcraft.main.worlds.EndWorld;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.utils.SkullTools;

import java.util.Arrays;

public class TestCommand extends AbstractCommand {
    public TestCommand() {
        super("test");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        SkyPlayer player = Data.getSkyPlayer(sender.getName());
        if (player == null) { return false;}
        player.bukkitPlayer.getInventory().addItem(Spawner.getItem(EntityType.SKELETON));
        player.bukkitPlayer.getInventory().addItem(Case.cases[0].key);
        player.bukkitPlayer.getInventory().addItem(Generator.getItem());
        player.generatorBlocks += 100000;
        player.changeMoney(100000, true);
        player.bukkitPlayer.teleport(new Location(EndWorld.world, 0, 101, 0));

        player.bukkitPlayer.getInventory().addItem(SkullTools.createSkull(SkullTools.LEFT_ARROW, "123", Arrays.asList()));
        return true;
    }
}
