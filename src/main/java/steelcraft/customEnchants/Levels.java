package steelcraft.customEnchants;

public class Levels {
    static String[] rome = {null, "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

    public static String get(int level) {
        return rome[level];
    }

    public static int get(String level) {
        for (int i = 1; i < rome.length; i++) {
            if (rome[i].equals(level)) {
                return i;
            }
        }
        return -1;
    }
}
