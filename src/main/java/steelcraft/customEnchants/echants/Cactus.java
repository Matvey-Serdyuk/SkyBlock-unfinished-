package steelcraft.customEnchants.echants;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.customEnchants.CustomEnchantRare;
import steelcraft.customEntities.CustomEntity;
import steelcraft.main.Data;

import java.util.Arrays;
import java.util.List;

public class Cactus extends CustomEnchant {
    final double damage = 0.5d;

    public Cactus() {
        super("Cactus", 25, 0, 5, CustomEnchantRare.RARE,
                Arrays.asList(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS));
    }

    @Override
    public void attack(EntityDamageByEntityEvent event, int level) {

    }

    @Override
    public void defend(EntityDamageByEntityEvent event, int level) {
        if (!checkChance(level)) {return;}

        CustomEntity damager = Data.getCustomEntity(event.getDamager());
        assert damager != null;
        damager.damage(0.25 + damage * level, Enchantment.PROTECTION_ENVIRONMENTAL);
    }

    @Override
    public void blockBreak(BlockBreakEvent event, int level) {

    }

    @Override
    public void time(CustomEntity entity, int level) {

    }
}
