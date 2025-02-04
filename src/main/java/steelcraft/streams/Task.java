package steelcraft.streams;

import java.util.Timer;
import java.util.TimerTask;

abstract public class Task extends TimerTask {
    public Timer timer = null;
    public TimerTask timerTask;
    public long delay;

    public Task(long delay) {
        this.delay = delay;
    }

    public void start() {
        timerTask = this;
        timer = new Timer(false);
        timer.schedule(timerTask, delay);
    }

    public void stop() {
        timer.cancel();
    }
}
