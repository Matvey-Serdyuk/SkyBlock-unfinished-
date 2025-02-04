package steelcraft.customEnchants.echants;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.customEnchants.CustomEnchantRare;
import steelcraft.customEntities.CustomEntity;

import java.util.Arrays;
import java.util.List;

public class Speed extends CustomEnchant {
    public Speed() {
        super("Speed", 100, 0, 3, CustomEnchantRare.RARE, Arrays.asList(Material.DIAMOND_BOOTS));
    }

    @Override
    public void attack(EntityDamageByEntityEvent event, int level) {

    }

    @Override
    public void defend(EntityDamageByEntityEvent event, int level) {

    }

    @Override
    public void blockBreak(BlockBreakEvent event, int level) {

    }

    @Override
    public void time(CustomEntity entity, int level) {
        entity.addEffect(PotionEffectType.SPEED, 30, level - 1, false);
    }
}
