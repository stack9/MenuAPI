package it.stack9.menuapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * MenuAPI
 * @author stack
 * @date 5/21/21
 */
public class Item {

    private static final String version;

    private static final Class<?> materialClass;
    private static final Class<?> itemStackClass;
    private static Constructor<?> glassPaneConstructor;
    private static final int major;
    private static final Class<?> craftItemStackClass;
    private static final Class<?> nmsItemStackClass;
    private static final Class<?> nbtTagCompoundClass;
    private static final List<String> colors = Arrays.asList(
            "WHITE",
            "ORANGE",
            "MAGENTA",
            "LIGHT_BLUE",
            "YELLOW",
            "LIME",
            "PINK",
            "GRAY",
            "LIGHT_GRAY",
            "CYAN",
            "PURPLE",
            "BLUE",
            "BROWN",
            "GREEN",
            "RED",
            "BLACK"
    );

    static {
        version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        major = Integer.parseInt(version.split("_")[1]);

        nbtTagCompoundClass = getNMSClass("NBTTagCompound");
        nmsItemStackClass = getNMSClass("ItemStack");
        craftItemStackClass = getCraftBukkitClass("inventory.CraftItemStack");
        materialClass = getBukkitClass("Material");
        itemStackClass = getBukkitClass("inventory.ItemStack");
        try {
            if (major >= 13) {
                glassPaneConstructor = itemStackClass.getConstructor(materialClass);
            } else {
                glassPaneConstructor = itemStackClass.getConstructor(materialClass, int.class, short.class);
            }
        } catch (NoSuchMethodException e) {
            glassPaneConstructor = null;
            e.printStackTrace();
        }
    }

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

    public static ItemStack craftColoredGlassPane(String colorTag) {
        try {
            Object object;
            // Check if currently running version is newer than 1.13
            if (major >= 13) {
                // Use new colored glass pane name (color as name appendix)
                object = glassPaneConstructor.newInstance(materialClass.getField(colorTag + "_STAINED_GLASS_PANE").get(null));
            } else {
                // Use old colored glass pane name and pass the color as parameter
                short colorId = (short) colors.indexOf(colorTag);
                if (colorId < 0) {
                    colorId = 0;
                }
                object = glassPaneConstructor.newInstance(materialClass.getField("STAINED_GLASS_PANE").get(null), 1, colorId);
            }
            return (ItemStack) object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ItemStack setNBT(ItemStack item, String key, int value) {
        try {
            // Get NMS copy of Bukkit ItemStack
            Object craftItem = getNMSItemStack(item);
            Object tag;
            // Check if NMS ItemStack has NBT
            if ((boolean) nmsItemStackClass.getMethod("hasTag").invoke(craftItem)) {
                // Get NMS ItemStack's NBT
                tag = nmsItemStackClass.getMethod("getTag").invoke(craftItem);
            } else {
                // Create new NBT for NMS ItemStack
                tag = nbtTagCompoundClass.getConstructor().newInstance();
            }
            // Set custom NBT value according to the value type
            nbtTagCompoundClass.getMethod("setInt", String.class, int.class).invoke(tag, key, value);
            // Update NBT data into NMS ItemStack
            nmsItemStackClass.getMethod("setTag", nbtTagCompoundClass).invoke(craftItem, tag);
            // Convert back NMS ItemStack to Bukkit ItemStack
            return (ItemStack) craftItemStackClass.getMethod("asBukkitCopy", craftItem.getClass()).invoke(null, craftItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Fallback return
        return null;
    }

    public static int getNBT(ItemStack item, String key) {
        try {
            // Get NMS copy of Bukkit ItemStack
            Object craftItem = getNMSItemStack(item);
            // Check if NMS ItemStack has NBT
            if ((boolean) nmsItemStackClass.getMethod("hasTag").invoke(craftItem)) {
                // Get NMS ItemStack's NBT
                Object tag = nmsItemStackClass.getMethod("getTag").invoke(craftItem);
                // Return NBT value in given key
                return (int) nbtTagCompoundClass.getMethod("getInt", String.class).invoke(tag, key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Fallback return
        return -1;
    }

    public static Object getNMSItemStack(ItemStack item) throws Exception {
        return craftItemStackClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
    }

    private static Class<?> getExternalClass(String path) {
        try {
            return Class.forName(path);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find class at path: " + path, e);
        }
    }

    public static Class<?> getNMSClass(String path) {
        return getExternalClass("net.minecraft.server." + version + "." + path);
    }

    public static Class<?> getCraftBukkitClass(String path) {
        return getBukkitClass("craftbukkit." + version + "." + path);
    }

    public static Class<?> getBukkitClass(String path) {
        return getExternalClass("org.bukkit." + path);
    }

    public static String getServerVersion() {
        return version;
    }

    public static void trigger() {}
}
