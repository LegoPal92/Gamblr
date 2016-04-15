package me.legopal92.Gamblr.GUI;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import me.legopal92.Gamblr.Gamblr;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by legop on 4/15/2016.
 */
public class ItemSerializer implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String name = obj.get("name").getAsString();
        Material type = Material.valueOf(obj.get("type").getAsString());
        Type listType = new TypeToken<List<String>>() {}.getType();
        List<String> lore = Gamblr.gson.fromJson(obj.get("lore"), listType);
        int amt = obj.get("amt").getAsInt();
        byte data = obj.get("data").getAsByte();

        ItemStack is = new ItemStack(type, amt);
        ItemMeta im = is.getItemMeta();
        im.setLore(lore);
        im.setDisplayName(name);
        is.setItemMeta(im);
        MaterialData md = is.getData();
        md.setData(data);
        is.setData(md);

        return is;
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jso = new JsonObject();
        jso.addProperty("name", src.getItemMeta().getDisplayName());
        jso.addProperty("type", src.getType().name());
        JsonArray lore = Gamblr.gson.toJsonTree(src.getItemMeta().getLore()).getAsJsonArray();
        jso.add("lore", lore);
        jso.addProperty("amt", src.getAmount());
        jso.addProperty("data", src.getData().getData());
        return jso;
    }
}
