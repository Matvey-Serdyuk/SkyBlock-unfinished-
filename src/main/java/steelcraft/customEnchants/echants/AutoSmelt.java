package steelcraft.customEnchants.echants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.customEnchants.CustomEnchantRare;
import steelcraft.customEnchants.utils.SmeltOre;
import steelcraft.customEntities.CustomEntity;
import steelcraft.main.Main;
import steelcraft.utils.BlockTools;

import java.util.Arrays;
import java.util.List;

public class AutoSmelt extends CustomEnchant {
    public SmeltOre[] ores = {new SmeltOre(Material.IRON_ORE, Material.IRON_INGOT, Material.COAL_ORE),
    new SmeltOre(Material.GOLD_ORE, Material.GOLD_INGOT, Material.COAL_ORE)};

    public AutoSmelt() {
        super("AutoSmelt", 20, 20, 5, CustomEnchantRare.RARE, Arrays.asList(Material.DIAMOND_PICKAXE));
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

        Block block = event.getBlock();
        assert event.getPlayer().getEquipment() != null;
        ItemStack item = event.getPlayer().getEquipment().getItemInMainHand();
        for (SmeltOre ore : ores) {
            if (ore.ore == block.getType()) {
                event.setDropItems(false);
                block.setType(ore.likeMaterial);
                event.setExpToDrop(BlockTools.getExp(block, item));
                ItemStack drop = new ItemStack(ore.ingot);
                drop.setAmount(BlockTools.getAmountDropItems(block, item));
                block.getWorld().dropItemNaturally(block.getLocation(), drop);
                block.setType(Material.AIR);
            }
        }
    }

    @Override
    public void time(CustomEntity entity, int level) {

    }
}
