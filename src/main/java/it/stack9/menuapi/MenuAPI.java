package it.stack9.menuapi;

import it.stack9.menuapi.menu.Menu;
import it.stack9.menuapi.menu.MenuFactory;
import it.stack9.menuapi.event.MenuItemSelectedEvent;
import it.stack9.menuapi.menu.Option;
import it.stack9.menuapi.utils.Item;
import it.stack9.menuapi.utils.MetaManipulator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * MenuAPI
 * @author stack
 * @date 5/20/21
 */
public final class MenuAPI extends JavaPlugin implements Listener {

    public MenuAPI() {
        MetaManipulator.setPlugin(this);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("test").setExecutor(this);
        // Trigger execution of static block to initialize reflection
        Item.trigger();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            getLogger().warning("This command can only be executed by players");
            return false;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("test")) {
//            Menu menu = MenuFactory.createSimple(
//                    "Test",
//                    Arrays.asList(
//                            new Option(0, new ItemStack(Material.GRASS), "1"),
//                            new Option(1, new ItemStack(Material.GRASS), "2"),
//                            new Option(2, new ItemStack(Material.GRASS), "3"),
//                            new Option(3, new ItemStack(Material.GRASS), "4"),
//                            new Option(4, new ItemStack(Material.GRASS), "5"),
//                            new Option(5, new ItemStack(Material.GRASS), "6"),
//                            new Option(6, new ItemStack(Material.GRASS), "7"),
//                            new Option(7, new ItemStack(Material.GRASS), "8"),
//                            new Option(8, new ItemStack(Material.GRASS), "9"),
//                            new Option(9, new ItemStack(Material.GRASS), "10"),
//                            new Option(10, new ItemStack(Material.GRASS), "11"),
//                            new Option(11, new ItemStack(Material.GRASS), "12"),
//                            new Option(12, new ItemStack(Material.GRASS), "13"),
//                            new Option(13, new ItemStack(Material.GRASS), "14"),
//                            new Option(14, new ItemStack(Material.GRASS), "15"),
//                            new Option(15, new ItemStack(Material.GRASS), "16"),
//                            new Option(16, new ItemStack(Material.GRASS), "17"),
//                            new Option(17, new ItemStack(Material.GRASS), "18"),
//                            new Option(18, new ItemStack(Material.GRASS), "19"),
//                            new Option(19, new ItemStack(Material.GRASS), "20"),
//                            new Option(20, new ItemStack(Material.GRASS), "21"),
//                            new Option(21, new ItemStack(Material.GRASS), "22"),
//                            new Option(22, new ItemStack(Material.GRASS), "23"),
//                            new Option(23, new ItemStack(Material.GRASS), "24"),
//                            new Option(24, new ItemStack(Material.GRASS), "25"),
//                            new Option(25, new ItemStack(Material.GRASS), "26"),
//                            new Option(26, new ItemStack(Material.GRASS), "27"),
//                            new Option(27, new ItemStack(Material.GRASS), "28"),
//                            new Option(28, new ItemStack(Material.GRASS), "29"),
//                            new Option(29, new ItemStack(Material.GRASS), "30"),
//                            new Option(30, new ItemStack(Material.GRASS), "31"),
//                            new Option(31, new ItemStack(Material.GRASS), "32"),
//                            new Option(32, new ItemStack(Material.GRASS), "33"),
//                            new Option(33, new ItemStack(Material.GRASS), "34"),
//                            new Option(34, new ItemStack(Material.GRASS), "35"),
//                            new Option(35, new ItemStack(Material.GRASS), "36"),
//                            new Option(36, new ItemStack(Material.GRASS), "37"),
//                            new Option(37, new ItemStack(Material.GRASS), "38"),
//                            new Option(38, new ItemStack(Material.GRASS), "38"),
//                            new Option(39, new ItemStack(Material.GRASS), "38"),
//                            new Option(40, new ItemStack(Material.GRASS), "38"),
//                            new Option(41, new ItemStack(Material.GRASS), "38"),
//                            new Option(42, new ItemStack(Material.GRASS), "38"),
//                            new Option(43, new ItemStack(Material.GRASS), "38"),
//                            new Option(44, new ItemStack(Material.GRASS), "38"),
//                            new Option(45, new ItemStack(Material.GRASS), "38"),
//                            new Option(46, new ItemStack(Material.GRASS), "38"),
//                            new Option(47, new ItemStack(Material.GRASS), "38"),
//                            new Option(48, new ItemStack(Material.GRASS), "38"),
//                            new Option(49, new ItemStack(Material.GRASS), "38"),
//                            new Option(50, new ItemStack(Material.GRASS), "38"),
//                            new Option(51, new ItemStack(Material.GRASS), "38"),
//                            new Option(52, new ItemStack(Material.GRASS), "38"),
//                            new Option(53, new ItemStack(Material.GRASS), "38"),
//                            new Option(54, new ItemStack(Material.GRASS), "38"),
//                            new Option(55, new ItemStack(Material.GRASS), "38")
//                    )
//            );
//            menu.open(player);
            Menu menu = MenuFactory.create("&6Test menu", Menu.CHEST_SIZE);
            menu.setOption(5, 0, new ItemStack(Material.DIRT));
            menu.setOption(12, 1, new ItemStack(Material.DIRT));
            menu.setOption(7, 2, new ItemStack(Material.DIRT));
            menu.open(player);
            return true;
        }

        return false;
    }

    @Override
    public void onDisable() {
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
                switch (Item.getNBT(item, "mat")) {

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
                        int selId = Item.getNBT(item, "maid");
                        if (selId >= 0) {
                            Option selected = menu.getOption(selId);
                            // Call selected item event
                            if (selected != null) {
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
}
