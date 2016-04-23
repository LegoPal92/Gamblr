package me.legopal92.gamblr.listeners;

import com.google.common.collect.Maps;
import me.legopal92.gamblr.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

/**
 * Created by legop on 4/16/2016.
 */
public class ItemConversationListener implements Listener {

    public static Map<UUID, ItemFactory> factories = Maps.newHashMap();


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
            }
            itemFactory.setName(s);
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
            }
            Material m = Material.getMaterial(s);
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
            return null;
        }

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "Any data for that item? True or false, please.";
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
            }
            itemFactory.setData(b);
            return null;
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
            }
            if (itemFactory.hasLore()){
                return "What more would you like to add?";
            }
            return "Would you like to add some lore? Yes or no?";
        }

        @Override
        public Prompt acceptInput(ConversationContext conversationContext, String s) {

            ItemFactory itemFactory;
            if (factories.containsKey(((Player)conversationContext.getForWhom()).getUniqueId())){
                itemFactory = factories.get(((Player)conversationContext.getForWhom()).getUniqueId());
            } else {
                itemFactory = new ItemFactory(((Player)conversationContext.getForWhom()).getUniqueId());
            }
            if (s.equalsIgnoreCase("No")){
                ItemStack is = itemFactory.build();
                Player player = (Player) conversationContext.getForWhom();
                player.getInventory().setItemInMainHand(is);
                player.sendMessage("Here is an example of the item you created.");
                return new ItemFinalizePrompt();
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
                return new ItemFinalizePrompt();
            }

        }

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "Do you have more lore? True or false.";
        }
    }

    public static class ItemFinalizePrompt extends BooleanPrompt{

        @Override
        protected Prompt acceptValidatedInput(ConversationContext conversationContext, boolean b) {
            if (b){
                return Prompt.END_OF_CONVERSATION;
            }

            factories.remove(((Player) conversationContext.getForWhom()).getUniqueId());
            return new ItemNamePrompt();
        }

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "Is this to your satisfaction? True or false.";
        }
    }

}
