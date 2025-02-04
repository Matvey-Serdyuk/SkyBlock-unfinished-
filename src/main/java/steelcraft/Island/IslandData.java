package steelcraft.Island;

import steelcraft.blocks.Generator;
import steelcraft.blocks.Spawner;
import steelcraft.blocks.data.GeneratorData;
import steelcraft.blocks.data.SpawnerData;
import steelcraft.skyPlayer.IslandPlayer;
import steelcraft.skyPlayer.data.IslandPlayerData;
import steelcraft.utils.Vector3;

import java.util.ArrayList;
import java.util.List;

public class IslandData {
    public String name;
    public String worldName;
    public int maxPlayers;
    public int maxSpawners;
    public int size;
    public List<IslandPlayerData> players = new ArrayList<>();
    public Vector3 center;
    public Vector3 spawn;

    public List<GeneratorData> generators = new ArrayList<>();
    public List<SpawnerData> spawners = new ArrayList<>();

    public IslandData(Island island) {
        name = island.name;
        assert island.area.start.getWorld() != null;
        worldName = island.area.start.getWorld().getName();
        maxPlayers = island.maxPlayers;
        maxSpawners = island.maxSpawners;
        for (IslandPlayer player : island.players) {
            players.add(new IslandPlayerData(player));
        }
        size = island.area.size;
        center = new Vector3(island.center);
        spawn = new Vector3(island.spawn);

        for (Generator generator : island.generators) {
            generators.add(new GeneratorData(generator));
        }

        for (Spawner spawner : island.spawners) {
            spawners.add(new SpawnerData(spawner));
        }
    }
}
