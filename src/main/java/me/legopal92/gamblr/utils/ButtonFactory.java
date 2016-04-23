package me.legopal92.gamblr.utils;

/**
 * Created by legop on 4/19/2016.
 */
public class ButtonFactory {

    private ItemFactory itemFactory;
    private int chance;
    private int reward;
    private int cost;
    private int amt;
    private String effect;

    public ButtonFactory(ItemFactory itemFactory){
        this.itemFactory = itemFactory;
    }

    public void setEffect(String e){
        this.effect = e;
    }

    public void setChance(int c){
        this.chance = c;
    }

    public void setReward(int r){
        this.reward = r;
    }

    public void setCost(int c) { this.cost = c; }

    public void setAmount(int a) { this.amt = a; }

    public ItemFactory getItemFactory(){ return itemFactory; }

    public String getEffect(){ return effect; }

    public int getChance(){ return chance; }

    public int getReward(){ return reward; }

    public int getCost(){ return cost; }

    public int getAmount(){ return amt; }
}
