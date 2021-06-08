package it.stack9.menuapi.menu;

import org.bukkit.entity.Player;

/*
 * Read only menu, if you don't need to listen for events in the menu
 *
 * @author stack
 * @date 5/22/21
 */
public class ReadOnlyMenuListener extends SimpleMenuListener {
    @Override
    public void onSelect(Menu menu, Player player, Option option) { }
}
