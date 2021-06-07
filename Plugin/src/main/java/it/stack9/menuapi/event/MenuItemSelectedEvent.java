package it.stack9.menuapi.event;

import it.stack9.menuapi.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import it.stack9.menuapi.menu.Option;

/*
 * MenuAPI
 * @author stack
 * @date 5/20/21
 */
public class MenuItemSelectedEvent extends MenuEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Option selected;

    public MenuItemSelectedEvent(Player player, Menu menu, Option selected) {
        super(player, menu);
        this.selected = selected;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Option getSelection() {
        return selected;
    }
}
