package it.stack9.menuapi.menu;

import it.stack9.menuapi.utils.ItemUtils;
import org.bukkit.inventory.ItemStack;

public class Option {

    public static final byte CLOSE_MENU_DATA = 0x01;
    public static final byte NEXT_PAGE_DATA = 0x02;
    public static final byte PREVIOUS_PAGE_DATA = 0x03;

    private final int id;
    private ItemStack icon;
    private final byte data;

    public Option(int id, ItemStack icon, byte data) {
        this.id = id;
        this.icon = icon;
        this.data = data;
    }

    public Option(int id, ItemStack icon) {
        this(id, icon, (byte) 0);
    }

    public Option(int id, ItemStack item, String name, String... lore) {
        this(id, ItemUtils.craft(item, name, lore));
    }

    public int getId() {
        return id;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public byte getData() {
        return data;
    }
}
