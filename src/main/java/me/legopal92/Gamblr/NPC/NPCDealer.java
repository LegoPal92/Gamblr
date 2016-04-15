package me.legopal92.Gamblr.NPC;

import me.legopal92.Gamblr.GUI.GUI;
import me.legopal92.Gamblr.Gamblr;
import net.minecraft.server.v1_9_R1.*;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by legop on 4/8/2016.
 */
public class NPCDealer extends EntityVillager {
    /**
     * Self explanatory local variables.
     * dealerArrayList - An arraylist of all the NPCDealers. Used when cleaning up and, ya know, the whole freaking time!
     * gui - The GUI that belongs to the certain Dealer.
     */
    private static ArrayList<NPCDealer> dealerArrayList = new ArrayList<>();
    private GUI gui;

    /**
     * Creates the NPCDealer entity with a sole goal of looking at players.
     * Adds the dealer to the dealerArrayList
     * @param world - The world to spawn in.
     */
    public NPCDealer(World world) throws Exception{
        super(world);
        NMS.clearGoals(targetSelector, goalSelector);
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0f));
        this.dealerArrayList.add(this);
    }

    public NPCDealer(World world, int i) {
        super(world, i);
        NMS.clearGoals(targetSelector, goalSelector);
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0f));
        this.dealerArrayList.add(this);
    }

    /**
     * Overriding the move method so that the Dealer does not move.
     * @param x - The X movement.
     * @param y - The Y movement.
     * @param z - The Z movement.
     */
    @Override
    public void g(double x, double y, double z){
        return;
    }

    /**
     * Overriding the collide method so that the Dealer cannot be pushed around. It doesn't fall to peer pressure.
     * It is not a scrub. It stands on its own to feet. Working hard, day and night. It has a family.
     * It worked hard to get this far. It doesn't let other people push it around. It stands firm. It was born on a warm day,
     * in the heart of the valley. One day, its mother and father abandoned it. They left to go to the casino. Wait a minute...
     * That's them over there! MOM! DAD! WHERE HAVE YOU BEEN?!?!
     * @param other
     */
    @Override
    public void collide(Entity other){

    }

    /**
     * Sets the gui for the Dealer.
     * @param gui
     */
    public void setGUI(GUI gui){
        this.gui = gui;
    }

    /**
     * Obvious method purpose is obvious.
     * @return - The gui that belongs to this dealer. Why do you want it? It belongs to me. I don't want you to have this.. Don't do this to me. Please?
     */
    public GUI getGui(){ return gui; }

    /**
     * Retrieve the ArrayList of NPCDealers stored within.
     * @return - The ArrayList of NPCDealers. Every last one of them.
     */
    public static ArrayList<NPCDealer> getDealers(){ return dealerArrayList; }
}

