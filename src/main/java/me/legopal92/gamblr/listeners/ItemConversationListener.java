package me.legopal92.gamblr.listeners;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.legopal92.gamblr.menu.GambleStack;
import me.legopal92.gamblr.utils.ButtonFactory;
import me.legopal92.gamblr.utils.ItemFactory;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

/**
 * Created by legop on 4/16/2016.
 */
public class ItemConversationListener implements Listener {

    public static Map<UUID, ItemFactory> factories = Maps.newHashMap();
    public static Map<UUID, ButtonFactory> bFactories = Maps.newHashMap();
    public static Map<UUID, ArrayList<GambleStack>> items = Maps.newHashMap();


    public static class ItemNamePrompt extends StringPrompt{

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "What would you like your item to be called?";
        }

        @Override
        public Prompt acceptInput(ConversationContext conversationContext, String s) {
            ItemFactory itemFactory;
            if (factories.containsKey(((Player)conversationContext.getForWhom()).getUniqueId())){
                itemFactory = factories.get(((Player)conversationContext.getForWhom()).getUniqueId());
            } else {
                itemFactory = new ItemFactory(((Player)conversationContext.getForWhom()).getUniqueId());
                factories.put(((Player) conversationContext.getForWhom()).getUniqueId(), itemFactory);
            }
            itemFactory.setName(ChatColor.translateAlternateColorCodes('&', s));
            return new ItemMaterialPrompt();
        }
    }

    public static class ItemMaterialPrompt extends StringPrompt{

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "What would you like the item to be?";
        }

        @Override
        public Prompt acceptInput(ConversationContext conversationContext, String s) {
            ItemFactory itemFactory;
            if (factories.containsKey(((Player)conversationContext.getForWhom()).getUniqueId())){
                itemFactory = factories.get(((Player)conversationContext.getForWhom()).getUniqueId());
            } else {
                itemFactory = new ItemFactory(((Player)conversationContext.getForWhom()).getUniqueId());
                factories.put(((Player) conversationContext.getForWhom()).getUniqueId(), itemFactory);
            }
            Material m = Material.getMaterial(s.toUpperCase());
            itemFactory.setMaterial(m);
            return new ItemAmountPrompt();
        }
    }

    public static class ItemAmountPrompt extends NumericPrompt{

        @Override
        protected Prompt acceptValidatedInput(ConversationContext conversationContext, Number number) {
            int i = number.intValue();
            ItemFactory itemFactory;
            if (factories.containsKey(((Player)conversationContext.getForWhom()).getUniqueId())){
                itemFactory = factories.get(((Player)conversationContext.getForWhom()).getUniqueId());
            } else {
                itemFactory = new ItemFactory(((Player)conversationContext.getForWhom()).getUniqueId());
                factories.put(((Player) conversationContext.getForWhom()).getUniqueId(), itemFactory);
            }
            itemFactory.setAmount(i);
            return new ItemDataVerifyPrompt();
        }

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "How many of them would you like in the stack?";
        }
    }

    public static class ItemDataVerifyPrompt extends BooleanPrompt{

        @Override
        protected Prompt acceptValidatedInput(ConversationContext conversationContext, boolean b) {
            if (b){
                return new ItemDataPrompt();
            }
            return new ItemLorePrompt();
        }

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "Any data for that item?";
        }
    }

    public static class ItemDataPrompt extends NumericPrompt{

        @Override
        protected Prompt acceptValidatedInput(ConversationContext conversationContext, Number number) {
            byte b = number.byteValue();
            ItemFactory itemFactory;
            if (factories.containsKey(((Player)conversationContext.getForWhom()).getUniqueId())){
                itemFactory = factories.get(((Player)conversationContext.getForWhom()).getUniqueId());
            } else {
                itemFactory = new ItemFactory(((Player)conversationContext.getForWhom()).getUniqueId());
                factories.put(((Player) conversationContext.getForWhom()).getUniqueId(), itemFactory);
            }
            itemFactory.setData(b);
            return new ItemLorePrompt();
        }

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "What data would you like? In byte format, please.";
        }
    }

    public static class ItemLorePrompt extends StringPrompt{

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            ItemFactory itemFactory;
            if (factories.containsKey(((Player)conversationContext.getForWhom()).getUniqueId())){
                itemFactory = factories.get(((Player)conversationContext.getForWhom()).getUniqueId());
            } else {
                itemFactory = new ItemFactory(((Player)conversationContext.getForWhom()).getUniqueId());
                factories.put(((Player) conversationContext.getForWhom()).getUniqueId(), itemFactory);
            }
            if (itemFactory.hasLore()){
                return "What more would you like to add?";
            }
            return "Would you like to add some lore? Either type it out now, or type \"no\"";
        }

        @Override
        public Prompt acceptInput(ConversationContext conversationContext, String s) {

            ItemFactory itemFactory;
            if (factories.containsKey(((Player)conversationContext.getForWhom()).getUniqueId())){
                itemFactory = factories.get(((Player)conversationContext.getForWhom()).getUniqueId());
            } else {
                itemFactory = new ItemFactory(((Player)conversationContext.getForWhom()).getUniqueId());
                factories.put(((Player) conversationContext.getForWhom()).getUniqueId(), itemFactory);
            }
            if (s.equalsIgnoreCase("No")){
                return new ItemChancePrompt();
            }

            itemFactory.addLore(s);
            return new ItemLoreMorePrompt();
        }
    }

    public static class ItemLoreMorePrompt extends BooleanPrompt{

        @Override
        protected Prompt acceptValidatedInput(ConversationContext conversationContext, boolean b) {
            if (b){
                return new ItemLorePrompt();
            } else {
                return new ItemChancePrompt();
            }

        }

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "Do you have more lore?";
        }
    }

    public static class ItemChancePrompt extends NumericPrompt{

        @Override
        protected Prompt acceptValidatedInput(ConversationContext conversationContext, Number number) {
            int i = number.intValue();
            ButtonFactory buttonFactory;
            if (bFactories.containsKey(((Player)conversationContext.getForWhom()).getUniqueId())){
                buttonFactory = bFactories.get(((Player)conversationContext.getForWhom()).getUniqueId());
            } else {
                buttonFactory = new ButtonFactory(factories.get(((Player)conversationContext.getForWhom()).getUniqueId()));
                bFactories.put(((Player)conversationContext.getForWhom()).getUniqueId(), buttonFactory);
            }
            buttonFactory.setChance(i);
            return new ItemCostPrompt();
        }

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "What percentage chance would you like this item to have?";
        }
    }

    public static class ItemCostPrompt extends NumericPrompt{

        @Override
        protected Prompt acceptValidatedInput(ConversationContext conversationContext, Number number) {
            int i = number.intValue();
            ButtonFactory buttonFactory;
            if (bFactories.containsKey(((Player)conversationContext.getForWhom()).getUniqueId())){
                buttonFactory = bFactories.get(((Player)conversationContext.getForWhom()).getUniqueId());
            } else {
                buttonFactory = new ButtonFactory(factories.get(((Player)conversationContext.getForWhom()).getUniqueId()));
                bFactories.put(((Player)conversationContext.getForWhom()).getUniqueId(), buttonFactory);
            }
            buttonFactory.setCost(i);
            return new ItemRewardPrompt();
        }

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "How much does it cost?";
        }
    }

    public static class ItemRewardPrompt extends NumericPrompt{

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "How much money should they win?";
        }

        @Override
        protected Prompt acceptValidatedInput(ConversationContext conversationContext, Number number) {
            int i = number.intValue();
            ButtonFactory buttonFactory;
            if (bFactories.containsKey(((Player)conversationContext.getForWhom()).getUniqueId())){
                buttonFactory = bFactories.get(((Player)conversationContext.getForWhom()).getUniqueId());
            } else {
                buttonFactory = new ButtonFactory(factories.get(((Player)conversationContext.getForWhom()).getUniqueId()));
                bFactories.put(((Player)conversationContext.getForWhom()).getUniqueId(), buttonFactory);
            }
            buttonFactory.setReward(i);
            return new ItemEffectPrompt();
        }
    }

    public static class ItemEffectPrompt extends StringPrompt{

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "What particle effect would you like to play?";
        }

        @Override
        public Prompt acceptInput(ConversationContext conversationContext, String s) {
            ButtonFactory buttonFactory;
            if (bFactories.containsKey(((Player)conversationContext.getForWhom()).getUniqueId())){
                buttonFactory = bFactories.get(((Player)conversationContext.getForWhom()).getUniqueId());
            } else {
                buttonFactory = new ButtonFactory(factories.get(((Player)conversationContext.getForWhom()).getUniqueId()));
                bFactories.put(((Player)conversationContext.getForWhom()).getUniqueId(), buttonFactory);
            }
            buttonFactory.setEffect(s);
            ItemFactory itemFactory;
            if (factories.containsKey(((Player)conversationContext.getForWhom()).getUniqueId())){
                itemFactory = factories.get(((Player)conversationContext.getForWhom()).getUniqueId());
            } else {
                itemFactory = new ItemFactory(((Player)conversationContext.getForWhom()).getUniqueId());
                factories.put(((Player) conversationContext.getForWhom()).getUniqueId(), itemFactory);
            }
            buttonFactory.setAmount(itemFactory.getAmount());
            ItemStack is = itemFactory.build();

            Player player = (Player) conversationContext.getForWhom();
            player.getInventory().setItemInHand(is);
            player.sendMessage("Here is an example of the item you created.");
            return new ItemFinalizePrompt();
        }
    }

    public static class ItemFinalizePrompt extends BooleanPrompt{

        @Override
        protected Prompt acceptValidatedInput(ConversationContext conversationContext, boolean b) {
            if (b){
                ButtonFactory buttonFactory;
                if (bFactories.containsKey(((Player)conversationContext.getForWhom()).getUniqueId())){
                    buttonFactory = bFactories.get(((Player)conversationContext.getForWhom()).getUniqueId());
                } else {
                    buttonFactory = new ButtonFactory(factories.get(((Player)conversationContext.getForWhom()).getUniqueId()));
                    bFactories.put(((Player)conversationContext.getForWhom()).getUniqueId(), buttonFactory);
                }
                Player player = (Player) conversationContext.getForWhom();
                GambleStack gs = new GambleStack(player.getInventory().getItemInHand(), buttonFactory.getAmount(), buttonFactory.getChance(), buttonFactory.getReward(), buttonFactory.getCost(), 0, buttonFactory.getEffect());
                ArrayList<GambleStack> gss = items.get(player.getUniqueId());
                if (gss == null){
                    gss = Lists.newArrayList();
                }
                gss.add(gs);
                items.put(player.getUniqueId(), gss);
                player.sendMessage("Item created, you can create another by running /gamblr item, or start menu creation with /gamblr menu.");
                factories.remove(((Player) conversationContext.getForWhom()).getUniqueId());
                bFactories.remove(((Player)conversationContext.getForWhom()).getUniqueId());
                return Prompt.END_OF_CONVERSATION;
            }

            factories.remove(((Player) conversationContext.getForWhom()).getUniqueId());
            bFactories.remove(((Player)conversationContext.getForWhom()).getUniqueId());
            ((Player)conversationContext.getForWhom()).sendMessage("Starting anew.");
            return new ItemNamePrompt();
        }

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "Is this to your satisfaction? True or false.";
        }
    }

}
