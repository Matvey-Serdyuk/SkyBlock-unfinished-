package steelcraft.streams;

abstract public class MainStream extends Stream{

    public MainStream(int period) {
        super(period);
    }

    abstract public void start();
}
