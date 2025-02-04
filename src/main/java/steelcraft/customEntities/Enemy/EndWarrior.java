package steelcraft.customEntities.Enemy;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import steelcraft.Case.Drops.ItemDrop;
import steelcraft.customEntities.CustomEntity;
import steelcraft.main.Data;
import steelcraft.utils.ItemTools;

public class EndWarrior extends Enemy {
    public EndWarrior() {
        super(EntityType.ZOMBIE, "Воин энда", "endWarrior", 400, 5, 1.5, 20,
                0.35, new ItemStack[]{
                        ItemTools.createLeatherArmor(Material.LEATHER_BOOTS, Color.BLACK),
                        ItemTools.createLeatherArmor(Material.LEATHER_LEGGINGS, Color.BLACK),
                        ItemTools.createLeatherArmor(Material.LEATHER_CHESTPLATE, Color.BLACK),
                        ItemTools.createLeatherArmor(Material.LEATHER_HELMET, Color.BLACK)
                }, new ItemStack(Material.DIAMOND_SWORD), true, 200,
                new ItemDrop[] {});
    }

    public void wound(EntityDamageByEntityEvent event, CustomEntity entity, CustomEntity damager) {
        super.wound(event, entity, damager);

        assert damager != null;

        if (Math.random() < 0.075f) {
            damager.damage(damage + 7, Enchantment.PROTECTION_ENVIRONMENTAL);
            assert this.entity.getLocation().getWorld() != null;
            this.entity.getLocation().getWorld().spawnParticle(Particle.DRAGON_BREATH, this.entity.getLocation(), 30);
        }
    }
}
