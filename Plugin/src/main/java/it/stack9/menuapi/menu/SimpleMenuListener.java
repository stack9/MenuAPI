package it.stack9.menuapi.menu;

import org.bukkit.entity.Player;

/*
 * Class to simplify the creation of a menu by defining all methods events but onSelection
 *
 * @author stack
 * @date 5/22/21
 */
public abstract class SimpleMenuListener implements MenuListener {
    @Override
    public void onOpen(Menu menu, Player player) { }

    @Override
    public void onUpdate(Menu menu, Player player) { }

    @Override
    public void onPageForward(Menu menu, Player player, int previousPage, int currentPage) { }

    @Override
    public void onPageBackward(Menu menu, Player player, int previousPage, int currentPage) { }

    @Override
    public void onClick(Menu menu, Player player, int slot) { }

    @Override
    public void onClose(Menu menu, Player player) { }
}
