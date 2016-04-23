package me.legopal92.gamblr.utils;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.List;
import java.util.UUID;

/**
 * Created by legop on 4/18/2016.
 */
public class ItemFactory {

    private UUID player;
    private String name;
    private int amt;
    private byte data;
    private List<String> lore;
    private Material m;

    public ItemFactory(UUID uuid){
        this.player = uuid;
    }

    public ItemFactory setName(String name){
        this.name = name;
        return this;
    }

    public ItemFactory setAmount(int amount){
        this.amt = amount;
        return this;
    }

    public ItemFactory setMaterial(Material m){
        this.m = m;
        return this;
    }

    public ItemFactory setData(byte data){
        this.data = data;
        return this;
    }

    public ItemFactory addLore(String s){
        if (lore == null){
            lore = Lists.newArrayList();
        }
        lore.add(s);
        return this;
    }

    public boolean hasLore(){
        return lore != null;
    }

    public int getAmount() { return amt; }

    public ItemStack build(){
        ItemStack is = new ItemStack(m, amt);
        MaterialData md = is.getData();
        md.setData(data);
        is.setData(md);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }


}
