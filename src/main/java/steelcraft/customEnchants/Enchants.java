package steelcraft.customEnchants;

public enum Enchants {
    DIVER(), NIGHT_VISION(),

    WITHER(), SUMMONER(), CACTUS(), SPEED(), ANTI_GRAVITY(), VAMPIRE(), PURIFICATION(),

    BERSERK(), GHOST(), ZEUS, LIFE_STEEL(), CRITICAL_DAMAGE(), AUTO_SMELT(), EXPERIENCE(), SMASH();

    int cur = 0;
    final private CustomEnchant enchant;
    final private int ind;

    Enchants() {
        this.ind = cur;
        cur++;
        this.enchant = CustomEnchant.enchants.get(ind);
    }

    public CustomEnchant getEnchant() { return enchant; }
    public int getInd() { return ind; }
}
