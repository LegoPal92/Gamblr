package me.legopal92.Gamblr.NPC;

import me.legopal92.Gamblr.Gamblr;
import net.minecraft.server.v1_9_R1.EntityInsentient;
import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.ExceptionPlayerNotFound;
import net.minecraft.server.v1_9_R1.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by legop on 4/8/2016.
 */
public enum CustomEntityType {

    NPCDEALER("Villager", 120, NPCDealer.class);

    CustomEntityType(String name, int id, Class<? extends EntityInsentient> clazz){
        addToMaps(clazz, name, id);
    }

    /**
     * Spawn a customentity at a location.
     * @param location - The location to spawn at.
     * @return - The entity spawned.
     */
    public EntityLiving spawn(Location location) {
        World world = ((CraftWorld) location.getWorld()).getHandle();
        EntityLiving entity = null;
        switch (this) {
            case NPCDEALER:
                try{
                    entity = new NPCDealer(world);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            default:
                return null;
        }
        entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        ((CraftWorld) location.getWorld()).getHandle().addEntity(entity);
        return entity;
    }

    @SuppressWarnings("unchecked")
    /**
     * Add the custom mob to the maps.
     */
    private static void addToMaps(Class clazz, String name, int id) {
        ((Map) getPrivateField("d", net.minecraft.server.v1_9_R1.EntityTypes.class, null)).put(clazz, name);
        ((Map) getPrivateField("f", net.minecraft.server.v1_9_R1.EntityTypes.class, null)).put(clazz, id);
    }

    public static Object getPrivateField(String fieldName, Class clazz, Object object) {
        Field field;
        Object o = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static void register() {
        Logger logger = Gamblr.getInstance().getLogger();
        logger.info("Registered " + CustomEntityType.NPCDEALER.name() );

    }
}
