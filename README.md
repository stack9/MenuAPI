# MenuAPI

<div style="text-align:center">
<img src="./screenshot.png"/>
</div>

<div style="text-align:center">
<img src="https://img.shields.io/badge/version-1.0.0-blue?style=for-the-badge">
<img src="https://img.shields.io/badge/Minecraft-1.9%20--%201.16.5-blue?style=for-the-badge">
</div>

<div style="text-align:center">
<b>This plugin is for developers only, if you're a server owner then this isn't intended for you.</b>
</div>

MenuAPI facilitates you the creation of menu through inventories. The process is really simple and the API contains some default classes to minimize the code you need to write.

__The wiki isn't ready yet, so if you need to use a specific functionality check out the source code on GitHub.__

### Usage
```java
package me.example.project;

import it.stack9.menuapi.menu.*;

public class Example {
    public static void main(String[] args) {
        Player player = Bukkit.getServer().getPlayer("009fdee8-895d-42d8-ba8e-415b51578fd0");
        
        Menu menu = MenuFactory.create("Test menu", Menu.CHEST_SIZE, new SimpleMenuListener() {
            @Override
            public void onClick(Menu menu, Player player, @Nullable Option selection) {
                player.sendMessage("You selected the option #" + selection.getId());
            }
        });
        menu.open(player);
    }
}
```
