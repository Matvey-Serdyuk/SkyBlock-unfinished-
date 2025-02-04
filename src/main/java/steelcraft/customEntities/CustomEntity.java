package steelcraft.customEntities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.main.Data;
import steelcraft.main.Main;

import java.util.HashMap;
import java.util.Objects;

public class CustomEntity {
    public String name;
    public String tag;

    public double HP;
    public double damage;
    public double regen;
    public double armor;
    public double speed;

    public boolean AI;

    public ItemStack[] itemArmors;
    public ItemStack itemInHand;

    public Entity entity = null;
    public LivingEntity livingEntity = null;
    public Mob mob = null;

    public EntityType type;
    public EntityStream entityStream = null;

    public boolean pvp = true;
    public boolean isAlive = false;
    public boolean isTicking = true;

    public CustomEntity(EntityType type, String name, String tag, double HP, double damage, double regen, double armor,
                        double speed, ItemStack[] itemArmors, ItemStack itemInHand, boolean AI) {
        this.type = type;
        this.name = name;
        this.tag = tag;

        this.HP = HP;
        this.damage = damage;
        this.regen = regen;
        this.armor = armor;
        this.speed = speed;
        this.AI = AI;

        this.itemArmors = itemArmors;
        this.itemInHand = itemInHand;
    }

    public CustomEntity copy() {
        return new CustomEntity(this.type, this.name, this.tag, this.HP, this.damage, this.regen, this.armor,
                this.speed, this.itemArmors, this.itemInHand, this.AI);
    }

    public void spawn(Location spawnLocation) {
        Main.spawn = true;
        entity = spawnLocation.getWorld().spawnEntity(spawnLocation, type);
        Main.spawn = false;
        livingEntity = (LivingEntity) entity;
        if (entity instanceof Mob) {
            mob = (Mob) entity;
        } else {
            mob = null;
        }
        entity.setCustomName(name);
        entity.setCustomNameVisible(true);
        entity.addScoreboardTag(tag);

        livingEntity.setAI(AI);

        livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HP);
        if (livingEntity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE) != null) {
            livingEntity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
        }
        if (livingEntity.getAttribute(Attribute.GENERIC_ARMOR) != null) {
            livingEntity.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(armor);
        }
        if (livingEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED) != null) {
            livingEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
        }
        livingEntity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);

        EntityEquipment equipment = livingEntity.getEquipment();
        if (equipment != null) {
            if (itemArmors != null) {
                equipment.setArmorContents(itemArmors);
            }
            if (itemInHand != null) {
                equipment.setItemInMainHand(itemInHand);
            }
        }

        livingEntity.setHealth(HP);

        Data.aliveEntities.add(this);
        if (isTicking) {
            entityStream = new EntityStream(this);
        }

        isAlive = true;
    }

    public void wound(EntityDamageByEntityEvent event, CustomEntity entity, CustomEntity damager) {
        wound();

        HashMap<CustomEnchant, Integer> map = CustomEnchant.getEnchants(livingEntity);

        for (CustomEnchant enc : map.keySet()) {
            enc.defend(event, map.get(enc));
        }

    }

    public void wound(EntityDamageEvent event) {
        wound();
    }

    public void wound() {

    }

    public void attack(EntityDamageByEntityEvent event, CustomEntity entity, CustomEntity damager) {
        if (type == EntityType.SLIME || type == EntityType.MAGMA_CUBE) {
            event.setDamage(damage);
        }

        HashMap<CustomEnchant, Integer> map = CustomEnchant.getEnchants(livingEntity);

        for (CustomEnchant enc : map.keySet()) {
            enc.attack(event, map.get(enc));
        }
    }

    public void death() {
        if (entityStream != null) {
            if (entityStream.isWork) {
                entityStream.stop();
            }
            entityStream = null;
        }
        Data.aliveEntities.remove(this);

        isAlive = false;
    }

    public void remove() {
        if (entity != null) {
            entity.remove();
        }
        death();
    }

    public void addHealth(double addHP) {
        double hp = livingEntity.getHealth();

        double newHP = hp + addHP;
        if (newHP < livingEntity.getMaxHealth()) {
            livingEntity.setHealth(newHP);
        } else {
            livingEntity.setHealth(livingEntity.getMaxHealth());
        }
    }

    public void setFullHp() {
        livingEntity.setHealth(livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
    }

    public void addEffect(PotionEffectType type, int duration, int level, boolean stack) {
        if (livingEntity == null) {return;}
        if (livingEntity.hasPotionEffect(type)) {
            PotionEffect eff = livingEntity.getPotionEffect(type);
            assert eff != null;
            if (stack && level == eff.getAmplifier()) {
                duration = eff.getDuration() + duration;
            } else if (level < eff.getAmplifier()) {
                return;
            }
            livingEntity.removePotionEffect(type);
            livingEntity.addPotionEffect(new PotionEffect(type, duration, level));
            return;
        }

        livingEntity.addPotionEffect(new PotionEffect(type, duration, level));
    }


    public void deSpawn() {
        remove();
    }

    public void damage(double damage, Enchantment enchantment) {
        double red = 0;
        double armorRed = 0;

        if (livingEntity.getEquipment() == null) {livingEntity.damage(damage); return;}

        EntityEquipment inv = livingEntity.getEquipment();

        ItemStack boots = inv.getBoots();
        ItemStack helmet = inv.getHelmet();
        ItemStack chest = inv.getChestplate();
        ItemStack pants = inv.getLeggings();

        if (helmet != null) {
            if (helmet.getType() == Material.LEATHER_HELMET) red = red + 0.04;
            else if (helmet.getType() == Material.GOLDEN_HELMET) red = red + 0.08;
            else if (helmet.getType() == Material.CHAINMAIL_HELMET) red = red + 0.08;
            else if (helmet.getType() == Material.IRON_HELMET) red = red + 0.08;
            else if (helmet.getType() == Material.DIAMOND_HELMET) red = red + 0.12;

            if (helmet.getItemMeta() != null) {
                armorRed += helmet.getItemMeta().getEnchantLevel(enchantment) * 0.04;
            }
        }

        if (boots != null) {
            if (boots.getType() == Material.LEATHER_BOOTS) red = red + 0.04;
            else if (boots.getType() == Material.GOLDEN_BOOTS) red = red + 0.04;
            else if (boots.getType() == Material.CHAINMAIL_BOOTS) red = red + 0.04;
            else if (boots.getType() == Material.IRON_BOOTS) red = red + 0.08;
            else if (boots.getType() == Material.DIAMOND_BOOTS) red = red + 0.12;

            if (boots.getItemMeta() != null) {
                armorRed += boots.getItemMeta().getEnchantLevel(enchantment) * 0.04;
            }
        }

        if (pants != null) {
            if (pants.getType() == Material.LEATHER_LEGGINGS) red = red + 0.08;
            else if (pants.getType() == Material.GOLDEN_LEGGINGS) red = red + 0.12;
            else if (pants.getType() == Material.CHAINMAIL_LEGGINGS) red = red + 0.16;
            else if (pants.getType() == Material.IRON_LEGGINGS) red = red + 0.20;
            else if (pants.getType() == Material.DIAMOND_LEGGINGS) red = red + 0.24;

            if (pants.getItemMeta() != null) {
                armorRed += pants.getItemMeta().getEnchantLevel(enchantment) * 0.04;
            }
        }

        if (chest != null) {
            if (chest.getType() == Material.LEATHER_CHESTPLATE) red = red + 0.12;
            else if (chest.getType() == Material.GOLDEN_CHESTPLATE) red = red + 0.20;
            else if (chest.getType() == Material.CHAINMAIL_CHESTPLATE) red = red + 0.20;
            else if (chest.getType() == Material.IRON_CHESTPLATE) red = red + 0.24;
            else if (chest.getType() == Material.DIAMOND_CHESTPLATE) red = red + 0.32;

            if (chest.getItemMeta() != null) {
                armorRed += chest.getItemMeta().getEnchantLevel(enchantment) * 0.04;
            }
        }

        red = red + armorRed * (1 - red);

        if (livingEntity.getAttribute(Attribute.GENERIC_ARMOR) != null) {
            red = red + (Objects.requireNonNull(livingEntity.getAttribute(Attribute.GENERIC_ARMOR)).getValue() * 0.04) * (1 - red);
        }

        red = Math.min(99, red);

        livingEntity.damage(damage * (1 - red));
    }

    public void tick() {
        addHealth(regen);
    }
}
