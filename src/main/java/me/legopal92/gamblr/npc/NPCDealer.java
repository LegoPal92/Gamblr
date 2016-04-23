package me.legopal92.gamblr.npc;

import com.google.common.collect.Lists;
import me.legopal92.gamblr.Gamblr;
import me.legopal92.gamblr.menu.MainMenu;
import net.minecraft.server.v1_8_R3.*;

import java.util.List;

/**
 * Created by legop on 4/8/2016.
 */
public class NPCDealer extends EntityVillager {
    /**
     * Self explanatory local variables.
     * dealerArrayList - An arraylist of all the NPCDealers. Used when cleaning up and, ya know, the whole freaking time!
     * gui - The GUI that belongs to the certain Dealer.
     */
    private static List<NPCDealer> DEALERS = Lists.newArrayList();
    private MainMenu gui;

    /**
     * Creates the NPCDealer entity with a sole goal of looking at players.
     * Adds the dealer to the dealerArrayList
     * @param world - The world to spawn in.
     */
    public NPCDealer(World world) {
        super(world);
        NMS.clearGoals(targetSelector, goalSelector);
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0f));
        DEALERS.add(this);
    }

    public NPCDealer(World world, int i) {
        super(world, i);
        NMS.clearGoals(targetSelector, goalSelector);
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0f));
        DEALERS.add(this);
    }

    /**
     * Overriding the move method so that the Dealer does not move.
     * @param x - The X movement.
     * @param y - The Y movement.
     * @param z - The Z movement.
     */
    @Override
    public void g(double x, double y, double z){
    }

    /**
     * Overriding the collide method so that the Dealer cannot be pushed around. It doesn't fall to peer pressure.
     * It is not a scrub. It stands on its own to feet. Working hard, day and night. It has a family.
     * It worked hard to get this far. It doesn't let other people push it around. It stands firm. It was born on a warm day,
     * in the heart of the valley. One day, its mother and father abandoned it. They left to go to the casino. Wait a minute...
     * That's them over there! MOM! DAD! WHERE HAVE YOU BEEN?!?!
     * @param other The entity colliding
     */
    @Override
    public void collide(Entity other){

    }

    /**
     * Sets the gui for the Dealer.
     * @param gui
     */
    public void setGUI(MainMenu gui){
        this.gui = gui;
        Gamblr.getInstance().getDealerConfig().set(this);
    }

    /**
     * Obvious method purpose is obvious.
     * @return - The gui that belongs to this dealer. Why do you want it? It belongs to me. I don't want you to have this.. Don't do this to me. Please?
     */
    public MainMenu getGui(){ return gui; }

    /**
     * Retrieve the ArrayList of NPCDealers stored within.
     * @return - The ArrayList of NPCDealers. Every last one of them.
     */
    public static List<NPCDealer> getDealers(){ return DEALERS; }
}

