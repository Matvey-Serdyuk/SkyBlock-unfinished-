package steelcraft.customEnchants.echants;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftItem;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.customEnchants.CustomEnchantRare;
import steelcraft.customEntities.CustomEntity;
import steelcraft.main.Main;
import steelcraft.utils.BlockTools;
import steelcraft.utils.EntityTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Smash extends CustomEnchant {
    public boolean mine = false;

    public Smash() {
        super("Smash", 10, 10, 10, CustomEnchantRare.LEGEND, Arrays.asList(Material.DIAMOND_PICKAXE));
    }

    @Override
    public void attack(EntityDamageByEntityEvent event, int level) {

    }

    @Override
    public void defend(EntityDamageByEntityEvent event, int level) {

    }

    @Override
    public void blockBreak(BlockBreakEvent event, int level) {
        if (!checkChance(level)) {return;}
        Block b = event.getBlock();
        Location location = b.getLocation();
        World world = b.getWorld();
        assert event.getPlayer().getEquipment() != null;
        ItemStack item = event.getPlayer().getEquipment().getItemInMainHand();


        float yaw = event.getPlayer().getLocation().getYaw();
        float pitch = event.getPlayer().getLocation().getPitch();
        int x_ = location.getBlockX(), y_ = location.getBlockY(), z_ = location.getBlockZ();
        List <Block> blocks = new ArrayList<>();
        if (Math.abs(pitch) > 45) {
            for (int x = x_ - 1; x <= x_ + 1; x++) {
                for (int z = z_ - 1; z <= z_ + 1; z++) {
                    blocks.add(world.getBlockAt(x, y_, z));
                }
            }
        } else if (-135 > yaw && -225 < yaw || yaw > -45 || yaw < -315) {
            for (int x = x_ - 1; x <= x_ + 1; x++) {
                for (int y = y_ - 1; y <= y_ + 1; y++) {
                    blocks.add(world.getBlockAt(x, y, z_));
                }
            }
        } else {
            for (int z = z_ - 1; z <= z_ + 1; z++) {
                for (int y = y_ - 1; y <= y_ + 1; y++) {
                    blocks.add(world.getBlockAt(x_, y, z));
                }
            }
        }

        int breakBlocks = 1;
        for (Block block : blocks) {
            if (block.getType() == Material.AIR || block.getType() == Material.BEDROCK) {
                continue;
            }
            breakBlocks++;

            mine = true;

            BlockBreakEvent blockEvent = new BlockBreakEvent(block, event.getPlayer());
            blockEvent.setExpToDrop(BlockTools.getExp(block, item));
            Bukkit.getServer().getPluginManager().callEvent(blockEvent);

            if (!blockEvent.isCancelled()) {
                if (blockEvent.isDropItems()) {
                    dropItemWithBlock(world, block, item);
                }
                int exp = blockEvent.getExpToDrop();
                if (exp != 0) {
                    EntityTools.spawnExp(exp, block.getLocation());
                }
                block.setType(Material.AIR);
            }
        }
        net.minecraft.server.v1_13_R2.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
        itemStack.isDamaged(breakBlocks, new Random(), ((CraftPlayer) event.getPlayer()).getHandle());
        ItemStack item1 = CraftItemStack.asBukkitCopy(itemStack);
        item.setItemMeta(item1.getItemMeta());
        item.setData(item1.getData());
    }

    @Override
    public void time(CustomEntity entity, int level) {

    }

    public void dropItemWithBlock(World world, Block block, ItemStack item) {
        ItemStack dropItem = new ItemStack(BlockTools.getDropItem(block));
        dropItem.setAmount(BlockTools.getAmountDropItems(block, item));
        if (dropItem.getType() == Material.AIR) {
            return;
        }
        world.dropItemNaturally(block.getLocation(), dropItem);
    }
}
