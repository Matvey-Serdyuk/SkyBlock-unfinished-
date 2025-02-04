package steelcraft.blocks.data;

import steelcraft.blocks.Generator;
import steelcraft.utils.Vector3;

public class GeneratorData {
    public Vector3 location;
    public int level;
    public boolean isWork;

    public GeneratorData(Generator generator) {
        location = new Vector3(generator.location);
        level = generator.level;
        isWork = generator.isWork;
    }
}
