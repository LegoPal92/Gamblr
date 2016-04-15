package me.legopal92.Gamblr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.legopal92.Gamblr.Commands.GamblrCommand;
import me.legopal92.Gamblr.Commands.SubCommands.*;
import me.legopal92.Gamblr.Economy.Bank;
import me.legopal92.Gamblr.GUI.GUI;
import me.legopal92.Gamblr.GUI.ItemSerializer;
import me.legopal92.Gamblr.Listeners.PlayerListener;
import me.legopal92.Gamblr.NPC.CustomEntityType;
import me.legopal92.Gamblr.NPC.NPCDealer;
import me.legopal92.Gamblr.Utils.LocationUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by legop on 4/8/2016.
 */
public class Gamblr extends JavaPlugin {

    private static Economy econ;
    private static Gamblr inst;
    public static Gson gson = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(ItemStack.class, new ItemSerializer()).create();

    @Override
    public void onEnable() {
        getDataFolder().mkdir();
        inst = this;
        {//NMS Dealer stuff.
            CustomEntityType.register();
            try {
                loadDealers();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        {//ECON
            if (!setupEconomy()) {
                getLogger().log(Level.SEVERE, "Vault is not enabled or found. Gamblr disabling.");
                getServer().getPluginManager().disablePlugin(getInstance());
            }
            new Bank();
        }
        {//Commands
            getCommand("Gamblr").setExecutor(new GamblrCommand());
            new CreateCommand();
            new GUICommand();
            new HelpCommand();
            new ItemCommand();
            new LoreCommand();
        }
        {//LISTENERS
            getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        }

    }

    @Override
    public void onDisable() {

        {//DEALER stuff.
            try {
                //saveDealers();
            } catch (Exception e) {
                System.out.println(e);
            }
            for (NPCDealer dealer : NPCDealer.getDealers()) {
                dealer.getWorld().removeEntity(dealer);
                dealer.die();
            }
        }
    }

    public static Gamblr getInstance() {
        return inst;
    }

    public static Economy getEconomy() {
        return econ;
    }

    /**
     * Hooks Vault
     *
     * @return - True if Vault is here, false if not.
     */
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            System.out.println("Vault is null");
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            System.out.println("rsp is null.");
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    /**
     * Saves dealers into a YML file.
     *
     * @throws IOException - Thrown if I cannot create or save the file.
     */
    private void saveDealers() throws IOException {
        File dealersFile = new File(getDataFolder(), "dealers.yml");
        if (!dealersFile.exists()) {
            dealersFile.createNewFile();
        }

        FileConfiguration dc = new YamlConfiguration().loadConfiguration(dealersFile);
        System.out.println("Writing dealers.yml");
        for (int i = 0; i <= NPCDealer.getDealers().size(); i++) {
            NPCDealer dealer = NPCDealer.getDealers().get(i);
            dc.set(i + ".NAME", dealer.getCustomName());
            dc.set(i + ".LOCATION", LocationUtil.parseLocation(dealer.getBukkitEntity().getLocation()));
            dc.set(i + ".INV_SIZE", dealer.getGui().getSlots());
            dc.set(i + ".GUI", dealer.getGui().toString());
        }
        dc.save(dealersFile);
    }

    public void saveDealer(NPCDealer dealer) throws IOException {
        File dealersFile = new File(getDataFolder(), "dealers.yml");
        if (!dealersFile.exists()) {
            dealersFile.createNewFile();
        }
        FileConfiguration dc = new YamlConfiguration().loadConfiguration(dealersFile);
        System.out.println("Writing dealers.yml");
        int i = dc.getKeys(false).size();
        dc.set(i + ".NAME", dealer.getCustomName());
        dc.set(i + ".LOCATION", LocationUtil.parseLocation(dealer.getBukkitEntity().getLocation()));
        dc.set(i + ".INV_SIZE", dealer.getGui().getSlots());
        dc.set(i + ".GUI", dealer.getGui().toString());

        dc.save(dealersFile);
    }

    /**
     * Load the dealers from the YML file.
     *
     * @return - And ArrayList of Dealers.
     * @throws IOException    - Thrown if the file cannot be created.
     * @throws ParseException - Thrown if the GUI is not valid JSON.
     */
    private ArrayList<NPCDealer> loadDealers() throws IOException, ParseException {
        ArrayList<NPCDealer> dealers = new ArrayList<>();
        File dealersFile = new File(getDataFolder(), "dealers.yml");
        if (!dealersFile.exists()) {
            dealersFile.createNewFile();
        }
        FileConfiguration dc = new YamlConfiguration().loadConfiguration(dealersFile);
        for (String s : dc.getKeys(false)) {

            String NPCNAME = dc.getString(s + ".NAME");
            String location = dc.getString(s + ".LOCATION");
            int inv_size = dc.getInt(s + ".INV_SIZE");
            String gui = dc.getString(s + ".GUI");
            JsonObject jso = new JsonObject();
            GUI g = Gamblr.gson.fromJson(gui, GUI.class);
            Location l = LocationUtil.parseString(location);

            NPCDealer dealer = (NPCDealer) CustomEntityType.NPCDEALER.spawn(l);
            dealer.setGUI(g);
            dealer.setCustomName(NPCNAME);

        }

        return dealers;
    }
}
