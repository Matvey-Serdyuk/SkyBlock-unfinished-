package steelcraft.customEnchants.echants;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.customEnchants.CustomEnchantRare;
import steelcraft.customEntities.CustomEntity;

import java.util.Arrays;

public class Ghost extends CustomEnchant {
    public Ghost() {
        super("Ghost", 1, 1, 5, CustomEnchantRare.LEGEND,
                Arrays.asList(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS));
    }

    @Override
    public void attack(EntityDamageByEntityEvent event, int level) {

    }

    @Override
    public void defend(EntityDamageByEntityEvent event, int level) {
        if (!checkChance(level)) {return;}

        event.setCancelled(true);
    }

    @Override
    public void blockBreak(BlockBreakEvent event, int level) {

    }

    @Override
    public void time(CustomEntity entity, int level) {

    }
}
