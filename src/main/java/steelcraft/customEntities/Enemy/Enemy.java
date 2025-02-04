package steelcraft.customEntities.Enemy;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import steelcraft.Case.Drops.ItemDrop;
import steelcraft.customEntities.CustomEntity;
import steelcraft.utils.EntityTools;

public class Enemy extends CustomEntity {
    final private float dropRange = 6f;
    final private int expPart = 5;

    public ItemDrop[] drops;
    public int exp;

    public Enemy(EntityType type, String name, String tag, double HP, double damage, double regen, double armor, double speed,
                 ItemStack[] itemArmors, ItemStack itemInHand, boolean AI, int exp, ItemDrop[] drops) {
        super(type, name, tag, HP, damage, regen, armor, speed, itemArmors, itemInHand, AI);

        this.exp = exp;
        this.drops = drops;
    }

    public void death() {
        Location loc = entity.getLocation();
        assert loc.getWorld() != null;
        for (ItemDrop item : drops) {
            if (item.check()) {
                loc.getWorld().dropItemNaturally(getDropLocation(loc), item.item);
            }
        }

        for (int i = 0; i < expPart; i++) {
            EntityTools.spawnExp(exp / expPart, getDropLocation(loc));
        }

        super.death();
    }

    private Location getDropLocation(Location loc) {
        return new Location(loc.getWorld(), loc.getX() + (dropRange * Math.random() * 2 - dropRange),
                loc.getY() + 2 + Math.random() * dropRange / 2,
                loc.getZ() + (dropRange * Math.random() * 2 - dropRange));
    }
}
