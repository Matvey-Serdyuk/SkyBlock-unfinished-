package steelcraft.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import steelcraft.utils.ItemTools;

import java.util.HashMap;

public class ConfirmMenu extends Menu{
    int yesInd = 19;
    int infoInd = 13;
    int noInd = 25;

    Runnable runnable;
    public ConfirmMenu(String info, Runnable runnable) {
        super("Подтверждение", 36, new HashMap<>());
        this.runnable = runnable;

        items.put(infoInd, ItemTools.createItem(Material.YELLOW_WOOL, info));

        items.put(yesInd, ItemTools.createItem(Material.GREEN_WOOL, ChatColor.GREEN + "Да!"));
        items.put(noInd, ItemTools.createItem(Material.RED_WOOL, ChatColor.RED + "Нет!"));
    }

    @Override
    public void clickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {return;}
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {return;}

        if (event.getSlot() == yesInd) {
            runnable.run();
            close();
        } else if (event.getSlot() == noInd) {
            close();
        }
    }
}
