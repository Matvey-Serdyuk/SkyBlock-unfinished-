package steelcraft.customEnchants;

import org.bukkit.ChatColor;

public class CustomEnchantRare {
    final public static byte NORMAL = 0;
    final public static byte RARE = 1;
    final public static byte LEGEND = 2;

    final public static String[] colors = {"" + ChatColor.GREEN, "" + ChatColor.BLUE, "" + ChatColor.RED};
    final public static int[][] chanceOverlay = {new int[]{10, 95}, new int[]{10, 80}, new int[]{10, 65}};
}
