package me.legopal92.gamblr.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

/**
 * Created by legop on 4/11/2016.
 */
public class GamblrCommand implements CommandExecutor {

    /**
     * A global HashMap of commands. To be used to determine which command to be executed.
     */
    public static HashMap<String, CommandExecutor> commands = new HashMap<>();

    /**
     *
     * @param sender - The sender of the command.
     * @param cmd - The command that was sent.
     * @param currentAlias - The alias that was used.
     * @param args - The arguments, first of which is the command name.
     * @return - True.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String currentAlias, String[] args){

        if (!sender.hasPermission("Gamblr.admin")){
            sender.sendMessage("You don't have permission to use that command. If you feel that this is an error, please contact an admin.");
            return true;
        }
        if (args.length == 0){
            commands.get("help".toLowerCase()).onCommand(sender, cmd, currentAlias, args);
        } else {
            CommandExecutor ex = commands.get(args[0].toLowerCase());
            if (ex != null){
                ex.onCommand(sender, cmd, currentAlias, args);
            } else {
                sender.sendMessage("That command does not exist.");
            }
        }

        return true;
    }

    /**
     * Register the command so that I can execute it based on the subCommand sent.
     * @param cmd - The subCommand.
     * @param cmdE - The CommandExecutor.
     */
    public void registerCommand(String cmd, CommandExecutor cmdE){
        commands.put(cmd.toLowerCase(), cmdE);
    }
}
