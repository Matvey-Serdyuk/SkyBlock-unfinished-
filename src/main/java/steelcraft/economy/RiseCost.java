package steelcraft.economy;

import java.util.ArrayList;
import java.util.List;

public class RiseCost {
    private static List<RiseCost> allRiseCost = new ArrayList<>();

    public double startCost;
    public float cof;
    public float plus;
    public int maxLevel;
    public double[] cost;

    public RiseCost(double startCost, float plus, float cof, int maxLevel) {
        this.startCost = startCost;
        this.cof = cof;
        this.plus = plus;
        this.maxLevel = maxLevel;
        cost = new double[maxLevel];
        allRiseCost.add(this);
    }

    public void set() {
        cost[0] = startCost;
        for (int i = 1; i < maxLevel; i++) {
            cost[i] = (cost[i-1] + plus) * cof;
        }
    }

    public static void setAll() {
        for (RiseCost riseCost : allRiseCost) {
            riseCost.set();
        }
    }
}
