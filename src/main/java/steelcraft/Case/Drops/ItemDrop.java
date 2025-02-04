package steelcraft.Case.Drops;

import org.bukkit.inventory.ItemStack;
import steelcraft.skyPlayer.SkyPlayer;

public class ItemDrop extends Drop{
    public ItemDrop(ItemStack item, float chance, Rare rare) {
        super(item, chance, rare);
    }

    public ItemDrop(ItemStack item, float chance) {
        super(item, chance, Rare.NORMAL);
    }

    @Override
    public void getDrop(SkyPlayer player) {
        player.bukkitPlayer.getInventory().addItem(item);
    }

    public boolean check() {
        return Math.random() <= chance;
    }
}
