package steelcraft.customEntities.Bosses;

import org.bukkit.boss.BossBar;
import steelcraft.skyPlayer.SkyPlayer;

import java.util.ArrayList;
import java.util.List;

public class Bar {
    public enum BarType {
        BOSS_BAR(0), SERVER_BAR(1);

        final private int num;

        BarType(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }

    public BossBar bukkitBar;
    public BarType type;
    public List<SkyPlayer> players = new ArrayList<>();

    public Bar(BossBar bukkitBar, BarType type) {
        this.bukkitBar = bukkitBar;
        this.type = type;
    }

    public void addPlayer(SkyPlayer player) {
        players.add(player);
        player.bars.add(this);
        bukkitBar.addPlayer(player.bukkitPlayer);
    }

    public void removePlayer(SkyPlayer player) {
        players.remove(player);
        player.bars.remove(this);
        bukkitBar.removePlayer(player.bukkitPlayer);
    }

    public boolean contains(Object object) {
        return players.contains(object);
    }

    public void setProgress(double progress) {
        bukkitBar.setProgress(progress);
    }

    public void removeAll() {
        for (int i = 0; i < players.size(); i++) {
            removePlayer(players.get(i));
            i--;
        }
    }
}
