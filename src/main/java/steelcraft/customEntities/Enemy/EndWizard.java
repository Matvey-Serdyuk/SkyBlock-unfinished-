package steelcraft.customEntities.Enemy;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import steelcraft.Case.Drops.ItemDrop;
import steelcraft.customEntities.CustomEntity;
import steelcraft.customEntities.Summon;
import steelcraft.main.Data;
import steelcraft.utils.ItemTools;

import java.util.ArrayList;
import java.util.List;

public class EndWizard extends Enemy {
    final PotionEffect effect = new PotionEffect(PotionEffectType.LEVITATION, 100, 0);
    final double distance = 3;
    final public Summon summon = new Summon(EntityType.ENDERMITE, "", "summonOfEndWizard", 20, 4, 0,
            5, 0.5, null, null, true, 20 * 30);

    public Summon[] summons = new Summon[10];
    public int amountSummons = 0;

    public EndWizard() {
        super(EntityType.ZOMBIE, "Маг энда", "endWizard", 250, 10, 2, 15, 0.3,
                new ItemStack[]{
                        ItemTools.createLeatherArmor(Material.LEATHER_BOOTS, Color.BLACK),
                        ItemTools.createLeatherArmor(Material.LEATHER_LEGGINGS, Color.BLACK),
                        ItemTools.createLeatherArmor(Material.LEATHER_CHESTPLATE, Color.BLACK),
                        ItemTools.createLeatherArmor(Material.LEATHER_HELMET, Color.BLACK)
                }, null, true, 300,
                new ItemDrop[] {

                });
    }

    public void tick() {
        super.tick();

        List<Entity> entityList = livingEntity.getNearbyEntities(8, 8, 8);
        List <CustomEntity> entities = new ArrayList<>();

        for (Entity entity : livingEntity.getNearbyEntities(8, 8, 8)) {
            if (entity instanceof Player) {
                entities.add(Data.getCustomEntity(entity));
            }
        }

        boolean addEffect = false;
        if (Math.random() < 0.1f) {
            addEffect = true;
        }

        for (CustomEntity entity : entities) {
            if (addEffect) {
                entity.livingEntity.addPotionEffect(effect);
            }
            entity.damage(damage, Enchantment.PROTECTION_ENVIRONMENTAL);
        }

        amountSummons = 0;
        int nullPlace = -1;
        for (int i = 0; i < summons.length; i++) {
            if (summons[i] == null) {
                amountSummons++;
                nullPlace = i;
            } else if (!summons[i].isAlive) {
                summons[i] = null;
                amountSummons++;
                nullPlace = i;
            }
        }

        if (nullPlace == -1) {return;}
        if (Math.random() < 0.5f) {
            Summon summon = this.summon.copy();
            summons[nullPlace] = summon;
            Location loc = entity.getLocation();
            summon.spawn(new Location(loc.getWorld(), loc.getX() + Math.random() * distance * 2 - distance,
                    loc.getY(), loc.getZ() + Math.random() * distance * 2 - distance));
        }
    }
}
