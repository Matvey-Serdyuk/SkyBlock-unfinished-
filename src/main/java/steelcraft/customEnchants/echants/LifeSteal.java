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

public class LifeSteal extends CustomEnchant {
    final double damage = 0.5;

    public LifeSteal() {
        super("LifeSteal", 100, 0, 5, CustomEnchantRare.LEGEND, Arrays.asList(Material.DIAMOND_SWORD));
    }

    @Override
    public void attack(EntityDamageByEntityEvent event, int level) {
        CustomEntity damager = Data.getCustomEntity(event.getDamager());

        double oldDamage = event.getFinalDamage();

        event.setDamage(event.getDamage() + damage * level);

        assert damager != null;
        damager.addHealth(event.getFinalDamage() - oldDamage);
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
