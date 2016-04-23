package me.legopal92.gamblr.menu;

import org.bukkit.inventory.ItemStack;

/**
 * Created by legop on 4/19/2016.
 */
public class GambleStack {

    private ItemStack is;
    private int chance, reward, cost, amt, radius;
    private String effect;

    public GambleStack(ItemStack is, int amt, int ch, int re, int co, int ra, String effect){
        this.is = is;
        this.chance = ch;
        this.reward = re;
        this.cost = co;
        this.effect = effect;
        this.amt = amt;
        this.radius = ra;
    }

    public int getChance(){ return chance; }

    public int getReward(){ return reward; }

    public int getCost(){ return cost; }

    public int getAmount(){ return amt; }

    public int getRadius() { return radius; }

    public String getEffect(){ return effect; }

    public ItemStack getItemStack(){ return is; }

    public boolean itemStackEquals(ItemStack is){

        if(is.getItemMeta().getDisplayName().equalsIgnoreCase(getItemStack().getItemMeta().getDisplayName()) && is.getType() == getItemStack().getType()){
            return true;
        }

        return false;
    }
}
