package steelcraft.customEnchants.echants;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.customEnchants.CustomEnchantRare;
import steelcraft.customEntities.CustomEntity;
import steelcraft.customEntities.Summon;

import java.util.Arrays;

public class Summoner extends CustomEnchant {
    final private Summon summon = new Summon(EntityType.SILVERFISH, "", "SummonerMob", 10, 1, 1,
            10, 0.4, null, null, true, 20 * 60);

    public Summoner() {
        super("Summoner", 1, 1, 5, CustomEnchantRare.RARE,
                Arrays.asList(Material.DIAMOND_BOOTS, Material.DIAMOND_LEGGINGS, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_HELMET));
    }

    @Override
    public void attack(EntityDamageByEntityEvent event, int level) {

    }

    @Override
    public void defend(EntityDamageByEntityEvent event, int level) {
        if (!checkChance(level) && event.getDamager().getType() != EntityType.PLAYER) {return;}

        Location location = event.getEntity().getLocation();
        Summon summon = this.summon.copy();
        summon.damage = level;

        Location spawnLocation = new Location(location.getWorld(), location.getX() + Math.random(), location.getY(),
                location.getZ() + Math.random());
        summon.spawn(spawnLocation);

        summon.mob.setTarget((LivingEntity) event.getDamager());
    }

    @Override
    public void blockBreak(BlockBreakEvent event, int level) {

    }

    @Override
    public void time(CustomEntity entity, int level) {

    }
}
