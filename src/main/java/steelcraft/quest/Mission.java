package steelcraft.quest;

import steelcraft.Case.Reward;
import steelcraft.skyPlayer.SkyPlayer;

abstract public class Mission {
    public boolean isCompleted = false;
    public Runnable checkRun;
    public Reward[] rewards;

    public Mission(Runnable checkRun, Reward[] rewards) {
        this.checkRun = checkRun;
        this.rewards = rewards;
    }

    public boolean check(SkyPlayer player) {
        checkRun.run();
        if (isCompleted) {
            implemented();
        } else {
            notImplemented();
        }
        return isCompleted;
    }

    abstract public void implemented();

    abstract public void notImplemented();
}
