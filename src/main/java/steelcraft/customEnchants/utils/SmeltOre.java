package steelcraft.customEnchants.utils;

import org.bukkit.Material;

public class SmeltOre {
    public Material ore;
    public Material ingot;
    public Material likeMaterial;

    public SmeltOre(Material ore, Material ingot, Material likeMaterial) {
        this.ore = ore;
        this.ingot = ingot;
        this.likeMaterial = likeMaterial;
    }
}
