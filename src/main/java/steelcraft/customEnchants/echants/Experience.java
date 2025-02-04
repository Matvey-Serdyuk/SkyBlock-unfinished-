package steelcraft.customEnchants.echants;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.customEnchants.CustomEnchantRare;
import steelcraft.customEntities.CustomEntity;

import java.util.Arrays;
import java.util.List;

public class Experience extends CustomEnchant {
    public Experience() {
        super("Experience", 100, 0, 5, CustomEnchantRare.LEGEND, Arrays.asList(Material.DIAMOND_PICKAXE));
    }

    @Override
    public void attack(EntityDamageByEntityEvent event, int level) {

    }

    @Override
    public void defend(EntityDamageByEntityEvent event, int level) {

    }

    @Override
    public void blockBreak(BlockBreakEvent event, int level) {
        event.setExpToDrop(event.getExpToDrop() * (1 + level));
    }

    @Override
    public void time(CustomEntity entity, int level) {

    }
}
