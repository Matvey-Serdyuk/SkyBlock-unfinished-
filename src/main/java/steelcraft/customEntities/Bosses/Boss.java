package steelcraft.customEntities.Bosses;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import steelcraft.Case.Drops.ItemDrop;
import steelcraft.customEntities.CustomEntity;
import steelcraft.customEntities.Enemy.Enemy;
import steelcraft.main.Data;
import steelcraft.skyPlayer.SkyPlayer;


abstract public class Boss extends Enemy {
    final String path = "Bosses.";
    final String fullPath;

    public Bar bar;
    public Location spawn;
    public long respawnTime;

    public long nextRespawn;

    public Boss(EntityType type, String name, String tag, double HP, double damage, double regen, double armor,
                double speed, ItemStack[] itemArmors, ItemStack itemInHand, boolean AI,
                Location spawn, int exp, ItemDrop[] drops, long respawnTime) {
        super(type, name, tag, HP, damage, regen, armor, speed, itemArmors, itemInHand, AI, exp, drops);


        fullPath = path + name;

        this.spawn = spawn;
        this.respawnTime = respawnTime;

        FileConfiguration fileCon = YamlConfiguration.loadConfiguration(Data.file);
        String str = fileCon.getString(fullPath);

        if (str == null) {
            nextRespawn = System.currentTimeMillis() + respawnTime;
        } else {
            nextRespawn = Long.parseLong(str);
        }
    }

    public void spawn() {
        super.spawn(spawn);
        bar = new Bar(Bukkit.createBossBar(name, BarColor.GREEN, BarStyle.SOLID), Bar.BarType.BOSS_BAR);
    }

    public void death() {
        bar.removeAll();
        bar = null;

        nextRespawn = System.currentTimeMillis() + respawnTime;

        super.death();
    }

    public void wound(EntityDamageByEntityEvent event, CustomEntity entity, CustomEntity damager) {
        super.wound(event, entity, damager);

        if (damager instanceof SkyPlayer) {
            SkyPlayer player = (SkyPlayer) damager;
            if (!bar.contains(player)) {
                bar.addPlayer(player);
            }
        }

        double progress = (livingEntity.getHealth() - event.getFinalDamage()) / HP;

        if (progress > 0) {
            bar.setProgress(progress);
        } else {
            bar.setProgress(0);
        }
    }

    public void attack(EntityDamageByEntityEvent event, CustomEntity entity, CustomEntity damager) {
        super.attack(event, entity, damager);
    }

    public void save(FileConfiguration fileConfiguration) {
        fileConfiguration.set(fullPath, nextRespawn);
    }
}
