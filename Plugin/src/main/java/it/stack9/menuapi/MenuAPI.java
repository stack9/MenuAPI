package it.stack9.menuapi;

import it.stack9.menuapi.api.Item;
import it.stack9.menuapi.menu.*;
import it.stack9.menuapi.event.MenuItemSelectedEvent;
import it.stack9.menuapi.utils.MetaManipulator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * MenuAPI
 * @author stack
 * @date 5/20/21
 */
public final class MenuAPI extends JavaPlugin implements Listener {

    public static Item ITEM;

    static {
        final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            final Class<?> clazz = Class.forName("it.stack9.menuapi.nms." + version + ".ItemMaster");
            // Check if we have a NMSHandler class at that location.
            if (Item.class.isAssignableFrom(clazz)) { // Make sure it actually implements NMS
                ITEM = (Item) clazz.getConstructor().newInstance(); // Set our handler
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public MenuAPI() {
        MetaManipulator.setPlugin(this);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("test").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            getLogger().warning("This command can only be executed by players");
            return false;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("test")) {
            Menu menu = MenuFactory.create("&8Test menu", Menu.DOUBLE_CHEST_SIZE, new SimpleMenuListener() {
                @Override
                public void onSelect(Menu menu, Player player, Option selection) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&6Github: &7https://github.com/stack9/MenuAPI"));
                }
            });
            menu.setOption(
                    22, 1,
                    new ItemStack(Material.EMERALD_BLOCK),
                    getDescription().getName() + " " + getDescription().getVersion(),
                    "",
                    "&r&6Name: &7" + getDescription().getName(),
                    "&r&6Version: &7" + getDescription().getVersion(),
                    "&r&6Author: &7" + getDescription().getAuthors().get(0),
                    ""
            );
            menu.setOption(49, -1, Menu.CLOSE_ICON);
            menu.open(player);
            return true;
        }

        return false;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {
        Menu menu = MetaManipulator.getMenu(event.getWhoClicked());
        if (menu != null) {
            Player player = (Player) event.getWhoClicked();
            // Prevent user from taking the item
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            // Deprecated but without it its possible to pick the item spam-clicking for whatever reason
            player.updateInventory();

            ItemStack item = event.getCurrentItem();
            if (item != null) {
                // Check if player has clicked on special button
                switch (ITEM.getNBT(item, "mat")) {

                    case Option.CLOSE_MENU_DATA:
                        menu.close(player);
                        break;

                    case Option.NEXT_PAGE_DATA:
                        menu.nextPage(player);
                        break;

                    case Option.PREVIOUS_PAGE_DATA:
                        menu.previousPage(player);
                        break;

                    default:
                        int selId = ITEM.getNBT(item, "maid");
                        if (selId >= 0) {
                            Option selected = menu.getOption(selId);
                            // Dispatch selection event
                            menu.getListener().onClick(menu, player, event.getSlot());
                            if (selected != null) {
                                menu.getListener().onSelect(menu, player, selected);
                                Bukkit.getPluginManager().callEvent(new MenuItemSelectedEvent(player, menu, selected));
                            }
                        }
                        break;
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Menu menu = MetaManipulator.getMenu(event.getPlayer());
        if (menu != null) {
            menu.close((Player) event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Clear player's metadata if the player quits with the menu still open (eg. crashes)
        if (MetaManipulator.getMenu(event.getPlayer()) != null) {
            MetaManipulator.set(event.getPlayer(), MetaManipulator.MENU_KEY, null);
        }
    }
}
