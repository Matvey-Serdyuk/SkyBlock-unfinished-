package steelcraft.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.meta.ItemMeta;
import steelcraft.Island.Area;
import steelcraft.Island.Island;
import steelcraft.commands.Ec;
import steelcraft.economy.Economy;
import steelcraft.economy.RiseCost;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.utils.ItemTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpgradeIslandMenu extends Menu {
    final int MAX_PLAYERS = 10;
    final int MAX_SIZE = 150;
    final int MAX_SPAWNERS = 20;

    public RiseCost islandSizeCost = new RiseCost(10, 10, 1.05f, MAX_SIZE - Island.startSize);
    public RiseCost maxSpawnersCost = new RiseCost(10000, 10000, 1.1f, MAX_SPAWNERS - Island.startMaxSpawners);
    public RiseCost maxPlayersCost = new RiseCost(25000, 50000, 1.1f, MAX_PLAYERS - Island.startMaxPlayers);

    final int PLUS_SIZE = 1;

    int indSize = 1;
    int indSlot = 7;
    int indMaxSpawners = 4;

    Island island;
    public UpgradeIslandMenu(Island island) {
        super("Улучшение острова", 9, new HashMap<>());
        for (int i = 1; i < 9; i += 2) {
            items.put(i, ItemTools.createItem(Material.GRAY_STAINED_GLASS_PANE, ""));
        }
        items.put(indSize, ItemTools.createItem(Material.GRASS_BLOCK, ChatColor.GREEN + "Увеличение размера"));
        items.put(indSlot, ItemTools.createItem(Material.PLAYER_HEAD, ChatColor.YELLOW + "Увеличение слотов"));
        items.put(indMaxSpawners, ItemTools.createItem(Material.SPAWNER, ChatColor.GRAY + "Увеличение максимального количество спавнеров"));

        this.island = island;
    }

    @Override
    public void clickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {return;}
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {return;}

        double cost;
        if (event.getSlot() == indSize) {
            cost = islandSizeCost.cost[island.area.size - Island.startSize];
            if (Economy.canBuy(user, cost, true)) {
                if (island.area.size != MAX_SIZE) {
                    island.area = new Area(island.area.center, island.area.size + PLUS_SIZE);
                    user.changeMoney(cost * -1, true);
                    update();
                }
            }
        } else if (event.getSlot() == indSlot) {
            cost = maxPlayersCost.cost[island.maxPlayers - Island.startMaxPlayers];
            if (Economy.canBuy(user, cost, true)) {
                if (island.maxPlayers != MAX_PLAYERS) {
                    island.maxPlayers++;
                    update();
                }
            }
        } else if (event.getSlot() == indMaxSpawners) {
            cost = maxSpawnersCost.cost[island.maxSpawners - Island.startMaxSpawners];
            if (Economy.canBuy(user, cost, true)) {
                if (island.maxSpawners != MAX_SPAWNERS) {
                    island.maxSpawners++;
                    update();
                }
            }
        }
    }

    public void open(SkyPlayer player) {
        super.open(player);
        update();
    }

    public void update() {
        List<String> lore = new ArrayList<>();
        ItemMeta meta;
        meta = items.get(indSize).getItemMeta();
        assert meta != null;

        if (island.maxPlayers != MAX_SIZE) {
            lore.add("" + island.area.size + ChatColor.GOLD + " >> " +
                    ChatColor.GRAY + (island.area.size + PLUS_SIZE));
        } else {
            meta.setDisplayName(ChatColor.GOLD + "У вас максимальный размер острова");
            lore.add("" + ChatColor.GRAY + island.area.size);
        }
        meta.setLore(lore);
        items.get(indSize).setItemMeta(meta);


        lore = new ArrayList<>();
        meta = items.get(indSlot).getItemMeta();
        assert meta != null;

        if (island.maxPlayers != MAX_PLAYERS) {
            lore.add("" + ChatColor.GRAY + island.maxPlayers + ChatColor.GOLD + " >> " +
                    ChatColor.GRAY + (island.maxPlayers + 1));
        } else {
            meta.setDisplayName(ChatColor.GOLD + "У вас максимальное количество слотов");
            lore.add("" + ChatColor.GRAY + island.maxPlayers);
        }
        meta.setLore(lore);
        items.get(indSlot).setItemMeta(meta);


        lore = new ArrayList<>();
        meta = items.get(indMaxSpawners).getItemMeta();
        assert meta != null;

        if (island.maxSpawners != MAX_SPAWNERS) {
            lore.add("" + ChatColor.GRAY + island.maxSpawners + ChatColor.GOLD + " >> " +
                    ChatColor.GRAY + (island.maxSpawners + 1));
        } else {
            meta.setDisplayName(ChatColor.GOLD + "У вас максимальное количество спавнеров");
            lore.add("" + ChatColor.GRAY + island.maxSpawners);
        }
        meta.setLore(lore);
        items.get(indMaxSpawners).setItemMeta(meta);

        for (int ind : items.keySet()) {
            inv.setItem(ind, items.get(ind));
        }
    }
}
