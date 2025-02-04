package steelcraft.quest;

abstract public class Quest {
    public int cur;
    public Mission[] missions;

    public boolean isCompleted = false;

    public Quest(Mission[] missions) {
        this.missions = missions;
        cur = 0;
    }

    public void next() {
        cur++;
        if (cur == missions.length) {
            isCompleted = true;
        }
    }
}
