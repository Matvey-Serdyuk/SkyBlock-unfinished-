package steelcraft.streams;

import steelcraft.auction.CheckAuctionItemsStream;
import steelcraft.main.Main;
import steelcraft.blocks.Spawner;

public class StreamManager {
    public static boolean isWork = false;
    public static Stream[] streams = {new UpdateBoardStream(1500) , new CheckAuctionItemsStream(800)};

    public static void start() {
        isWork = true;
        Main.secondStream.start();
        for (Stream stream : streams) {
            stream.start();
        }

        for (Spawner spawner : Spawner.getAllSpawners()) {
            spawner.tickStream = new SpawnerTick(spawner, 10000);
            spawner.tickStream.start();
        }

        Main.plugin.getLogger().info("Started stream");
    }

    public static void stop() {
        isWork = false;
        Main.secondStream.stop();

        for (Stream stream : streams) {
            stream.stop();
        }

        for (Spawner spawner : Spawner.getAllSpawners()) {
            spawner.deSpawn();
            spawner.tickStream.stop();
        }

        Main.plugin.getLogger().info("Stopped stream");
    }
}
