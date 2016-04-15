package me.legopal92.gamblr.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * An abstract class for managing buttons inside of menus
 */
public abstract class Button
{

    private ItemStack _item;

    public Button(ItemStack item)
    {
        this._item = item;
    }

    /**
     * The method called when a players clicks the slot
     *
     * @param player The player who clicked
     */
    public abstract void onClick(Player player);

    public ItemStack getItemStack()
    {
        return _item;
    }

    public void setItemStack(ItemStack item)
    {
        this._item = item;
    }

}
