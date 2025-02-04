package steelcraft.economy;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SpecialCost {
    public double moneyCost;
    public HashMap<Material, Integer> blocksCost;

    public SpecialCost(double moneyCost, HashMap<Material, Integer> blockCost) {
        this.moneyCost = moneyCost;
        this.blocksCost = blockCost;
    }
}
