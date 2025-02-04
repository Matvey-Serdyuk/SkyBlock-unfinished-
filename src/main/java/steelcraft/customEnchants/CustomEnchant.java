package steelcraft.customEnchants;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import steelcraft.skyPlayer.SkyPlayer;
import steelcraft.customEnchants.echants.*;
import steelcraft.customEntities.CustomEntity;
import steelcraft.utils.ItemTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

abstract public class CustomEnchant {

    final private static String symbol = ChatColor.BLACK + "CustomEnchant";

    final public static List <CustomEnchant> enchants = Arrays.asList(
            new Diver(),
            new NightVision(),

            new Wither(),
            new Summoner(),
            new Cactus(),
            new Speed(),
            new AntiGravity(),
            new Vampire(),
            new Purification(),

            new Berserk(),
            new Ghost(),
            new Zeus(),
            new LifeSteal(),
            new CriticalDamage(),
            new AutoSmelt(),

            new Experience(),
            new Smash());

    public String name;
    public float chance;
    public float riceChance;
    public int maxLevel;
    public byte rare;
    public List<Material> materials;

    final private String notOverlayMsg = ChatColor.RED + "Енчант не наложился";
    final private String overlayMsg = ChatColor.GREEN + "Енчант наложился";

    public CustomEnchant(String name, float chance, float riceChance, int maxLevel, byte rare, List<Material> materials) {
        this.name = CustomEnchantRare.colors[rare] + name;
        this.chance = chance;
        this.riceChance = riceChance;
        this.maxLevel = maxLevel;
        this.rare = rare;
        this.materials = materials;
    }

    abstract public void attack(EntityDamageByEntityEvent event, int level);

    abstract public void defend(EntityDamageByEntityEvent event, int level);

    abstract public void blockBreak(BlockBreakEvent event, int level);

    abstract public void time(CustomEntity entity, int level);

    public boolean checkChance(int level) {
        if (Math.random() * 100 < chance +  (level-1) * riceChance) {
            return true;
        }
        return false;
    }

    public static boolean canOverlay(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (!(event.getWhoClicked() instanceof Player)) {
            return false;
        }

        if (item == null) {return false;}
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {return false;}

        Player p = (Player) event.getWhoClicked();

        ItemStack enc = p.getItemOnCursor();
        if (enc.getItemMeta() == null) {return false;}
        List <String> lore = enc.getItemMeta().getLore();
        if (lore == null) {return false;}
        if (!lore.get(lore.size() - 1).equals(symbol)) {return false;}

        return true;
    }

    public static boolean isEnchant(ItemStack item) {
        if (item == null) {return false;}
        if (item.getType() != Material.BOOK) {return false;}
        if (item.getItemMeta() == null) {return false;}
        if (item.getItemMeta().getLore() == null) {return false;}
        if (!item.getItemMeta().getLore().get(item.getItemMeta().getLore().size()-1).equals(symbol)) {return false;}
        return true;
    }

    public boolean overlay(SkyPlayer player, ItemStack item, ItemStack enc) {
        CustomEnchant curEnchant = (CustomEnchant) getEnchantByString(enc.getItemMeta().getDisplayName()).keySet().toArray()[0];
        if (!this.name.equals(curEnchant.name)) {return false;}
        if (!materials.contains(item.getType())) { return true; }
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List <String> lore = meta.getLore();

        if (lore == null) { lore = new ArrayList<>(); }

        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).split(" ")[0].equals(name)) {
                player.sendMessage(ChatColor.YELLOW + "На этом предмете уже есть это зачарование", true);
                return true;
            }
        }

        if (Math.random() * 100 > getChance(enc)) {
            enc.setAmount(enc.getAmount() - 1);
            player.sendMessage(notOverlayMsg, true);
            return true;
        }

        assert enc.getItemMeta() != null;
        int ind = 0, curInd = 0;
        CustomEnchant loreEnchant;
        if (lore.size() != 0) {
            loreEnchant = (CustomEnchant) getEnchantByString(lore.get(curInd)).keySet().toArray()[0];
            for (CustomEnchant enchant : enchants) {
                if (enchant.name.equals(curEnchant.name)) {
                    break;
                }
                if (enchant.name.equals(loreEnchant.name)) {
                    if (lore.size() == curInd + 1) {
                        ind = -1;
                        break;
                    }
                    curInd++;
                    ind = curInd;
                    loreEnchant = (CustomEnchant) getEnchantByString(lore.get(curInd)).keySet().toArray()[0];
                }
            }
        }
        if (ind == -1) {
            lore.add(enc.getItemMeta().getDisplayName());
        } else {
            lore.add(ind, enc.getItemMeta().getDisplayName());
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
        enc.setAmount(enc.getAmount() - 1);
        player.sendMessage(overlayMsg, true);
        return true;
    }

    public static int getChance(ItemStack item) {
        if (item == null) { return -1; }
        if (item.getItemMeta() == null) { return -1; }
        List <String> lore = item.getItemMeta().getLore();
        if (lore == null) { return -1; }

        String str = lore.get(0).split(" ")[1];

        return Integer.parseInt(str.substring(0, str.length() - 1));
    }

    public static HashMap<CustomEnchant, Integer> getEnchants(LivingEntity livingEntity) {
        // Получить енчант и его сумарный уровень со всех инструментов
        HashMap<CustomEnchant, Integer> out = new HashMap<>();
        String name;
        List <ItemStack> items = new ArrayList<>();
        EntityEquipment equipment = livingEntity.getEquipment();
        if (equipment == null) { return out; }
        items.add(equipment.getItemInMainHand());
        items.addAll(Arrays.asList(equipment.getArmorContents()));

        List <String> lore;
        HashMap<CustomEnchant, Integer> curEnchant;
        for (ItemStack item : items) {
            if (item == null) {continue;}
            if (item.getType() == Material.AIR) {continue;}

            curEnchant = getEnchants(item);
            for (CustomEnchant enc : curEnchant.keySet()) {
                if (out.containsKey(enc)) {
                    out.put(enc, out.get(enc) + curEnchant.get(enc));
                } else {
                    out.put(enc, curEnchant.get(enc));
                }
            }
        }

        return out;
    }

    public static HashMap<CustomEnchant, Integer> getEnchants(ItemStack item) {
        HashMap<CustomEnchant, Integer> out = new HashMap<>();
        if (item == null) {return out;}
        if (item.getItemMeta() == null) {return out;}
        List <String> lore = item.getItemMeta().getLore();
        if (lore == null) {return out;}

        for (String str : lore) {
            out.putAll(getEnchantByString(str));
        }

        return out;
    }

    public static HashMap<CustomEnchant, Integer> getEnchantByString(String str) {
        HashMap<CustomEnchant, Integer> out = new HashMap<>();

        String[] split = str.split(" ");
        for (CustomEnchant enc : enchants) {
            if (split[0].equals(enc.name)) {
                out.put(enc, Levels.get(split[1]));
                return out;
            }
        }
        return out;
    }

    public static String getString(CustomEnchant enchant, int level) {
        return enchant.name + " " + Levels.get(level);
    }

    public ItemStack getEnchant(int level) {
        int chance = (int) Math.round(CustomEnchantRare.chanceOverlay[rare][0] + Math.random() *
                (CustomEnchantRare.chanceOverlay[rare][1] - CustomEnchantRare.chanceOverlay[rare][0]));
        return getEnchant(level, chance);
    }

    public ItemStack getEnchant(int level, int chance) {
        return ItemTools.createItem(Material.BOOK, name + " " + Levels.get(level), Arrays.asList(ChatColor.GRAY + "Шанс: " + chance + "%", symbol));
    }
}
