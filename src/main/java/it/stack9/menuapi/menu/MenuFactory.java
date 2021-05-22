package it.stack9.menuapi.menu;

import java.util.List;

/*
 * MenuAPI
 * @author stack
 * @date 5/20/21
 */
public class MenuFactory {

    public static Menu create(String title, int size) {
        return new Menu(title, size, false);
    }

    public static Menu createSimple(String title, List<Option> items) {
        Menu menu = new Menu(title, items.size(), false);
        for (int i = 0; i < items.size(); i++) {
            menu.setOption(i, items.get(i));
        }
        return menu;
    }

    public static Menu createBordered(String title, List<Option> items) {
        Menu menu = new Menu(title, items.size(), true);
        int i = 0;
        for (; i < items.size(); i++) {
            menu.setOption(i, items.get(i));
        }
        return menu;
    }
}
