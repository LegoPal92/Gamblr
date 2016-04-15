package me.legopal92.gamblr.npc;

import me.legopal92.gamblr.Gamblr;
import net.minecraft.server.v1_9_R1.EntityInsentient;
import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by legop on 4/8/2016.
 */
public enum CustomEntityType {

    NPCDEALER("Villager", 120, NPCDealer.class);

    private static final MetadataValue FIXED = new FixedMetadataValue(Gamblr.getInstance(), "");

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
        EntityLiving entity;
        switch (this) {
            case NPCDEALER:
                entity = new NPCDealer(world);
                break;
            default:
                entity = new NPCDealer(world);
                break;
        }
        entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        world.addEntity(entity);
        entity.getBukkitEntity().setMetadata("nodamage", FIXED);
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
