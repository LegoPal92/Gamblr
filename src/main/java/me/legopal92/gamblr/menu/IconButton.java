package me.legopal92.gamblr.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * This is a button which serves no purpose other than to look pretty
 */
public class IconButton extends Button {

    public IconButton(ItemStack item) {
        super(item);
    }

    @Override
    public void onClick(Player player) {

    }
}
