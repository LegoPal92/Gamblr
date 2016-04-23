package me.legopal92.gamblr.commands.subCommands;

import me.legopal92.gamblr.Gamblr;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.Conversation;

/**
 * Created by legop on 4/18/2016.
 */
public class ItemCommand implements SubCommand {
    @Override
    public String getName() {
        return "item";
    }

    @Override
    public String getDescription() {
        return "Opens the Item Conversation.";
    }

    @Override
    public String getUsage() {
        return "/gamblr item";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Conversation conversation = Gamblr.getInstance().getFactory().buildConversation((Conversable) sender);
        conversation.begin();
    }
}
