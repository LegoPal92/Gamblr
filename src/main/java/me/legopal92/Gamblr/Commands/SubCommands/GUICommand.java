package me.legopal92.gamblr.commands.subCommands;

import me.legopal92.gamblr.commands.GamblrCommand;
import me.legopal92.gamblr.gui.GUI;
import me.legopal92.gamblr.gui.GUIItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by legop on 4/11/2016.
 */
public class GUICommand extends GamblrCommand {

    public GUICommand(){
        registerCommand("gui", this);
        HelpCommand.registerHelp("gui", "Creates a gui for a dealer.");
        HelpCommand.registerHelp("gui", "Usage: /Gamblr gui NAME SLOTS");
        HelpCommand.registerHelp("gui", "Requires you to create some items first.");
    }

    //-------/Gamblr gui NAME SLOTS
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String currentAlias, String[] args){
        if (!(sender instanceof Player)){
            sender.sendMessage("You must be a player to use that command.");
        }
        if (args.length != 3){
            sender.sendMessage("Improper arguments. Usage: ");
            sender.sendMessage("/Gamblr gui NAME SLOTS");
            return true;
        }

        Player player = (Player) sender;
        if (!ItemCommand.playerItems.containsKey(player.getUniqueId())){
            sender.sendMessage("Create some items first.");
            return true;
        }
        ArrayList<GUIItem> items = ItemCommand.playerItems.get(player.getUniqueId());
        GUIItem[] gis = new GUIItem[items.size()];
        for (int i = 0; i < items.size(); i++){
            gis[i] = items.get(i);
        }
        new GUI(args[1], Integer.parseInt(args[2]), gis);

        return true;

    }
}
