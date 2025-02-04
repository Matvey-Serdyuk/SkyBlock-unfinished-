package steelcraft.Case.Drops;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import steelcraft.skyPlayer.SkyPlayer;

abstract public class Drop {
    public static enum Rare {
        NORMAL(0, ChatColor.GREEN), RARE(1, ChatColor.DARK_GREEN), VERY_RARE(2, ChatColor.BLUE),
        LEGEND(3, ChatColor.RED);

        final private int num;
        final private ChatColor color;

        Rare(int num, ChatColor color) {
            this.num = num;
            this.color = color;
        }

        public int getNum() {
            return num;
        }

        public ChatColor getColor() {
            return color;
        }
    }

    public float chance;
    public Rare rare;
    public ItemStack item;

    public Drop(ItemStack item, float chance, Rare rare) {
        this.item = item;
        this.chance = chance;
        this.rare = rare;
    }

    abstract public void getDrop(SkyPlayer player);
}
