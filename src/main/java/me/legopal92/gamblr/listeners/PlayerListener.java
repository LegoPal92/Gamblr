package me.legopal92.gamblr.listeners;

import me.legopal92.gamblr.economy.Bank;
import me.legopal92.gamblr.menu.MainMenu;
import me.legopal92.gamblr.npc.NPCDealer;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by legop on 4/12/2016.
 */
public class PlayerListener implements Listener{

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
            return;
        }
        MainMenu g = npcd.getGui();
        g.open(event.getPlayer());//OPEN THE ASSOCIATED GUI
        event.setCancelled(true);
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
