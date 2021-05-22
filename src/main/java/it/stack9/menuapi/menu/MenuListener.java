package it.stack9.menuapi.menu;

import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/*
 * MenuAPI
 * @author stack
 * @date 5/22/21
 */
public interface MenuListener {
    void onOpen(Menu menu, Player player);
    void onClick(Menu menu, Player player, @Nullable Option selection);
    void onClose(Menu menu, Player player);
}
