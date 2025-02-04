package steelcraft.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;


public class SkullTools {
    public static final String DIAMOND_BLOCK = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTAwYjI2YTQyZGYxM2M3Njk5NDJiMDE3MjdlMGE0MjA1YmJkNTZjNjFjNWZiZDI1Y2UzNWYzZDc0NzhjNzNiOCJ9fX0";
    public static final String IRON_BLOCK = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmZjNWUyODZkOWMxZDE5ZDZkNjA1OWQ2OWIzMzEzOWYxOGNlNjlmYWVmZjBmZWU4OTRkMDRlOTNlN2NiOTg2NyJ9fX0";
    public static final String LEFT_ARROW = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ";
    public static final String TRAINER = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzEyYzJiY2QwYjAyNTdmYjg0MDcyMzgwMzNkYTU5MjYzZTFjOTRmZWFkMWM5Yjk4N2FiN2EwZjUzNTA0NGJiYSJ9fX0";
    public static final String DONATE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODVlNzExMmQ1NjI5NjVmM2EzYWMzNDExMTZmMmUxNzc1ZTZiMmViNTdkY2YxMjdkMzM0ZDg5NDRjOGMxYWNiYiJ9fX0";

    public static ItemStack createSkull(String url, String displayName, List<String> lore) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        if (url.isEmpty())
            return head;

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }
        if (displayName != null) {
            headMeta.setDisplayName(displayName);
        }
        if (lore != null) {
            headMeta.setLore(lore);
        }
        head.setItemMeta(headMeta);
        return head;
    }
}
