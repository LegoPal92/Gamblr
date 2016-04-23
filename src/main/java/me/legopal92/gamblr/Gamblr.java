package me.legopal92.gamblr;

import me.legopal92.gamblr.commands.GamblrCommand;
import me.legopal92.gamblr.listeners.ItemConversationListener;
import me.legopal92.gamblr.listeners.MenuConversationListener;
import me.legopal92.gamblr.listeners.PlayerListener;
import me.legopal92.gamblr.menu.MenuListener;
import me.legopal92.gamblr.npc.NPCDealer;
import me.legopal92.gamblr.utils.DealerConfig;
import me.legopal92.gamblr.utils.MessageConfig;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by legop on 4/8/2016.
 */
public class Gamblr extends JavaPlugin {

    private static Gamblr inst;

    private Economy econ;
    private ConversationFactory factory;
    private ConversationFactory menuFactory;
    private DealerConfig config;

    @Override
    public void onEnable() {
        inst = this;
        if(getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().severe("Vault not found, disabling.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupEconomy();
        factory = new ConversationFactory(this).withModality(true).withFirstPrompt(new ItemConversationListener.ItemNamePrompt()).withEscapeSequence("//done");
        menuFactory = new ConversationFactory(this).withModality(true).withFirstPrompt(new MenuConversationListener.CreateMenuPrompt()).withEscapeSequence("//done");

        try{
            config = new DealerConfig();
            new MessageConfig();
        } catch (Exception e){
            e.printStackTrace();
        }
        config.loadDealers();
        getCommand("gamblr").setExecutor(new GamblrCommand());

        {//REGISTER EVENTS
            getServer().getPluginManager().registerEvents(new PlayerListener(), this);
            getServer().getPluginManager().registerEvents(new MenuListener(), this);
        }
    }

    @Override
    public void onDisable(){
        for (NPCDealer d : NPCDealer.getDealers()){
            d.getWorld().removeEntity(d);
            d.die();
        }
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> serviceProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if(serviceProvider == null) {
            return false;
        }
        this.econ = serviceProvider.getProvider();
        return true;
    }

    public static Gamblr getInstance(){ return inst;}

    public Economy getEconomy() {return econ;}

    public ConversationFactory getFactory(){ return factory; }

    public ConversationFactory getMenuFactory(){ return menuFactory; }

    public DealerConfig getDealerConfig(){
        return config;
    }


}
