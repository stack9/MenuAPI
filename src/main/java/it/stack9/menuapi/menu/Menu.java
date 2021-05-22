package it.stack9.menuapi.menu;

import it.stack9.menuapi.event.MenuCloseEvent;
import it.stack9.menuapi.event.MenuOpenEvent;
import it.stack9.menuapi.utils.Chat;
import it.stack9.menuapi.utils.Item;
import it.stack9.menuapi.utils.MetaManipulator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/*
 * MenuAPI
 * @author stack
 * @date 5/20/21
 */
public class Menu implements IMenu {

    private static final ItemStack BORDER_ITEM = Item.craftColoredGlassPane("GRAY");
    public static final ItemStack CLOSE_ICON = Item.setNBT(Item.craft(Item.craftColoredGlassPane("RED"), "&cExit"), "mat", Option.CLOSE_MENU_DATA);
    public static final ItemStack NEXT_PAGE_ICON = Item.setNBT(Item.craft(Material.ARROW, "&7Next page"), "mat", Option.NEXT_PAGE_DATA);
    public static final ItemStack PREVIOUS_PAGE_ICON = Item.setNBT(Item.craft(Material.ARROW, "&7Previous page"), "mat", Option.PREVIOUS_PAGE_DATA);

    public static final int CHEST_SIZE = InventoryType.CHEST.getDefaultSize();
    public static final int DOUBLE_CHEST_SIZE = InventoryType.CHEST.getDefaultSize() * 2;

    private final UUID id;
    private String title;
    private final int size;
    private final boolean bordered;
    private final Option[] options;
    private final MenuListener listener;
    private int page;
    private boolean open;

    Menu(String title, int size, boolean bordered, MenuListener listener) {
        this.id = UUID.randomUUID();
        this.title = title;
        if (!bordered && size > DOUBLE_CHEST_SIZE) {
            Chat.getLogger().severe("=======================================================");
            Chat.getLogger().severe("  Error: You are creating a menu with too much items");
            Chat.getLogger().severe("         for a double chest and without the borders");
            Chat.getLogger().severe("         (no control to switch between pages))");
            Chat.getLogger().severe("=======================================================");
            this.size = DOUBLE_CHEST_SIZE;
        } else {
            this.size = size;
        }
        this.bordered = bordered;
        this.options = new Option[this.size];
        this.listener = listener;
        this.page = 0;
        this.open = false;
    }

    public Menu setOption(int slot, Option option) {
        if (slot < size) {
            option.setIcon(Item.setNBT(option.getIcon(), "maid", option.getId()));
            options[slot] = option;
        }
        return this;
    }

    public Menu setOption(int slot, int id, ItemStack icon) {
        return setOption(slot, new Option(id, icon));
    }

    public Menu setOption(int slot, int id, ItemStack item, String name, String... lore) {
        return setOption(slot, id, Item.craft(item, name, lore));
    }

    public void nextPage(Player player) {
        if (bordered && page < Math.floor((float) size / 28)) {
            page++;
            update(player);
        }
    }

    public void previousPage(Player player) {
        if (bordered && page > 0) {
            page--;
            update(player);
        }
    }

    @Override
    public void update(Player player) {
        if (open) {
            close(player);
        }
        open(player);
    }

    @Override
    public void open(Player player) {
        int invSize = CHEST_SIZE;
        // If number of options is > than 27 (chest inventory) set inventory as double chest
        if (size > invSize) {
            invSize *= 2;
        }
        // Create inventory
        Inventory inventory = Bukkit.createInventory(player, invSize, Chat.tr(title));
        // Fill inventory
        fill(inventory);

        // Save inventory object into player's metadata
        MetaManipulator.set(player, MetaManipulator.MENU_KEY, this);
        this.open = true;
        // Open inventory to player
        player.openInventory(inventory);

        // Dispatch open event
        Bukkit.getPluginManager().callEvent(new MenuOpenEvent(player, this));
        listener.onOpen(this, player);
    }

    private void fill(Inventory inventory) {
        // If items needs to be placed on more than 1 page adds space for page controls
        int actualSize = options.length;
        if (bordered) {
            int size = inventory.getSize();
            int i = 0;
            // Fill border top
            for (; i < 9; i++) {
                inventory.setItem(i, BORDER_ITEM);
            }
            // Fill side borders and items
            for (int j = page * 28; i < size - 9; i++) {
                if (i % 9 == 0 || (i % 9) - 8 == 0) {
                    inventory.setItem(i, BORDER_ITEM);
                } else {
                    if (j < actualSize) {
                        inventory.setItem(i, options[j].getIcon());
                        j++;
                    }
                }
            }
            // Fill border bottom
            for (i = size - 9; i < size; i++) {
                // Set buttons and placeholder items
                if (i == (size - 5)) {
                    inventory.setItem(i, CLOSE_ICON);
                } else if (i == (size - 4) && actualSize > 28) {
                    inventory.setItem(i, NEXT_PAGE_ICON);
                } else if (i == (size - 6) && actualSize > 28) {
                    inventory.setItem(i, PREVIOUS_PAGE_ICON);
                } else {
                    inventory.setItem(i, BORDER_ITEM);
                }
            }
        } else {
            for (int i = page * 28; i < actualSize && i < size; i++) {
                if (options[i] != null) {
                    inventory.setItem(i, options[i].getIcon());
                }
            }
        }
    }

    @Override
    public void close(Player player) {
        MetaManipulator.set(player, MetaManipulator.MENU_KEY, null);
        player.closeInventory();
        this.open = false;

        // Dispatch close event
        Bukkit.getPluginManager().callEvent(new MenuCloseEvent(player, this));
        listener.onClose(this, player);
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCurrentPage() {
        return page;
    }

    public Option getOption(int optionId) {
        for (Option option : options) {
            if (option != null && option.getId() == optionId) {
                return option;
            }
        }
        return null;
    }

    public int getSize() {
        return size;
    }

    public boolean isBordered() {
        return bordered;
    }

    public boolean isOpen() {
        return open;
    }

    public MenuListener getListener() {
        return listener;
    }
}
