package steelcraft.customEntities;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import steelcraft.blocks.Spawner;

public class SpawnerEntity {
    public String name;
    EntityType type;
    public ItemStack[] drop;
    public int exp;

    public SpawnerEntity(String name, EntityType type, int exp, ItemStack[] drop) {
        this.name = name;
        this.type = type;
        this.exp = exp;
        this.drop = drop;
    }

    public static SpawnerEntity getSpawnerEntity(EntityType type) {
        for (SpawnerEntity entity : Spawner.spawnerEntities) {
            if (entity.type == type) {
                return entity;
            }
        }
        return null;
    }
}
