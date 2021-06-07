package it.stack9.menuapi.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ItemUtils {

    public static ItemStack craft(ItemStack item, String name, String... lore) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(Chat.tr("&r" + name));
            meta.setLore(Arrays.stream(lore).map(Chat::tr).collect(Collectors.toList()));
            item.setItemMeta(meta);
        }

        return item;
    }

    public static ItemStack craft(Material material, String name, String... lore) {
        return craft(new ItemStack(material), name, lore);
    }
}
