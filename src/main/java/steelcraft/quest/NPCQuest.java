package steelcraft.quest;

import org.bukkit.event.player.PlayerInteractEvent;

abstract public class NPCQuest extends Quest {
    public NPCQuest(Mission[] missions) {
        super(missions);
    }

    abstract public boolean action(PlayerInteractEvent event);
}
