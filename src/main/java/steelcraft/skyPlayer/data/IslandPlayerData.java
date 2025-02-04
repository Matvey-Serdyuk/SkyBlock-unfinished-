package steelcraft.skyPlayer.data;

import steelcraft.skyPlayer.IslandPlayer;

public class IslandPlayerData {
    public SkyPlayerData skyPlayerData;
    public boolean owner;

    public IslandPlayerData(IslandPlayer player) {
        skyPlayerData = new SkyPlayerData(player.skyPlayer);
        owner = player.owner;
    }
}
