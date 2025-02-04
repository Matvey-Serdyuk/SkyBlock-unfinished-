package steelcraft.customEnchants.echants;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.customEntities.CustomEntity;

import java.util.List;

public class TestEnchant extends CustomEnchant {
    public TestEnchant(String name, float chance, float riceChance, int maxLevel, byte rare, List<Material> materials) {
        super(name, chance, riceChance, maxLevel, rare, materials);
    }

    @Override
    public void attack(EntityDamageByEntityEvent event, int level) {
        if (event.getDamager() instanceof Player) {
            event.getDamager().sendMessage("attack");
        }
    }

    @Override
    public void defend(EntityDamageByEntityEvent event, int level) {
        if (event.getEntity() instanceof Player) {
            event.getEntity().sendMessage("defend");
        }
    }

    @Override
    public void blockBreak(BlockBreakEvent event, int level) {
        event.getPlayer().sendMessage("break");
    }

    @Override
    public void time(CustomEntity entity, int level) {

    }
}
