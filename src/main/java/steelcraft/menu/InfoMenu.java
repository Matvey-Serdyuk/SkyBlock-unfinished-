package steelcraft.menu;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class InfoMenu extends Menu {
    public InfoMenu(String title, int size, HashMap<Integer, ItemStack> items) {
        super(title, size, items);
    }

    @Override
    public void clickEvent(InventoryClickEvent event) {

    }
}
