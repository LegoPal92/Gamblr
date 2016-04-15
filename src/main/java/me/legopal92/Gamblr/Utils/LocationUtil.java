package me.legopal92.gamblr.utils;

import me.legopal92.gamblr.Gamblr;
import org.bukkit.Location;

/**
 * Created by legop on 4/12/2016.
 */
public class LocationUtil {


    /**
     * Parses the location into a string. Usable in configurations, and the lot.
     * @param l - The location to parse.
     * @return - A string representing the key components of the location. To recreate it.
     */
    public static String parseLocation(Location l){
        return l.getWorld().getName() + ":" + l.getX() + ":"
                + l.getY() + ":" + l.getZ() + ":" + l.getYaw() + ":" + l.getPitch();
    }

    /**
     * Parse the string into a location. Used after retrieving from configurations.
     * @param s - The serialized location string.
     * @return - The location.
     */
    public static Location parseString(String s){
        String[] parts = s.split(":");
        String worldName = parts[0];
        String x = parts[1];
        String y = parts[2];
        String z = parts[3];
        String yaw = parts[4];
        String pitch = parts[5];
        return new Location(Gamblr.getInstance().getServer().getWorld(worldName), Double.parseDouble(x), Double.parseDouble(y),
                Double.parseDouble(z), Float.parseFloat(yaw), Float.parseFloat(pitch));
    }

}
