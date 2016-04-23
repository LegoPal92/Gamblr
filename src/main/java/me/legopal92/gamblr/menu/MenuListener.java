package me.legopal92.gamblr.menu;

import me.legopal92.gamblr.listeners.ItemConversationListener;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * @author Timothy Andis (TadahTech) on 4/3/2016.
 */
public class MenuListener implements Listener
{

    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
        String name = event.getInventory().getName();
        Player player = (Player) event.getWhoClicked();
        Menu gui = Menu.get(player.getUniqueId());

        if (gui == null)
        {
            return;
        }

        if (!gui.getName().equalsIgnoreCase(name))
        {
            return;
        }
        if (!gui.isSet()){
            return;
        }

        Button button = gui.getButton(event.getRawSlot());

        event.setCancelled(true);
        event.setResult(Event.Result.DENY);

        if (button == null)
        {
            return;
        }

        button.onClick(player);
        gui.onClose(player);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        String name = event.getInventory().getName();
        Player player = (Player) event.getPlayer();
        Menu gui = Menu.get(player.getUniqueId());

        if (gui == null)
        {
            return;
        }

        if (!gui.getName().equalsIgnoreCase(name))
        {
            return;
        }

        if (gui.isSet()){
            return;
        }

        Inventory i = event.getInventory();
        Button[] buttons = new Button[i.getSize()];
        ArrayList<GambleStack> gss = ItemConversationListener.items.get(player.getUniqueId());

        for (int i2 = 0; i2 < i.getSize(); i2++){
            if (i.getItem(i2) == null){
                continue;
            }
            ItemStack is = i.getItem(i2);
            for (GambleStack gs : gss){

                if (gs.itemStackEquals(is)){
                    GambleButton gb = new GambleButton(is, gs.getCost(), gs.getChance(), gs.getReward(), gs.getRadius(), Effect.valueOf(gs.getEffect()));
                    gb.setSlot(i2);
                    buttons[i2] = gb;
                    continue;
                }
            }
        }

        gui.setButtons(buttons);

        gui.set(true);
        gui.onClose(player);
    }

}
