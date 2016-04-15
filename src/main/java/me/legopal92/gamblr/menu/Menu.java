package me.legopal92.gamblr.menu;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A class to manage dynamic creation of GUI's
 */
public abstract class Menu {

    protected static final Button[] EMPTY = new Button[45];
    protected static Map<UUID, Menu> MENUS = new HashMap<>();
    private String name;
    protected Button[] buttons;

    public Menu(String name) {
        this.name = name;
        this.buttons = EMPTY;
    }

    public static Menu get(UUID name) {
        return MENUS.get(name);
    }


    /**
     * Create an icon (no click action)
     *
     * @param item The itemstack ti display
     * @return The created button
     */
    protected Button create(ItemStack item) {
        return new IconButton(item);
    }

    public String getName() {
        return ChatColor.translateAlternateColorCodes('&', name);
    }

    /**
     * Open and setup the inventory for the player to view
     * Store a reference to it inside a map for retrieving later
     *
     * @param player The player who we wish to show the GUI to
     */
    public void open(Player player) {
        setButtons(setUp());

        if (MENUS.get(player.getUniqueId()) != null) {
            MENUS.remove(player.getUniqueId());
        }

        MENUS.put(player.getUniqueId(), this);

        int size = (buttons.length + 8) / 9 * 9;
        Inventory inventory = Bukkit.createInventory(player, size, getName());

        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == null) {
                continue;
            }

            ItemStack item = buttons[i].getItemStack();

            inventory.setItem(i, item);
        }
        player.openInventory(inventory);
    }

    /**
     * Set up the GUI with buttons
     *
     * @return The setup button array
     */
    protected abstract Button[] setUp();

    public Button[] getButtons() {
        return buttons;
    }

    public void setButtons(Button[] buttons) {
        this.buttons = buttons;
    }

    /**
     * Retrieve the button based off the slot
     *
     * @param slot The slot in the inventory
     * @return The button corresponding to that slot
     */
    public Button getButton(int slot) {
        try {
            return buttons[slot];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Replace a button, or create a new button dynamically
     * Update the players GUI
     *
     * @param slot   The slot to set the new button
     * @param button The reference to the button
     * @param player The player whose GUI we'll be updating
     */
    public void setButton(int slot, Button button, Player player) {
        try {
            buttons[slot] = button;
        } catch (ArrayIndexOutOfBoundsException ignored) {
            ignored.printStackTrace();
        }
        update(player);
    }

    /**
     * Refresh the players view, allows to change what the player sees, without opening and closing the GUI
     *
     * @param player The player whose view you wish to update
     */
    public void update(Player player) {
        InventoryView view = player.getOpenInventory();

        if (view == null) {
            return;
        }

        if (!view.getTitle().equalsIgnoreCase(getName())) {
            return;
        }

        Inventory inventory = view.getTopInventory();
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == null) {
                continue;
            }

            ItemStack item = buttons[i].getItemStack();

            inventory.setItem(i, item);
        }
    }

    public void setTitle(String title) {
        name = title;
    }

    public void onClose(Player player) {
        MENUS.remove(player.getUniqueId());
    }

    public static Menu remove(UUID uniqueId) {
        return MENUS.remove(uniqueId);
    }
}
