package steelcraft.customEnchants.echants;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.customEnchants.CustomEnchantRare;
import steelcraft.customEntities.CustomEntity;
import steelcraft.main.Data;

import java.util.Arrays;
import java.util.List;

public class Vampire extends CustomEnchant {
    final float vampire = 0.2f;

    public Vampire() {
        super("Vampire", 10, 0, 5, CustomEnchantRare.RARE, Arrays.asList(Material.DIAMOND_SWORD));
    }

    @Override
    public void attack(EntityDamageByEntityEvent event, int level) {
        if (!checkChance(level)) {return;}

        CustomEntity entity = Data.getCustomEntity(event.getDamager());
        assert entity != null;
        entity.addHealth(event.getFinalDamage() * vampire * level);
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
