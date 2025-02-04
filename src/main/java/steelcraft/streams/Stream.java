package steelcraft.streams;

import java.util.Timer;
import java.util.TimerTask;

abstract public class Stream extends TimerTask {
    public Timer timer = null;
    public TimerTask timerTask;
    public int period;

    public boolean isWork = false;

    public Stream(int period) {
        this.period = period;
    }

    public void start() {
        timerTask = this;
        timer = new Timer(false);
        timer.scheduleAtFixedRate(timerTask, 1000, period);

        isWork = true;
    }

    public void stop() {
        timer.cancel();

        isWork = false;
    }
}
