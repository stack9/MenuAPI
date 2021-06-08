package it.stack9.menuapi.menu;

import org.bukkit.entity.Player;

/*
 * Represents events which occurs in the menu
 *
 * @author stack
 * @date 5/22/21
 */
public interface MenuListener {

    /**
     * Called when a player opens the menu
     *
     * @param menu Opened menu
     * @param player Targeted player
     */
    void onOpen(Menu menu, Player player);

    /**
     * Called when the menu is updated (closed and reopened without the player noticing)
     *
     * @param menu Updated menu
     * @param player Targeted player
     */
    void onUpdate(Menu menu, Player player);

    /**
     * Called when a player clicks a slot in the menu
     *
     * @param menu Opened menu
     * @param player Targeted player
     * @param slot Selected slot
     */
    void onClick(Menu menu, Player player, int slot);

    /**
     * Called when a player clicks on a valid option in the menu
     *
     * @param menu Opened menu
     * @param player Targeted player
     * @param option Selected option
     */
    void onSelect(Menu menu, Player player, Option option);

    /**
     * Called when a player goes to the next page of the menu (if there are no pages left
     * this event is NOT called)
     *
     * @param menu Opened menu
     * @param player Targeted player
     * @param previousPage Number of the previous page
     * @param currentPage Number of the current page (also obtainable by `menu.getCurrentPage()`)
     */
    void onPageForward(Menu menu, Player player, int previousPage, int currentPage);

    /**
     * Called when a player goes to the previous page of the menu (if there are no pages left
     * this event is NOT called)
     *
     * @param menu Opened menu
     * @param previousPage Number of the previous page
     * @param currentPage Number of the current page (also obtainable by `menu.getCurrentPage()`)
     */
    void onPageBackward(Menu menu, Player player, int previousPage, int currentPage);

    /**
     * Called when a player closes the menu
     *
     * @param menu closed menu
     * @param player Targeted player
     */
    void onClose(Menu menu, Player player);
}
