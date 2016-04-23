package me.legopal92.gamblr.commands.subCommands;

import me.legopal92.gamblr.Gamblr;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.Conversation;

/**
 * Created by legop on 4/19/2016.
 */
public class MenuCommand implements SubCommand {
    @Override
    public String getName() {
        return "menu";
    }

    @Override
    public String getDescription() {
        return "Start the menu conversation.";
    }

    @Override
    public String getUsage() {
        return "/gamblr menu";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Conversation conversation = Gamblr.getInstance().getMenuFactory().buildConversation((Conversable) sender);
        conversation.begin();
    }
}
