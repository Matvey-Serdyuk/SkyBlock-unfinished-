package steelcraft.utils;

import org.bukkit.ChatColor;

import java.text.DecimalFormat;

public class Format {
    public static String pointNumber(double num) {
        if (num <= 100) {
            // 0.001
            DecimalFormat decimalFormat = new DecimalFormat("#.###");
            return decimalFormat.format(num);
        }
        if (num > 1000000000000d) {
            // 1,01T
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            return decimalFormat.format(num / 1000000000000d) + "T";
        }
        if (num > 1000000000) {
            // 1,01B
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            return decimalFormat.format(num / 1000000000) + "B";
        }
        if (num > 1000000) {
            // 1,01M
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            return decimalFormat.format(num / 1000000) + "M";
        }
        if (num > 100000) {
            // 100,01к
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            return decimalFormat.format(num / 1000) + "K";
        }
        if (num > 10000) {
            // 10001
            DecimalFormat decimalFormat = new DecimalFormat("#");
            return decimalFormat.format(num);
        }
        if (num > 1000) {
            // 1000,1
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            return decimalFormat.format(num);
        }
        // 10,01
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(num);
    }

    public static String getMoney(double money) {
        return pointNumber(money) + ChatColor.GREEN + "$";
    }

    public static String getBlocks(long blocks) {
        return blocks + "§b⧠";
    }

    public static String getTimer(long time) {
        time /= 1000;
        if (time < 0) {
            return "00:00";
        }
        String out = "";
        long hours = time / 3600;
        if (hours < 10 && hours != 0) {
            out += "0" + hours + ":";
        } else if (hours != 0){
            out += hours + ":";
        }
        long minutes = time % 3600 / 60;
        if (minutes < 10) {
            out += "0" + minutes + ":";
        } else {
            out += minutes + ":";
        }
        long sec = time % 60;
        if (sec < 10) {
            out += "0" + sec;
        } else {
            out += sec;
        }
        return out;
    }
}

