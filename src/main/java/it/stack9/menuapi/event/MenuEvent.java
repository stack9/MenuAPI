package it.stack9.menuapi.event;

import it.stack9.menuapi.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/*
 * MenuAPI
 * @author stack
 * @date 5/20/21
 */
public class MenuEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final Menu menu;

    public MenuEvent(Player player, Menu menu) {
        this.player = player;
        this.menu = menu;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    public Menu getMenu() {
        return menu;
    }
}
