package me.legopal92.gamblr.commands.subCommands;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by legop on 4/20/2016.
 */
public class HelpCommand implements SubCommand {

    public static List<String> help = Lists.newArrayList();
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Help of Gamblr.";
    }

    @Override
    public String getUsage() {
        return "/gamblr";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        for (String s : help){

        }
    }
}
