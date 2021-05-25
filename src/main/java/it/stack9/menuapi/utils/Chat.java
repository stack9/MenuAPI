package it.stack9.menuapi.utils;

import org.bukkit.ChatColor;

public class Chat {
    public static String tr(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
