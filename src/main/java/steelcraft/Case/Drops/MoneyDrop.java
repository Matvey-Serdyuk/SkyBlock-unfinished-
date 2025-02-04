package steelcraft.Case.Drops;

import org.bukkit.Material;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.utils.Format;
import steelcraft.utils.ItemTools;

public class MoneyDrop extends Drop {
    public double money;

    public MoneyDrop(double money, float chance, Rare rare) {
        super(ItemTools.createItem(Material.SUNFLOWER, Format.getMoney(money)), chance, rare);

        this.money = money;
    }

    @Override
    public void getDrop(SkyPlayer player) {
        player.changeMoney(money, true);
    }
}
