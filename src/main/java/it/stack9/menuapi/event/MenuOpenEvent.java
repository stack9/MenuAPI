package it.stack9.menuapi.event;

import it.stack9.menuapi.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/*
 * MenuAPI
 * @author stack
 * @date 5/20/21
 */
public class MenuOpenEvent extends MenuEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public MenuOpenEvent(Player player, Menu menu) {
        super(player, menu);
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
