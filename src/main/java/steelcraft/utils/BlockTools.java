package steelcraft.utils;

import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.IMaterial;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class BlockTools {
    public static int getAmountDropItems(Block block, ItemStack item) {
        net.minecraft.server.v1_13_R2.Block minecraftBlock = ((CraftBlock) block).getNMS().getBlock();

        return minecraftBlock.getDropCount(minecraftBlock.getBlockData(),
                item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS), ((CraftWorld) block.getWorld()).getHandle(),
                new BlockPosition(block.getX(), block.getY(), block.getZ()), new Random());
    }

    public static int getExp(Block block, ItemStack item) {
        net.minecraft.server.v1_13_R2.Block minecraftBlock = ((CraftBlock) block).getNMS().getBlock();

        return minecraftBlock.getExpDrop(minecraftBlock.getBlockData(), ((CraftWorld) block.getWorld()).getHandle(),
                new BlockPosition(block.getX(), block.getY(), block.getZ()), 0);
    }

    public static Material getDropItem(Block block) {
        net.minecraft.server.v1_13_R2.Block minecraftBlock = ((CraftBlock) block).getNMS().getBlock();

        IMaterial material = minecraftBlock.getDropType(minecraftBlock.getBlockData(), ((CraftWorld) block.getWorld()).getHandle(),
                new BlockPosition(block.getX(), block.getY(), block.getZ()), 0);
        net.minecraft.server.v1_13_R2.ItemStack item = new net.minecraft.server.v1_13_R2.ItemStack(material);
        ItemStack itemStack = CraftItemStack.asBukkitCopy(item);
        return itemStack.getType();
    }
}
