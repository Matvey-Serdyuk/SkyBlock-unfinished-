package steelcraft.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import steelcraft.utils.DropItems;


public class MagicBlock {
    public static DropItems[] drop = {
            new DropItems(new ItemStack[] {new ItemStack(Material.DIRT), new ItemStack(Material.SAND),
                    new ItemStack(Material.OAK_WOOD)},
                    new float[] {35.f, 35.f, 30.f},
                    new int[] {1, 1, 1})
    };

    public static void place(Location location, int level) {
        ItemStack item = drop[level].getItem();
        Block block = location.getWorld().getBlockAt(location);
        block.setType(item.getType());
    }
}
