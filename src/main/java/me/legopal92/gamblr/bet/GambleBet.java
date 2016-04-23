package me.legopal92.gamblr.bet;

import me.legopal92.gamblr.Gamblr;
import me.legopal92.gamblr.economy.Bank;
import me.legopal92.gamblr.utils.MessageConfig;
import org.bukkit.Effect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Random;
import java.util.UUID;

/**
 * Created by legop on 4/8/2016.
 */
public class GambleBet implements Bet {

    /**
     * Straightforward local variables.
     * Cost is the cost of the item.
     * Chance is the percent out of 100 for a success.
     * Prize is the amount of money that you win if you win.
     * Player is the UUID of the player.
     * Random is a random used to tell if a person wins.
     */
    private int cost;
    private int chance;
    private int prize;
    private int radius;
    private UUID player;
    private Random r;
    private Effect e;
    private PotionEffect pe;

    /**
     *
     * @param cost - The cost of the bet.
     * @param chance - The chance to win out of 100.
     * @param prize - The prize for winning.
     * @param player - The player that this bet belongs to.
     */
    public GambleBet(int cost, int chance, int prize, int radius, UUID player, Effect effect, PotionEffect potionEffect){
        this.chance = chance;
        this.cost = cost;
        this.player = player;
        this.prize = prize;
        this.radius = radius;
        this.r = new Random(System.currentTimeMillis());
        this.e = effect;
        this.pe = potionEffect;
    }

    /**
     * Places the bet, provided they can afford the bet...
     */
    @Override
    public void place() {
        if (Bank.has(player, cost())){
            Bank.withdraw(player, cost());
            Gamblr.getInstance().getServer().getPlayer(player).sendMessage("Bet placed!");
            bet();
        } else {
            Gamblr.getInstance().getServer().getPlayer(player).sendMessage("Unable to afford bet. It costs: " + cost());
        }

    }

    /**
     * The bet, if you win call @link{GambleBet.win()}, if you lose call @link{GambleBet.lost()}.
     */
    @Override
    public void bet(){
        if (r.nextInt(100/chance()) == 1){
            win();
        } else {
            lose();
        }
    }

    /**
     * Called when the player wins the bet. Awards prize money.
     */
    @Override
    public void win() {
        Player bp = Gamblr.getInstance().getServer().getPlayer(player);
        bp.sendMessage(MessageConfig.WIN.replace("%MONEY%", prize + ""));
        if (e != null){
            bp.getWorld().playEffect(bp.getLocation(), e, 0);
        }
        Bank.deposit(player, prize());

    }

    /**
     * Called when the player loses the bet.
     */
    @Override
    public void lose() {
        Gamblr.getInstance().getServer().getPlayer(player).sendMessage(MessageConfig.LOSE);
        Player p = Gamblr.getInstance().getServer().getPlayer(player);
        if (pe == null){
            return;
        }
        for (Entity e : p.getNearbyEntities(radius, radius, radius)){
            if (e.getType() != EntityType.PLAYER){
                continue;
            }
            pe.apply((Player)e);
        }
    }

    /**
     * The cost of the bet.
     * @return The price that is required.
     */
    @Override
    public int cost() {
        return cost;
    }

    /**
     * The chance to win.
     * @return The chance to win the bet, out of 100. EG 46
     */
    @Override
    public int chance() {
        return chance;
    }

    /**
     * The prize money.
     * @return What you get when you win.
     */
    @Override
    public int prize() { return prize; }

    public PotionEffect getPotionEffect() { return pe; }
}
