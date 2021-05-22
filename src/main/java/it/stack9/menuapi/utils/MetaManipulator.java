package it.stack9.menuapi.utils;

import it.stack9.menuapi.menu.Menu;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

/*
 * MenuAPI
 * @author stack
 * @date 5/20/21
 */
public class MetaManipulator {

    public static final String MENU_KEY = "menu";

    private static Plugin plugin;

    public static void setPlugin(final Plugin plugin) {
        MetaManipulator.plugin = plugin;
    }

    public static void set(Metadatable object, String key, Object value) {
        object.setMetadata(key, new FixedMetadataValue(plugin, value));
    }

    public static Object get(Metadatable object, String key) {
        for (MetadataValue value : object.getMetadata(key)) {
            if (value.getOwningPlugin() == plugin) {
                return value.value();
            }
        }
        return null;
    }

    public static Menu getMenu(Metadatable object) {
        return (Menu) get(object, MENU_KEY);
    }
}
