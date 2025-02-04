package steelcraft.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import steelcraft.customEnchants.CustomEnchant;
import steelcraft.main.Main;
import steelcraft.utils.ItemTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BsMenu extends Menu {
    final int cost = 25000;

    final int firstItemInd = 10;
    final int secondItemInd = 13;
    final int resultInd = 16;

    public BsMenu() {
        super("Наковальня", 27, new HashMap<>());
        for (int i = 0; i < 27; i++) {
            items.put(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }
        items.remove(firstItemInd);
        items.remove(secondItemInd);
        items.remove(resultInd);
    }

    @Override
    public void clickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {return;}

        if (event.getClickedInventory().getType() == InventoryType.PLAYER && event.getCurrentItem() != null) {
            if (inv.getItem(firstItemInd) == null) {
                // первый item
                inv.setItem(firstItemInd, new ItemStack(event.getCurrentItem()));
                event.getClickedInventory().setItem(event.getSlot(), null);
            } else if (inv.getItem(secondItemInd) == null) {
                // второй item
                inv.setItem(secondItemInd, new ItemStack(event.getCurrentItem()));
                event.getClickedInventory().setItem(event.getSlot(), null);

                if (inv.getItem(firstItemInd).getType() != inv.getItem(secondItemInd).getType() ||
                        inv.getItem(firstItemInd).getItemMeta() == null || inv.getItem(secondItemInd).getItemMeta() == null) {
                    // ошибка
                    inv.setItem(resultInd, ItemTools.createItem(Material.BARRIER, ChatColor.RED + "Ошибка!"));
                    return;
                }

                // совмещение
                HashMap<CustomEnchant, Integer> firstEnchants = CustomEnchant.getEnchants(inv.getItem(firstItemInd));
                HashMap<CustomEnchant, Integer> secondEnchants = CustomEnchant.getEnchants(inv.getItem(secondItemInd));
                if (CustomEnchant.isEnchant(inv.getItem(firstItemInd)) && CustomEnchant.isEnchant(inv.getItem(secondItemInd))) {
                    String firstName = inv.getItem(firstItemInd).getItemMeta().getDisplayName();
                    String secondName = inv.getItem(secondItemInd).getItemMeta().getDisplayName();
                    firstEnchants = CustomEnchant.getEnchantByString(firstName);
                    secondEnchants = CustomEnchant.getEnchantByString(secondName);

                    CustomEnchant firstEnc = (CustomEnchant) firstEnchants.keySet().toArray()[0];
                    if (firstEnchants.equals(secondEnchants) && firstEnchants.get(firstEnc) != firstEnc.maxLevel) {
                        ItemStack item = new ItemStack(inv.getItem(firstItemInd));
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(CustomEnchant.getString(firstEnc, firstEnchants.get(firstEnc) + 1));
                        item.setItemMeta(meta);
                        inv.setItem(resultInd, item);
                    } else {
                        inv.setItem(resultInd, ItemTools.createItem(Material.BARRIER, ChatColor.RED + "Ошибка!"));
                    }
                    return;
                }

                boolean isSpecial = true;
                for (CustomEnchant firstEnchant : firstEnchants.keySet()) {
                    for (CustomEnchant secondEnchant : secondEnchants.keySet()) {
                        if (firstEnchant.name.equals(secondEnchant.name)) {
                            if (firstEnchants.get(firstEnchant) < secondEnchants.get(secondEnchant)) {
                                firstEnchants.put(firstEnchant, secondEnchants.get(secondEnchant));
                            } else if (firstEnchants.get(firstEnchant).equals(secondEnchants.get(secondEnchant)) &&
                                    firstEnchants.get(firstEnchant) != firstEnchant.maxLevel) {
                                firstEnchants.put(firstEnchant, firstEnchants.get(firstEnchant) + 1);
                            }
                        }
                    }
                }

                ItemStack item = new ItemStack(inv.getItem(firstItemInd));
                ItemMeta meta = item.getItemMeta();
                List<String> lore = new ArrayList<>();
                for (CustomEnchant enchant : firstEnchants.keySet()) {
                    lore.add(CustomEnchant.getString(enchant, firstEnchants.get(enchant)));
                }
                meta.setLore(lore);
                item.setItemMeta(meta);
                inv.setItem(resultInd, item);
            }
        } else if (event.getInventory().equals(inv)) {
            if (event.getSlot() == firstItemInd && inv.getItem(firstItemInd) != null) {
                user.bukkitPlayer.getInventory().addItem(new ItemStack(inv.getItem(firstItemInd)));
                inv.setItem(firstItemInd, null);

                if (inv.getItem(secondItemInd) != null) {
                    user.bukkitPlayer.getInventory().addItem(new ItemStack(inv.getItem(secondItemInd)));
                    inv.setItem(secondItemInd, null);
                }

                if (inv.getItem(resultInd) != null) {
                    inv.setItem(resultInd, null);
                }
                return;
            }
            if (event.getSlot() == secondItemInd && inv.getItem(secondItemInd) != null) {
                user.bukkitPlayer.getInventory().addItem(new ItemStack(inv.getItem(secondItemInd)));
                inv.setItem(secondItemInd, null);
                if (inv.getItem(resultInd) != null) {
                    inv.setItem(resultInd, null);
                }
                return;
            }
            if (event.getSlot() == resultInd && inv.getItem(resultInd) != null) {
                if (inv.getItem(resultInd).getType() == Material.BARRIER) {return;}

                if (user.bukkitPlayer.getTotalExperience() > cost) {
                    user.bukkitPlayer.getInventory().addItem(new ItemStack(inv.getItem(resultInd)));
                    inv.setItem(firstItemInd, null);
                    inv.setItem(secondItemInd, null);
                    inv.setItem(resultInd, null);
                    user.sendMessage(ChatColor.GREEN + "Соединение прошло успешно!", false);
                    user.changeExp(cost * -1, true);
                } else {
                    close();
                    user.bukkitPlayer.sendTitle(ChatColor.RED + "У вас недостаточно опыта!",
                            ChatColor.YELLOW + "Соединение стоит " + cost + " (у вас " +
                                    user.bukkitPlayer.getTotalExperience() + ")");
                }
            }
        }
    }

    public void close() {
        if (inv.getItem(firstItemInd) != null) {
            user.bukkitPlayer.getInventory().addItem(new ItemStack(inv.getItem(firstItemInd)));
        }
        if (inv.getItem(secondItemInd) != null) {
            user.bukkitPlayer.getInventory().addItem(new ItemStack(inv.getItem(secondItemInd)));
        }
        super.close();
    }
}
