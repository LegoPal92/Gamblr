package me.legopal92.gamblr.commands;

import com.google.common.collect.Maps;
import me.legopal92.gamblr.commands.subCommands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.HelpCommand;

import java.util.Map;

/**
 * Created by legop on 4/11/2016.
 */
public class GamblrCommand implements CommandExecutor {

    /**
     * A global HashMap of commands. To be used to determine which command to be executed.
     */
    public static Map<String, SubCommand> subCommands = Maps.newHashMap();
    private static HelpCommand HELP;

    public GamblrCommand() {
    }

    /**
     * @param sender       - The sender of the command.
     * @param cmd          - The command that was sent.
     * @param currentAlias - The alias that was used.
     * @param args         - The arguments, first of which is the command name.
     * @return - True.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String currentAlias, String[] args) {
        if (!sender.hasPermission("Gamblr.admin")) {
            sender.sendMessage("You don't have permission to use that command. If you feel that this is an error, please contact an admin.");
            return true;
        }
        if (args.length == 0) {
            //subCommands.get("help".toLowerCase()).execute(sender, args);
            return true;
        }
        SubCommand ex = subCommands.get(args[0].toLowerCase());
        if(ex == null) {
            //sendHelp
            return true;
        }
        String[] newArgs = new String[args.length];
        System.arraycopy(args, 1, newArgs, 0, args.length - 1);
        ex.execute(sender, newArgs);
        return true;
    }

    /**
     * Register the command so that I can execute it based on the subCommand sent.
     *
     * @param commands - A list of SubCommands
     */
    public void registerCommand(SubCommand... commands) {
        for (SubCommand command : commands) {
            subCommands.put(command.getName(), command);
        }
    }
}
