package me.legopal92.Gamblr.GUI;

import me.legopal92.Gamblr.Gamblr;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by legop on 4/8/2016.
 */
public class GUI {
    /**
     * Global list of all guis.
     */
    public static ArrayList<GUI> guis = new ArrayList<>();

    /**
     * Local variables.
     * openUsers - Users with open GUI's.
     * gui - The Bukkit Inventory object associated with this gui.
     * name - The name of the inventory.
     * slots - The amount of slots in the inventory.
     * items - The items in the inventory.
     */
    private List<UUID> openUsers = new ArrayList<>();

    private Inventory gui = null;
    private String name;
    private int slots;
    private GUIItem[] items;

    /**
     * Creates a GUI object.
     * @param name - The name of the GUI.
     * @param slots - The amount of slots in the inventory.
     * @param guiItem - The items in the inventory.
     */
    public GUI(String name, int slots, GUIItem[] guiItem){
        this.gui =  Gamblr.getInstance().getServer().createInventory(null,(int) slots, name);
        this.name = name;
        this.slots = slots;
        this.items = guiItem;
        populate();
        this.guis.add(this);
    }

    /**
     * Obvious method being obvious.
     * @return - The name of the gui inventory.
     */
    public String getName(){ return name; }

    /**
     * Opens the gui for the player. Adds them to the list of all players who have this GUI open.
     * @param player - The player to open the inventory for.
     */
    public void openInventory(Player player){
        player.openInventory(gui);
        openUsers.add(player.getUniqueId());
    }

    /**
     * Removes the player from the list of players with this gui open.
     * @param player - the player to remove from the list.
     */
    public void closeInventory(Player player){
        openUsers.remove(player.getUniqueId());
    }

    /**
     * Seriously. This is one of the most obvious methods I have.
     * @return - The amount of slots in the inventory.
     */
    public int getSlots(){ return slots; }

    /**
     * Populates the inventory with the @link{GUIItem}s on craetion.
     */
    private void populate(){
        for (GUIItem gi : items){
            gui.setItem(gi.getSlot(), gi.getBukkitItem());
        }
    }

    /**
     * Get the array of items.
     * @return - The array of items.
     */
    public GUIItem[] getItems(){ return items; }

    /**
     * Get a GUIItem at a certain inventory slot.
     * @param slot - The slot the item is located.
     * @return - The Item, unless the user clicked a blank slot. In which case, null.
     */
    public GUIItem getItemAtSlot(int slot){
        for (GUIItem g : getItems()){
            if (g.getSlot() == slot){
                return g;
            }
        }
        return null;
    }

    /**
     * Get a GUI from its name.
     * @param name - The name of the GUI.
     * @return - The GUI that is named. Null if there isn't one.
     */
    public static GUI getByName(String name){
        for (GUI gui : guis){
            if (gui.getName().equalsIgnoreCase(name)){
                return gui;
            }
        }
        return null;
    }
}
