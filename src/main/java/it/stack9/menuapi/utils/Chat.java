package it.stack9.menuapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Logger;

public class Chat {
    public static String tr(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static Logger getLogger() {
        return Bukkit.getServer().getPluginManager().getPlugin("MenuAPI").getLogger();
    }
}
