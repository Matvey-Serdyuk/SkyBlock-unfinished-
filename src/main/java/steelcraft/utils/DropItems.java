package steelcraft.utils;

import org.bukkit.inventory.ItemStack;

public class DropItems {
    public ItemStack[] items;
    public int[] amounts;
    public float[] chances;

    public DropItems(ItemStack[] items, float[] chances, int[] amounts) {
        this.items = items;
        this.chances = chances;
        this.amounts = amounts;
    }

    public ItemStack getItem() {
        float chance = 0;
        double rand = Math.random() * 100;
        for (int i = 0; i < items.length; i++) {
            chance += chances[i];
            if (rand <= chance) {
                ItemStack drop = new ItemStack(items[i]);
                drop.setAmount(amounts[i]);
                return drop;
            }
        }
        return null;
    }
}
