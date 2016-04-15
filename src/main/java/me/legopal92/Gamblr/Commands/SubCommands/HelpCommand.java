package me.legopal92.Gamblr.Commands.SubCommands;

import me.legopal92.Gamblr.Commands.GamblrCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * Created by legop on 4/11/2016.
 */
public class HelpCommand extends GamblrCommand{

    public HelpCommand(){
        registerCommand("help", this);
        registerHelp("help", "Displays this menu.");
        registerHelp("help", "Usage: /Gamblr or /Gamblr help");
    }

    private static ArrayList<String> help = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String currentAlias, String[] args){

        for (String s : help){
            sender.sendMessage(s);
        }

        return true;
    }


    public static void registerHelp(String cmd, String help){
        HelpCommand.help.add(cmd+" - "+help);
    }
}
