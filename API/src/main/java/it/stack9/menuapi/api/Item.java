package it.stack9.menuapi.api;

import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public abstract class Item {

    protected static final List<String> colors = Arrays.asList(
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

    public abstract ItemStack craftColoredGlassPane(String colorTag);
    public abstract ItemStack setNBT(ItemStack item, String key, int value);
    public abstract int getNBT(ItemStack item, String key);
}
