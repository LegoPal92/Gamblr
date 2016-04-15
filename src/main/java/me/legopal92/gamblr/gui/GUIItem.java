package me.legopal92.gamblr.gui;

import me.legopal92.gamblr.bet.Bet;
import me.legopal92.gamblr.bet.GambleBet;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
    private List<Bet> bets;

    private ItemStack itemStack;
    private int slot, chance;
    private int reward, cost;
    private String name;
    private Effect effect;

    /**
     * Create a GUIItem.
     * @param is - The Bukkit ItemStack.
     * @param slot - The inventory slot to be used.
     */
    public GUIItem(ItemStack is, int slot){
        this.itemStack = is;
        this.slot = slot;
        this.bets = new ArrayList<Bet>();
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

    public void setEffect(Effect effect){ this.effect = effect; }

    public Effect getEffect(){ return effect; }

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

    /**
     * Overrides the toString() method of Object to provide me with a serialized JSON string to save to a config.
     * @return - A serialized JSON string.
     */
    @Override
    public String toString(){
        JSONObject jso = new JSONObject();
        jso.put("name", name);
        jso.put("slot", slot);
        jso.put("chance", chance);
        jso.put("reward", reward);
        jso.put("cost", cost);
        jso.put("material", getBukkitItem().getType().name());
        jso.put("amount", getBukkitItem().getAmount());
        JSONArray array = new JSONArray();
        if (getLore() != null) {
            for (int i = 0; i < getLore().size(); i++) {
                array.add(i, getLore().get(i));
            }
        }
        jso.put("lore", array);
        jso.put("effect", effect.name());

        return jso.toJSONString();
    }

    /**
     * Load a GUIItem from JSON, which was stored in a config.
     * @param jso - The JSONObject that was the stored string.
     * @return - The GUIItem loaded.
     */
    public static GUIItem fromJSON(JSONObject jso){
        String name = (String)jso.get("name");
        long slot = (Long)jso.get("slot");
        long chance = (Long)jso.get("chance");
        long reward = (Long)jso.get("reward");
        long cost = (Long)jso.get("cost");
        Material m = Material.getMaterial((String)jso.get("material"));
        long amount = (Long)jso.get("amount");
        JSONArray array = (JSONArray)jso.get("lore");
        Effect effect = Effect.valueOf((String)jso.get("effect"));
        List<String> lore = new ArrayList<>();
        for (int i = 0; i <array.size(); i++){
            lore.add(i, (String)array.get(i));
        }

        ItemStack is = new ItemStack(m, (int)amount);
        GUIItem guiItem = new GUIItem(is, (int)slot);
        guiItem.setLore(lore);
        guiItem.setChance((int)chance);
        guiItem.setCost((int)cost);
        guiItem.setName(name);
        guiItem.setReward((int)reward);
        guiItem.setEffect(effect);

        return guiItem;
    }
}
