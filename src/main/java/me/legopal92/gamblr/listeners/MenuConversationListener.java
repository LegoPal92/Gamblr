package me.legopal92.gamblr.listeners;

import me.legopal92.gamblr.menu.MainMenu;
import me.legopal92.gamblr.npc.CustomEntityType;
import me.legopal92.gamblr.npc.NPCDealer;
import org.bukkit.ChatColor;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;

/**
 * Created by legop on 4/18/2016.
 */
public class MenuConversationListener {

    public static class CreateMenuPrompt extends BooleanPrompt{

        @Override
        protected Prompt acceptValidatedInput(ConversationContext conversationContext, boolean b) {
            if (b){

                return new MenuSizePrompt();
            } else {
                Player player = (Player) conversationContext.getForWhom();
                player.sendMessage("Please create your items before continuing.");
                return Prompt.END_OF_CONVERSATION;
            }
        }

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "Are you ready to create the menu? Do you have all of your items in your inventory to arrange in the menu?";
        }
    }

    public static class MenuSizePrompt extends NumericPrompt{

        @Override
        protected Prompt acceptValidatedInput(ConversationContext conversationContext, Number number) {
            int i = number.intValue();

            return new MenuNamePrompt(i);
        }

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "How many slots would you like in the menu? (Multiple of 9, please.";
        }
    }

    public static class MenuNamePrompt extends StringPrompt{

        private int size;

        public MenuNamePrompt(int i){
            this.size = i;
        }

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "What would you like the name to be?";
        }

        @Override
        public Prompt acceptInput(ConversationContext conversationContext, String s) {
            Player player = (Player) conversationContext.getForWhom();
            MainMenu mm = new MainMenu(ChatColor.translateAlternateColorCodes('&', s), size);
            mm.open(player);

            return new VillagerNamePrompt(mm);
        }
    }

    public static class VillagerNamePrompt extends StringPrompt{
        MainMenu mm;

        public VillagerNamePrompt(MainMenu mm){
            this.mm = mm;
        }

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "What would you like your dealer to be called?";
        }

        @Override
        public Prompt acceptInput(ConversationContext conversationContext, String s) {
            return new VillagerCreatePrompt(mm, s);
        }
    }

    public static class VillagerCreatePrompt extends BooleanPrompt{
        MainMenu mm;
        String name;

        public VillagerCreatePrompt(MainMenu mm, String n){
            this.mm = mm;
            this.name = n;
        }

        @Override
        protected Prompt acceptValidatedInput(ConversationContext conversationContext, boolean b) {
            Player player = (Player) conversationContext.getForWhom();
            NPCDealer dealer = (NPCDealer)CustomEntityType.NPCDEALER.spawn(player.getLocation());
            dealer.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
            dealer.setGUI(mm);

            return Prompt.END_OF_CONVERSATION;
        }

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return "Are you standing where your Dealer should be? If not, please do and type true.";
        }
    }
}
