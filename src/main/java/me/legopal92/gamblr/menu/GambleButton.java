package me.legopal92.gamblr.menu;

import me.legopal92.gamblr.bet.GambleBet;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

/**
 * Created by legop on 4/15/2016.
 */
public class GambleButton extends Button {

    private int slot, cost, prize, chance, radius;
    private Effect effect;
    private PotionEffect pe;

    public GambleButton(ItemStack is, int cost, int chance, int prize, int radius, Effect effect){
        super(is);
        this.cost = cost;
        this.chance = chance;
        this.prize = prize;
        this.effect = effect;
        this.radius = radius;
    }

    @Override
    public void onClick(Player player){
        GambleBet gb = new GambleBet(cost, chance, prize, radius, player.getUniqueId(), effect, pe);
        gb.place();
    }

    public int getSlot(){
        return this.slot;
    }

    public int getCost(){
        return this.cost;
    }

    public int getPrize(){
        return this.prize;
    }

    public int getChance(){
        return chance;
    }

    public int getRadius() { return radius; }

    public String getEffect(){
        return effect.name();
    }

    public void setSlot(int slot){
        this.slot = slot;
    }

    public void setPotionEffect(PotionEffect pe) { this.pe = pe; }

    public PotionEffect getPotionEffect() { return pe; }
}
