package it.stack9.menuapi.menu;

import java.util.List;

/*
 * Class used to create menus. Use this if you need to create simple menus instead of the Menu's constructor
 *
 * @author stack
 * @date 5/20/21
 */
public class MenuFactory {

    public static Menu create(String title, int size, MenuListener listener) {
        return new Menu(title, size, false, listener);
    }

    public static Menu create(String title, List<Option> items, MenuListener listener) {
        Menu menu = new Menu(title, items.size(), false, listener);
        for (int i = 0; i < items.size(); i++) {
            menu.setOption(i, items.get(i));
        }
        return menu;
    }

    public static Menu create(String title, MenuListener listener, List<? extends IMenuOption> items) {
        Menu menu = new Menu(title, items.size(), false, listener);
        for (int i = 0; i < items.size(); i++) {
            menu.setOption(i, items.get(i));
        }
        return menu;
    }

    public static Menu createBordered(String title, int size, MenuListener listener) {
        return new Menu(title, size, true, listener);
    }

    public static Menu createBordered(String title, List<Option> items, MenuListener listener) {
        Menu menu = new Menu(title, items.size(), true, listener);
        for (int i = 0; i < items.size(); i++) {
            menu.setOption(i, items.get(i));
        }
        return menu;
    }

    public static Menu createBordered(String title, MenuListener listener, List<? extends IMenuOption> items) {
        Menu menu = new Menu(title, items.size(), true, listener);
        for (int i = 0; i < items.size(); i++) {
            menu.setOption(i, items.get(i));
        }
        return menu;
    }
}
