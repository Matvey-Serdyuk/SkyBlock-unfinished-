package steelcraft.skyPlayer;

import steelcraft.Island.Island;
import steelcraft.skyPlayer.data.IslandPlayerData;

public class IslandPlayer {
    public Island island;
    public SkyPlayer skyPlayer;
    public boolean owner;

    public IslandPlayer(Island island, SkyPlayer player, boolean owner) {
        this.island = island;
        this.skyPlayer = player;
        this.owner = owner;
    }

    public IslandPlayer(Island island, IslandPlayerData data) {
        this.island = island;
        island.players.add(this);
        skyPlayer = new SkyPlayer(data.skyPlayerData);
        skyPlayer.islandPlayer = this;
        owner = data.owner;
    }
}
