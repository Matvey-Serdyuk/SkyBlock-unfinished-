package steelcraft.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import steelcraft.main.Main;
import steelcraft.utils.EntityTools;

public class Resource {
    public Material block;
    public Material ingot;
    public int[] exp;
    public int[] amount;

    public Resource(Material block, Material ingot, int[] exp, int[] amount) {
        this.block = block;
        this.ingot = ingot;
        this.exp = exp;
        this.amount = amount;
    }

    public int getExp() {
        return (int) Math.round(exp[0] + Math.random() * (exp[1] - exp[0]));
    }

    public int getAmount() {
        return (int) Math.round(amount[0] + Math.random() * (amount[1] - amount[0]));
    }

    public void drop(Location location) {
        EntityTools.spawnExp(getExp(), location);
    }
}
