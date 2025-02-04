package steelcraft.Case;

import net.minecraft.server.v1_13_R2.ItemStack;

public class Reward {
    enum RewardType {
        MONEY(0), ITEM(1);

        private int num;

        RewardType(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }

    public RewardType type;
    public int amount;
    public ItemStack item;

    public Reward(int amount) {
        this.amount = amount;
        this.type = RewardType.MONEY;
    }

    public Reward(ItemStack item, int amount) {
        this.item = item;
        this.amount = amount;
        this.type = RewardType.ITEM;
    }
}
