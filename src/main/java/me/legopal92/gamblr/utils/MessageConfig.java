package me.legopal92.gamblr.utils;

import com.google.common.collect.Maps;
import me.legopal92.gamblr.Gamblr;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Map;

/**
 * Created by legop on 4/21/2016.
 */
public class MessageConfig {

    public static Map<String, String> messages = Maps.newHashMap();
    private FileConfiguration config;

    public static String LOSE = "I'm sorry, you have lost, better luck next time!";
    public static String WIN = "Congratulations! You have won: %MONEY%";

    public MessageConfig() throws Exception{
        File f = new File(Gamblr.getInstance().getDataFolder(), "messages.yml");
        if (!f.exists()){
            f.createNewFile();
        }

        config = YamlConfiguration.loadConfiguration(f);
        config.addDefault("win", "Congratulations! You have won: %MONEY%");
        config.addDefault("lose", "I'm sorry, you have lost, better luck next time!");
        if (!config.isSet("win")){
            config.options().copyDefaults(true);
        }
        config.save(f);
        load();
    }

    public void load(){
        LOSE = config.getString("lose");
        WIN = config.getString("win");
    }
}
