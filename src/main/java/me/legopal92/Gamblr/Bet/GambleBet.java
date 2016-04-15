package me.legopal92.Gamblr.Bet;

import me.legopal92.Gamblr.Economy.Bank;
import me.legopal92.Gamblr.Gamblr;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

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
    private UUID player;
    private Random r;
    private Effect e;

    /**
     *
     * @param cost - The cost of the bet.
     * @param chance - The chance to win out of 100.
     * @param prize - The prize for winning.
     * @param player - The player that this bet belongs to.
     */
    public GambleBet(int cost, int chance, int prize, UUID player, Effect effect){
        this.chance = chance;
        this.cost = cost;
        this.player = player;
        this.prize = prize;
        this.r = new Random(System.currentTimeMillis());
        this.e = effect;
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
        bp.sendMessage("Congratulations! You have won: " + prize());
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
        Gamblr.getInstance().getServer().getPlayer(player).sendMessage("I'm sorry, you have lost, better luck next time!");
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
}
