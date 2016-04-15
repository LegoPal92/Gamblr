package me.legopal92.gamblr.listeners;

import me.legopal92.gamblr.bet.GambleBet;
import me.legopal92.gamblr.economy.Bank;
import me.legopal92.gamblr.gui.GUI;
import me.legopal92.gamblr.gui.GUIItem;
import me.legopal92.gamblr.npc.NPCDealer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by legop on 4/12/2016.
 */
public class PlayerListener implements Listener{

    private static HashMap<UUID, GUI> playerGUIs = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if (!Bank.isInBank(event.getPlayer().getUniqueId())){
            Bank.join(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){//THE EVENT WHEN THE PLAYER INTERACTS AT A VILLAGER.
        NPCDealer npcd = null;
        if (event.getRightClicked().getType() == EntityType.VILLAGER){//MAKE SURE IT IS A VILLAGER.
            for (NPCDealer dealer : NPCDealer.getDealers()){
                if (event.getRightClicked().getCustomName().equalsIgnoreCase(dealer.getCustomName())){//CHECK IF IT IS A DEALER.
                    npcd = dealer;
                    break;
                }
            }
        }
        if (npcd == null){//DEALER IS NULL
            event.getPlayer().sendMessage("Dealer is null.");
            return;
        }
        GUI g = npcd.getGui();
        g.openInventory(event.getPlayer());//OPEN THE ASSOCIATED GUI
        event.setCancelled(true);
        playerGUIs.put(event.getPlayer().getUniqueId(), g);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){//CLOSE THE INVENTORY.
        if (playerGUIs.containsKey(event.getPlayer().getUniqueId())){
            GUI g = playerGUIs.get(event.getPlayer().getUniqueId());
            g.closeInventory((Player)event.getPlayer());
            playerGUIs.remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onGUIclick(InventoryClickEvent event){//PLACE BET IF INVENTORY IS A GUI
        if (!playerGUIs.containsKey(((Player)event.getWhoClicked()).getUniqueId())){
            return;
        }
        GUI g = playerGUIs.get(((Player)event.getWhoClicked()).getUniqueId());
        GUIItem item = g.getItemAtSlot(event.getSlot());
        if (item == null){//Clicked an empty slot.
            return;
        }
        GambleBet gb = (GambleBet)item.initiateBet(((Player)event.getWhoClicked()).getUniqueId());
        gb.place();
        Player player = (Player) event.getWhoClicked();
        player.closeInventory();
        event.setCancelled(true); //CANCEL THE ITEM GRAB
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        if (event.getEntityType() != EntityType.VILLAGER){
            return;
        }
        if (event.getEntity().hasMetadata("nodamage")){
            event.setCancelled(true);
            return;
        }
    }



}
