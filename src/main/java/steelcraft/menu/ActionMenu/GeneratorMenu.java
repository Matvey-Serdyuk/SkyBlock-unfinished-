package steelcraft.menu.ActionMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import steelcraft.economy.Economy;
import steelcraft.menu.Menu;
import steelcraft.blocks.Generator;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.utils.Format;
import steelcraft.utils.ItemTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeneratorMenu extends Menu {
    final private int infoInd = 10;
    final private int upgradeInd = 13;
    final private int statusInd = 16;

    public Generator generator;

    public GeneratorMenu(Generator generator) {
        super("Улучшение генератора", 27, new HashMap<>());
        this.generator = generator;

        items.put(infoInd, new ItemStack(Material.STONE));
        ItemMeta meta = items.get(infoInd).getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.YELLOW + "Информация");
        items.get(infoInd).setItemMeta(meta);

        items.put(upgradeInd, new ItemStack(generator.item));
        meta = items.get(upgradeInd).getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD + "Улучшение");
        items.get(upgradeInd).setItemMeta(meta);

        items.put(statusInd, ItemTools.createItem(Material.BARRIER, ChatColor.YELLOW + "Состояние"));
    }

    @Override
    public void clickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {return;}
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {return;}

        if (event.getSlot() == upgradeInd) {
            if (generator.level != Generator.MAX_LEVEL) {
                if (Generator.cost[generator.level].canBuy(user)) {
                    user.changeMoney(Generator.cost[generator.level].cost * -1, true);
                    generator.level++;
                    user.sendMessage(ChatColor.GOLD + "Вы улучшили генератор", true);
                    update();
                }
            }
            return;
        }
        if (event.getSlot() == statusInd) {
            generator.isWork = !generator.isWork;
            generator.update();
            update();
        }
    }

    public void open(SkyPlayer player) {
        super.open(player);
        update();
    }

    public void update() {
        List<String> lore = new ArrayList<>();
        HashMap<Material, String> labels = new HashMap<Material, String>() {{
            put(Material.COAL_ORE, "Угольная руда");
            put(Material.IRON_ORE, "Железная руда");
            put(Material.GOLD_ORE, "Золотая руда");
            put(Material.LAPIS_ORE, "Лазуритная руда");
            put(Material.REDSTONE_ORE, "Редстоуновая руда");
            put(Material.DIAMOND_ORE, "Алмазная руда");
            put(Material.EMERALD_ORE, "Изумрудная руда");
        }};

        HashMap <Material, Float> materials =  Generator.materials.get(generator.level);
        if (generator.level != Generator.MAX_LEVEL) {
            for (Material material : labels.keySet()) {
                if (materials.get(material) == null) {continue;}
                lore.add(ChatColor.GRAY + labels.get(material) + ": " + materials.get(material) + "%" +
                        ChatColor.GOLD + " >> " + ChatColor.GREEN + Generator.materials.get(generator.level + 1).get(material) + "%");
            }

            if(materials.size() != Generator.materials.get(generator.level + 1).size()) {
                lore.add(ChatColor.GOLD + "+ новые руды");
            }
        } else {
            for (Material material : labels.keySet()) {
                lore.add(ChatColor.GRAY + labels.get(material) + ": " + materials.get(material) + "%");
            }
        }

        ItemMeta meta = items.get(infoInd).getItemMeta();
        assert meta != null;
        meta.setLore(lore);
        items.get(infoInd).setItemMeta(meta);


        meta = items.get(upgradeInd).getItemMeta();
        assert meta != null;
        lore = new ArrayList<>();
        String color;
        if (Generator.cost.length == generator.level) {
            meta.setDisplayName(ChatColor.GOLD + "Максимальный уровень!");
        } else {
            if (Economy.canBuy(user, Generator.cost[generator.level].cost, false)) {
                color = "" + ChatColor.GREEN;
            } else {
                color = "" + ChatColor.RED;
            }
            lore.add(ChatColor.BLUE + "Стоимость: ");
            lore.add(ChatColor.GRAY + " - " + ChatColor.YELLOW + "" + Format.getMoney(Generator.cost[generator.level].cost) +
                    ChatColor.GRAY + "/" + color + Format.getMoney(user.money));

            if (Generator.cost[generator.level].haveEnoughBlocks(user)) {
                color = "" + ChatColor.GREEN;
            } else {
                color = "" + ChatColor.RED;
            }
            lore.add(ChatColor.GRAY + " - " + ChatColor.YELLOW + Format.getBlocks(Generator.cost[generator.level].blockCost) +
                    ChatColor.GRAY + "/" + color + Format.getBlocks(user.generatorBlocks));
        }

        meta.setLore(lore);
        items.get(upgradeInd).setItemMeta(meta);


        meta = items.get(statusInd).getItemMeta();
        assert meta != null;
        if (generator.isWork) {
            items.get(statusInd).setType(Material.GREEN_STAINED_GLASS_PANE);
            meta.setDisplayName(ChatColor.GREEN + "Включено");
        } else {
            items.get(statusInd).setType(Material.RED_STAINED_GLASS_PANE);
            meta.setDisplayName(ChatColor.GREEN + "Отключено");
        }

        items.get(statusInd).setItemMeta(meta);

        inv.setItem(infoInd, items.get(infoInd));
        inv.setItem(upgradeInd, items.get(upgradeInd));
        inv.setItem(statusInd, items.get(statusInd));
    }
}
