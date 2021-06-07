package it.stack9.menuapi.nms.v1_14_R1;

import it.stack9.menuapi.api.Item;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;

public class ItemMaster extends Item {

    @Override
    public ItemStack craftColoredGlassPane(String colorTag) {
        // Use new colored glass pane name (color as name appendix)
        return new ItemStack(Material.valueOf(colorTag + "_STAINED_GLASS_PANE"), 1);
    }

    @Override
    public ItemStack setNBT(ItemStack item, String key, int value) {
        // Get NMS copy of Bukkit ItemStack
        net.minecraft.server.v1_14_R1.ItemStack craftItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag;
        // Check if NMS ItemStack has NBT
        if (craftItem.hasTag()) {
            // Get NMS ItemStack's NBT
            tag = craftItem.getTag();
        } else {
            // Create new NBT for NMS ItemStack
            tag = new NBTTagCompound();
        }
        // Set custom NBT value according to the value type
        tag.setInt(key, value);
        // Update NBT data into NMS ItemStack
        craftItem.setTag(tag);
        // Convert back NMS ItemStack to Bukkit ItemStack
        return CraftItemStack.asBukkitCopy(craftItem);
    }

    @Override
    public int getNBT(ItemStack item, String key) {
        // Get NMS copy of Bukkit ItemStack
        net.minecraft.server.v1_14_R1.ItemStack craftItem = CraftItemStack.asNMSCopy(item);
        // Check if NMS ItemStack has NBT
        if (craftItem.hasTag()) {
            // Get NMS ItemStack's NBT
            NBTTagCompound tag = craftItem.getTag();
            // Return NBT value in given key
            return tag.getInt(key);
        }
        // Fallback return
        return -1;
    }
}
