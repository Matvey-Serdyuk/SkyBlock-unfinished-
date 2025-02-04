package steelcraft.customEnchants.echants;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.customEnchants.CustomEnchantRare;
import steelcraft.customEntities.CustomEntity;
import java.util.Arrays;
import java.util.List;

public class Purification extends CustomEnchant {
    final private static List<PotionEffectType> types = Arrays.asList(
            PotionEffectType.WITHER, PotionEffectType.BLINDNESS,
            PotionEffectType.SLOW, PotionEffectType.WEAKNESS, PotionEffectType.POISON,
            PotionEffectType.CONFUSION, PotionEffectType.HUNGER,
            PotionEffectType.SLOW_DIGGING, PotionEffectType.UNLUCK
    );

    public Purification() {
        super("Purification", 2, 1.5f, 5, CustomEnchantRare.RARE, Arrays.asList(Material.DIAMOND_SWORD));
    }

    @Override
    public void attack(EntityDamageByEntityEvent event, int level) {
        LivingEntity entity = (LivingEntity) event.getDamager();

        for (PotionEffect effect : entity.getActivePotionEffects()) {
            if (types.contains(effect.getType())) {
                entity.removePotionEffect(effect.getType());
            }
        }
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
