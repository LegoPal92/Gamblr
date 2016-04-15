package me.legopal92.gamblr;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by legop on 4/8/2016.
 */
public class Gamblr extends JavaPlugin {

    private static Gamblr inst;

    private Economy econ;

    @Override
    public void onEnable() {
        inst = this;
        if(getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().severe("Vault not found, disabling.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupEconomy();
        
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> serviceProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if(serviceProvider == null) {
            return false;
        }
        this.econ = serviceProvider.getProvider();
        return true;
    }
}
