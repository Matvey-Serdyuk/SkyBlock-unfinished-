package steelcraft.blocks.data;

import org.bukkit.entity.EntityType;
import steelcraft.blocks.Spawner;
import steelcraft.utils.Vector3;

public class SpawnerData {
    public EntityType type;
    public Vector3 location;
    public int level;

    public SpawnerData(Spawner spawner) {
        type = spawner.type;
        location = new Vector3(spawner.location);
        level = spawner.level;
    }
}
