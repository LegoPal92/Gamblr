package me.legopal92.gamblr.commands.subCommands;

import me.legopal92.gamblr.commands.GamblrCommand;
import me.legopal92.gamblr.gui.GUI;
import me.legopal92.gamblr.Gamblr;
import me.legopal92.gamblr.npc.CustomEntityType;
import me.legopal92.gamblr.npc.NPCDealer;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by legop on 4/11/2016.
 */
public class CreateCommand extends GamblrCommand {

    public CreateCommand(){
        registerCommand("Create", this);
        HelpCommand.registerHelp("create", "Creates a dealer at the location you are standing at.");
        HelpCommand.registerHelp("create", "Usage: /Gamblr create NAME GUI_NAME");
    }

    //-------/Gamblr create NAME GUI_NAME
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String currentAlias, String[] args){
        if (!(sender instanceof Player)){
            sender.sendMessage("You must be a player to perform this command.");
            return true;
        }

        if (args.length != 3){
            sender.sendMessage("Improper arguments. Usage: /Gamblr create NAME GUI_NAME");
            return true;
        }

        Player player = (Player) sender;
        Location l = player.getLocation();
        GUI g = GUI.getByName(args[2]);
        if (g == null){
            sender.sendMessage("Invalid gui name.");
            return true;
        }
        NPCDealer dealer = (NPCDealer) CustomEntityType.NPCDEALER.spawn(l);
        dealer.setGUI(g);
        dealer.setCustomName(args[1]);
        dealer.setCustomNameVisible(true);

        try {
            Gamblr.getInstance().saveDealer(dealer);
        } catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }
}
