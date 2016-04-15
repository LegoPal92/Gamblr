package me.legopal92.Gamblr.Commands.SubCommands;

import me.legopal92.Gamblr.Commands.GamblrCommand;
import me.legopal92.Gamblr.GUI.GUIItem;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by legop on 4/11/2016.
 */
public class ItemCommand extends GamblrCommand {

    public ItemCommand(){
        registerCommand("item", this);
        HelpCommand.registerHelp("item", "Create the item in a gui.");
        HelpCommand.registerHelp("item", "Usage: /Gamblr item MATERIAL NAME AMOUNT SLOT CHANCE COST REWARD");
    }

    public static HashMap<UUID, ArrayList<GUIItem>> playerItems = new HashMap<>();
    //-----/Gamblr item TYPE NAME AMT SLOT CHANCE COST REWARD EFFECT
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String currentAlias, String[] args){
        if (!(sender instanceof Player)){
            sender.sendMessage("You must be a player to use that command.");
            return true;
        }
        String effect = null;
        if (args.length == 9){
            effect = args[8];
        }
        if (args.length != 9){
            sender.sendMessage("Improper arguments. Usage: ");
            sender.sendMessage("/Gamblr item TYPE NAME AMT SLOT CHANCE COST REWARD EFFECT");
            return true;
        }
        Player player = (Player) sender;
        Material m = Material.getMaterial(args[1].toUpperCase());
        String name = args[2];
        int amt = Integer.parseInt(args[3]);
        int slot = Integer.parseInt(args[4]);
        int chance = Integer.parseInt(args[5]);
        int cost = Integer.parseInt(args[6]);
        int reward = Integer.parseInt(args[7]);


        GUIItem gi = new GUIItem(new ItemStack(m, amt), slot);
        gi.setReward(reward);
        gi.setCost(cost);
        gi.setChance(chance);
        gi.setName(name);

        if (!effect.equals(null)){
            Effect effect1 = Effect.valueOf(effect.toUpperCase());
            gi.setEffect(effect1);
        }
        if (playerItems.containsKey(player.getUniqueId())){
            ArrayList<GUIItem> items = playerItems.get(player.getUniqueId());
            items.add(gi);
            playerItems.remove(player.getUniqueId());
            playerItems.put(player.getUniqueId(), items);

        } else {
            ArrayList<GUIItem> items = new ArrayList<>();
            items.add(gi);
            playerItems.put(player.getUniqueId(), items);
        }

        sender.sendMessage("Successfully create " + name + "!");


        return true;
    }
}
