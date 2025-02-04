package steelcraft.customEnchants.echants;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.customEnchants.CustomEnchantRare;
import steelcraft.customEntities.CustomEntity;
import steelcraft.main.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Berserk extends CustomEnchant {
    final double addHP = 0.1f;
    final float multiple = 0.05f;

    public Berserk() {
        super("Berserk", 100, 0, 5, CustomEnchantRare.LEGEND,
                Arrays.asList(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS));
    }

    @Override
    public void attack(EntityDamageByEntityEvent event, int level) {
        CustomEntity damager = Data.getCustomEntity(event.getDamager());
        assert damager != null;

        double maxHP = Objects.requireNonNull(damager.livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue();
        double cof = (1 - damager.livingEntity.getHealth() / maxHP);
        event.setDamage(event.getDamage() * (1 + multiple * level * cof));
    }

    @Override
    public void defend(EntityDamageByEntityEvent event, int level) {

    }

    @Override
    public void blockBreak(BlockBreakEvent event, int level) {

    }

    @Override
    public void time(CustomEntity entity, int level) {
        double maxHP = Objects.requireNonNull(entity.livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue();
        double cof = (1 - entity.livingEntity.getHealth() / maxHP);
        entity.addHealth(addHP * level * cof);
    }
}
