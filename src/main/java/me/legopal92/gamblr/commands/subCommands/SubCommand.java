package me.legopal92.gamblr.commands.subCommands;

import org.bukkit.command.CommandSender;

/**
 * @author Timothy Andis (TadahTech) on 4/15/2016.
 */
public interface SubCommand {

    String getName();
    String getDescription();
    String getUsage();
    void execute(CommandSender sender, String[] args);
}
