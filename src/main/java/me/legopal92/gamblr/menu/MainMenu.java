package me.legopal92.gamblr.menu;

import java.util.Arrays;

/**
 * Created by legop on 4/15/2016.
 */
public class MainMenu extends Menu {

    public MainMenu(String name, int size){
        super(name, size);
    }
    /**
     * Set up the GUI with buttons
     *
     * @return The setup button array
     */
    @Override
    protected Button[] setUp() {
        for (Button b : buttons){
            System.out.println("Slot: " + getSlot(b) + " Item: " + b.getItemStack());
        }
        System.out.println(Arrays.toString(buttons));
        return buttons;
    }

    public void setButtons(Button[] butts){
        this.buttons = butts;
    }

}
