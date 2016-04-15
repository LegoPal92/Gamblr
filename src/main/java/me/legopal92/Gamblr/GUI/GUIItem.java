package me.legopal92.Gamblr.GUI;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.legopal92.Gamblr.Bet.Bet;
import me.legopal92.Gamblr.Bet.GambleBet;
import me.legopal92.Gamblr.Gamblr;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by legop on 4/8/2016.
 */
public class GUIItem {
    /**
     * Local variables.
     * bets - The list of bets for this item.
     * itemStack - The Bukkit ItemStack.
     * slot - The inventory slot used.
     * chance - The chance out of 100.
     * reward - The amount of money to win if you win.
     * cost - The cost of the bet.
     * name - The name of the item.
     */
    private transient List<Bet> bets;

    public transient ItemStack itemStack;
    public int slot, chance;
    public int reward, cost;
    public String name;
    public String effect;
    public String jsonItemStack;


    public GUIItem(){

    }
    /**
     * Create a GUIItem.
     * @param is - The Bukkit ItemStack.
     * @param slot - The inventory slot to be used.
     */
    public GUIItem(ItemStack is, int slot){
        this.itemStack = is;
        this.slot = slot;
        this.bets = new ArrayList<Bet>();
        jsonItemStack = getItemStackAsJson().toString();
    }

    public ItemStack getBukkitItem(){
        return itemStack;
    }

    public int getSlot(){ return slot; }

    /**
     * Sets the lore of the item.
     * @param lore - A list of lore lines.
     */
    public void setLore(List<String> lore){
        ItemMeta im = getBukkitItem().getItemMeta();
        im.setLore(lore);
        getBukkitItem().setItemMeta(im);
    }

    /**
     * Set the name of the item.
     * @param name - The name of the item.
     */
    public void setName(String name){
        this.name = name;
        ItemMeta im = getBukkitItem().getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        getBukkitItem().setItemMeta(im);
    }

    public void setEffect(String effect){ this.effect = effect; }

    public Effect getEffect(){ return Effect.valueOf(effect); }

    /**
     * Initiates the bet by creating a GambleBet object and adding it to the list.
     * @param uuid - The player's UUId.
     * @return - The bet created.
     */
    public Bet initiateBet(UUID uuid){
        GambleBet gb = new GambleBet(getCost(), getChance(), getReward(), uuid, getEffect());
        bets.add(gb);
        return gb;
    }

    public void setSlot(int slot){ this.slot = slot; }

    public void setReward(int reward){ this.reward = reward; }

    public void setChance(int chance){ this.chance = chance; }

    public void setCost(int cost){ this.cost = cost; }

    public List<String> getLore(){ return getBukkitItem().getItemMeta().getLore(); }

    public String getName(){ return getBukkitItem().getItemMeta().getDisplayName(); }

    public int getReward(){ return reward; }

    public int getChance(){ return chance; }

    public int getCost(){ return cost; }

    public List<Bet> getBets(){ return bets; }

    public JsonObject getItemStackAsJson(){
        JsonObject jso = new JsonObject();
        jso.addProperty("name", name);
        jso.addProperty("type", getBukkitItem().getType().name());
        JsonArray lore = Gamblr.gson.toJsonTree(getLore()).getAsJsonArray();
        jso.add("lore", lore);
        jso.addProperty("amt", getBukkitItem().getAmount());
        jso.addProperty("data", getBukkitItem().getData().getData());
        return jso;
    }

}
