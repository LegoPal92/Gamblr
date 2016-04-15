package me.legopal92.gamblr.bet;

/**
 * Created by legop on 4/8/2016.
 */
public interface Bet {
    /**
     * Place a bet, provided you have enough currency.
     */
    public void place();

    /**
     * Called when the bet is allowed to happen.
     */
    public void bet();

    /**
     * Called when the player wins the bet.
     */
    public void win();

    /**
     * Called when the player loses the bet.
     */
    public void lose();

    /**
     * The cost of the bet.
     * @return An integer representing the cost of the bet.
     */
    public int cost();

    /**
     * The percentage chance, in XX(%) format.
     * @return the odds of the player winning the bet.
     */
    public int chance();

    /**
     * The prize for winning the bet.
     * @return the prize amount for winning.
     */
    public int prize();

}
