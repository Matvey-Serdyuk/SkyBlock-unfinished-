package steelcraft.customEnchants.echants;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.customEnchants.CustomEnchantRare;
import steelcraft.customEntities.CustomEntity;
import steelcraft.main.Data;

import java.util.Arrays;
import java.util.List;

public class Wither extends CustomEnchant {
    public Wither() {
        super("Wither", 7.5f, 0, 5, CustomEnchantRare.RARE, Arrays.asList(Material.DIAMOND_SWORD));
    }

    @Override
    public void attack(EntityDamageByEntityEvent event, int level) {
        if (!checkChance(level)) {return;}

        CustomEntity entity = Data.getCustomEntity(event.getEntity());
        assert entity != null;

        entity.addEffect(PotionEffectType.WITHER, 40 + 10 * level, level / 3, true);
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
