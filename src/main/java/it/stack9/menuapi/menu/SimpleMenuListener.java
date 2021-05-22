package it.stack9.menuapi.menu;

import org.bukkit.entity.Player;

/*
 * MenuAPI
 * @author stack
 * @date 5/22/21
 */
public abstract class SimpleMenuListener implements MenuListener {
    @Override
    public void onOpen(Menu menu, Player player) { }

    @Override
    public void onClose(Menu menu, Player player) { }
}
