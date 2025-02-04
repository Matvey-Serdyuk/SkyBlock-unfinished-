package steelcraft.menu.ActionMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import steelcraft.menu.Menu;
import steelcraft.blocks.Spawner;
import steelcraft.utils.ItemTools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SpawnerMenu extends Menu {
    Spawner spawner;

    final private static int upgradeInd = 13;


    public SpawnerMenu(Spawner spawner) {
        super("Улучшение спавнера", 27, new HashMap<>());
        items.put(upgradeInd, ItemTools.createItem(Material.SPAWNER,  ChatColor.GOLD + "Улучшение",
                    Arrays.asList(ChatColor.GRAY + "Уровень: " + spawner.level + ChatColor.GOLD + " >> " + ChatColor.GRAY + (spawner.level+1))));
        this.spawner = spawner;
    }

    @Override
    public void clickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {return;}
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {return;}

        if (upgradeInd == event.getSlot()) {
            ItemStack item;
            for (ItemStack curItem : user.bukkitPlayer.getInventory().getContents()) {
                if (curItem == null) {continue;}
                if (curItem.getType() != Material.SPAWNER) {continue;}
                item = new ItemStack(curItem);
                item.setAmount(1);
                if (item.equals(spawner.item)) {
                    if (spawner.level != Spawner.MAX_LEVEL) {
                        curItem.setAmount(curItem.getAmount() - 1);
                        spawner.upgrade(user);
                        update();
                    } else {
                        user.sendMessage(ChatColor.YELLOW + "Спавнер максимального уровня!", true);
                    }
                    return;
                }
            }
            user.sendMessage(ChatColor.YELLOW + "У вас в инвентаре нету такого спавнера!", true);
        }
    }

    public void update() {
        ItemMeta meta = items.get(upgradeInd).getItemMeta();
        List <String> lore = meta.getLore();
        if (spawner.level != spawner.MAX_LEVEL) {
            lore.set(0, ChatColor.GRAY + "Уровень: " + spawner.level + ChatColor.GOLD + " >> " + ChatColor.GRAY + (spawner.level + 1));
        } else {
            lore.set(0, ChatColor.GREEN + "Спавнер максимального уровня");
        }
        meta.setLore(lore);
        items.get(upgradeInd).setItemMeta(meta);

        inv.setItem(upgradeInd, items.get(upgradeInd));
    }
}
