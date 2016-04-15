package me.legopal92.gamblr.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

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

        Button button = gui.getButton(event.getRawSlot());

        event.setCancelled(true);
        event.setResult(Event.Result.DENY);

        if (button == null)
        {
            return;
        }

        button.onClick(player);
    }

}
