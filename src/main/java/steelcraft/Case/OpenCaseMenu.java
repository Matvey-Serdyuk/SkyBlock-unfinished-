package steelcraft.Case;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.menu.Menu;
import steelcraft.utils.ItemTools;

import java.util.HashMap;

public class OpenCaseMenu extends Menu {
    public OpenCase openCase;
    public ItemStack[] items;

    public OpenCaseMenu(OpenCase openCase) {
        super("Кейс", 27, new HashMap<>());
        this.openCase = openCase;
        items = new ItemStack[openCase.drops.length];
        for (int i = 0; i < openCase.drops.length; i++) {
            items[i] = openCase.drops[i].item;
        }
    }

    public void setCur(int cur) {
        for (int i = 9; i < 18; i++) {
            inv.setItem(i, items[cur - (13-i)]);
        }
    }

    public void close() {
        super.close();
        user.openCase = false;
        openCase.drops[openCase.spin].getDrop(user);
    }

    public void open(SkyPlayer player) {
        super.open(player);

        for (int i = 0; i < 9; i++) {
            inv.setItem(i, ItemTools.createItem(Material.GRAY_STAINED_GLASS_PANE, ""));
            inv.setItem(18 + i, ItemTools.createItem(Material.GRAY_STAINED_GLASS_PANE, ""));
        }
        inv.setItem(4, ItemTools.createItem(Material.GREEN_STAINED_GLASS_PANE, ""));
        inv.setItem(22, ItemTools.createItem(Material.GREEN_STAINED_GLASS_PANE, ""));

        user.openCase = true;
    }

    @Override
    public void clickEvent(InventoryClickEvent event) {

    }
}
