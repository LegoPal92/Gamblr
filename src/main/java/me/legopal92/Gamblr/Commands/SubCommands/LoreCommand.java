package me.legopal92.Gamblr.Commands.SubCommands;

import me.legopal92.Gamblr.Commands.GamblrCommand;
import me.legopal92.Gamblr.GUI.GUI;
import me.legopal92.Gamblr.GUI.GUIItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by legop on 4/11/2016.
 */
public class LoreCommand extends GamblrCommand {

    public LoreCommand(){
        registerCommand("lore", this);
        HelpCommand.registerHelp("lore", "Creates a line of lore for an item, adding to the existing lore if needed.");
        HelpCommand.registerHelp("lore", "Usage: /Gamblr lore NAME (LORE)");
    }

    //-------/Gamblr lore NAME (LORE GOES HERE)
    //--------Creates a line of lore for an item, adding to the existing lore if needed.
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String currentAlias, String[] args){

        String name = args[1];

        GUIItem guiItem = null;

        for (GUI gui : GUI.guis){
            for (GUIItem gi : gui.getItems()){
                if (gi.getName().equalsIgnoreCase(name)){
                    guiItem = gi;
                    break;
                }
            }
        }

        if (guiItem == null){
            sender.sendMessage("That item doesn't exist. Do you have the name correct?");
            return true;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i<args.length; i++){
            sb.append(args[i]);
        }
        List<String> lore = guiItem.getLore();
        lore.add(sb.toString());
        guiItem.setLore(lore);

        return true;
    }

}
