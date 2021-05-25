package it.stack9.menuapi.menu;

import org.bukkit.entity.Player;

/*
 * MenuAPI
 * @author stack
 * @date 5/22/21
 */
public interface MenuListener {
    void onOpen(Menu menu, Player player);
    void onClick(Menu menu, Player player, int slot);
    void onSelect(Menu menu, Player player, Option option);
    void onClose(Menu menu, Player player);
}
