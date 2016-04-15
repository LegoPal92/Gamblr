package me.legopal92.Gamblr.Economy;

import me.legopal92.Gamblr.Gamblr;

import java.util.UUID;

/**
 * Created by legop on 4/8/2016.
 */
public class Bank {

    /**
     * Craetes a bank with Vault.
     */
    public Bank(){
        Gamblr.getEconomy().createBank("Gamblr", "Gamblr");
    }

    /**
     * Join the bank with a starting amount of money. Default 100.
     * @param u - The player's UUID.
     */
    public static void join(UUID u){
        join(u, 100);
    }

    /**
     * Join the bank with a starting amount of money.
     * @param u - The player's UUID.
     * @param startingBalance - The starting balance.
     */
    public static void join(UUID u, double startingBalance){
        Gamblr.getEconomy().depositPlayer(Gamblr.getInstance().getServer().getOfflinePlayer(u), "Gamblr", startingBalance);
    }

    /**
     * Checks to see if the player has enough money to do the thing.
     * @param u - The player's UUID.
     * @param h - The amount in question.
     * @return - true if the person has enough money.
     */
    public static boolean has(UUID u, double h){
        return Gamblr.getEconomy().has(Gamblr.getInstance().getServer().getOfflinePlayer(u), "Gamblr", h);

    }

    public static boolean isInBank(UUID u){
        return Gamblr.getEconomy().hasAccount(Gamblr.getInstance().getServer().getOfflinePlayer(u), "Gamblr");
    }

    /**
     * Deposits money into the player's account.
     * @param u - The player's UUId.
     * @param d - The amount to deposit.
     */
    public static void deposit(UUID u, double d){
        Gamblr.getEconomy().depositPlayer(Gamblr.getInstance().getServer().getOfflinePlayer(u), "Gamblr", d);
    }

    /**
     * Withdraws money from the player's account.
     * @param u - The player's UUID.
     * @param d - The amount to withdraw.
     */
    public static void withdraw(UUID u, double d){
        Gamblr.getEconomy().withdrawPlayer(Gamblr.getInstance().getServer().getOfflinePlayer(u), d);
    }
}
