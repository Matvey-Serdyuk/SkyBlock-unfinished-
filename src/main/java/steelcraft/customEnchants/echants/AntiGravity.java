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

public class AntiGravity extends CustomEnchant {
    public AntiGravity() {
        super("AntiGravity", 100, 0, 5, CustomEnchantRare.RARE, Arrays.asList(Material.DIAMOND_BOOTS));
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
        entity.addEffect(PotionEffectType.JUMP, 30, level - 1, false);
    }
}
