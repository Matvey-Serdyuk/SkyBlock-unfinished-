package steelcraft.skyPlayer.data;


import steelcraft.skyPlayer.SkyPlayer;

public class SkyPlayerData {
    public String name;
    public double money;
    public boolean pvp;

    public SkyPlayerData(SkyPlayer player) {
        name = player.name;
        money = player.money;
        pvp = player.pvp;
    }
}
