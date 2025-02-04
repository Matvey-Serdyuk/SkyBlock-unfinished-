package steelcraft.customEnchants.echants;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.customEnchants.CustomEnchantRare;
import steelcraft.customEntities.CustomEntity;
import steelcraft.main.Data;

import java.util.Arrays;

public class Zeus extends CustomEnchant {
    final float damage = 4;
    public Zeus() {
        super("Zeus", 5, 1, 5, CustomEnchantRare.LEGEND, Arrays.asList(Material.DIAMOND_SWORD));
    }

    @Override
    public void attack(EntityDamageByEntityEvent event, int level) {
        if (!checkChance(level)) {return;}

        CustomEntity entity = Data.getCustomEntity(event.getEntity());
        assert entity != null;
        Location location = entity.entity.getLocation();
        assert location.getWorld() != null;
        location.getWorld().strikeLightningEffect(location);

        entity.damage(damage + damage * level / 2, Enchantment.PROTECTION_ENVIRONMENTAL);

        int duration = 40 + level * 10;
        entity.addEffect(PotionEffectType.BLINDNESS, duration, 5, false);
        entity.addEffect(PotionEffectType.SLOW, duration, 5, false);
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
