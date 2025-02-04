package steelcraft.customEnchants.echants;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.customEnchants.CustomEnchantRare;
import steelcraft.customEntities.CustomEntity;

import java.util.Arrays;

public class CriticalDamage extends CustomEnchant {
    final double multi = 0.25d;

    public CriticalDamage() {
        super("CriticalDamage", 25, 0, 5, CustomEnchantRare.LEGEND, Arrays.asList(Material.DIAMOND_SWORD));
    }

    @Override
    public void attack(EntityDamageByEntityEvent event, int level) {
        if (!checkChance(level)) {return;}

        event.setDamage(event.getDamage() * (1 + multi * level));
    }

    @Override
    public void defend(EntityDamageByEntityEvent event, int level) {

    }

    @Override
    public void blockBreak(BlockBreakEvent event, int level) {

    }

    @Override
    public void time(CustomEntity entity, int level) {

    }
}
