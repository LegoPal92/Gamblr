package me.legopal92.gamblr.utils;

import com.google.common.collect.Lists;
import me.legopal92.gamblr.Gamblr;
import me.legopal92.gamblr.menu.Button;
import me.legopal92.gamblr.menu.GambleButton;
import me.legopal92.gamblr.menu.IconButton;
import me.legopal92.gamblr.menu.MainMenu;
import me.legopal92.gamblr.npc.CustomEntityType;
import me.legopal92.gamblr.npc.NPCDealer;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.List;

/**
 * Created by legop on 4/15/2016.
 */
public class DealerConfig {

    private static File dealerConfig;
    private static FileConfiguration config;

    public DealerConfig() throws Exception {
        dealerConfig = new File(Gamblr.getInstance().getDataFolder(), "dealers.yml");
        Gamblr.getInstance().getDataFolder().mkdirs();
        if (!dealerConfig.exists()){
            //dealerConfig.mkdir();
            dealerConfig.createNewFile();
        }
        config = YamlConfiguration.loadConfiguration(dealerConfig);
        config.set("Villagers.-1.default", "default");
        config.options().header("To add a potion effect to an item as a lose clause, you will add it in the item section.\n In the format that follows:\n  pe:\n    type: POTIONEFFECTTYPE\n    level: The level that you want.\n    duration: The length of time to last.\n    radius: The radius of the effect, will only affect players in that radius.\n\n\n To delete a dealer, simply remove that section of the config.\n\n\n Permissions: Gamblr.admin");
        config.save(dealerConfig);
    }

    public void set(NPCDealer dealer) {
        ConfigurationSection cs = config.getConfigurationSection("Villagers");
        int i = 0;
        if (cs.getKeys(false) != null){
            i = cs.getKeys(false).size();
        }
        config.set("Villagers." + i + ".name", dealer.getCustomName());
        config.set("Villagers." + i + ".invSize", dealer.getGui().getSize());
        config.set("Villagers." + i + ".invName", dealer.getGui().getName());
        config.set("Villagers." + i + ".location", LocationUtil.parseLocation(dealer.getBukkitEntity().getLocation()));

        for (Button b : dealer.getGui().getButtons()) {
            if (b instanceof GambleButton) {
                GambleButton gb = (GambleButton) b;
                config.set("Villagers." + i + ".items." + gb.getSlot() + ".BT", "GB");
                config.set("Villagers." + i + ".items." + gb.getSlot() + ".name", gb.getItemStack().getItemMeta().getDisplayName());
                config.set("Villagers." + i + ".items." + gb.getSlot() + ".amt", gb.getItemStack().getAmount());
                config.set("Villagers." + i + ".items." + gb.getSlot() + ".type", gb.getItemStack().getType().name());
                config.set("Villagers." + i + ".items." + gb.getSlot() + ".lore", gb.getItemStack().getItemMeta().getLore());
                config.set("Villagers." + i + ".items." + gb.getSlot() + ".data", gb.getItemStack().getData().getData());
                config.set("Villagers." + i + ".items." + gb.getSlot() + ".cost", gb.getCost());
                config.set("Villagers." + i + ".items." + gb.getSlot() + ".chance", gb.getChance());
                config.set("Villagers." + i + ".items." + gb.getSlot() + ".prize", gb.getPrize());
                config.set("Villagers." + i + ".items." + gb.getSlot() + ".effect", gb.getEffect());
                if (gb.getPotionEffect() != null){
                    config.set("Villagers." + i + ".items." + gb.getSlot() + ".pe.type", gb.getPotionEffect().getType().getName());
                    config.set("Villagers." + i + ".items." + gb.getSlot() + ".pe.level", gb.getPotionEffect().getAmplifier());
                    config.set("Villagers." + i + ".items." + gb.getSlot() + ".pe.duration", gb.getPotionEffect().getDuration());
                    config.set("Villagers." + i + ".items." + gb.getSlot() + ".pe.radius", gb.getRadius());
                }
                continue;
            } else if (b instanceof IconButton) {
                IconButton ib = (IconButton) b;
                config.set("Villagers." + i + ".items." + ib.getSlot() + ".BT", "IB");
                config.set("Villagers." + i + ".items." + ib.getSlot() + ".name", ib.getItemStack().getItemMeta().getDisplayName());
                config.set("Villagers." + i + ".items." + ib.getSlot() + ".amt", ib.getItemStack().getAmount());
                config.set("Villagers." + i + ".items." + ib.getSlot() + ".type", ib.getItemStack().getType().name());
                config.set("Villagers." + i + ".items." + ib.getSlot() + ".lore", ib.getItemStack().getItemMeta().getLore());
                config.set("Villagers." + i + ".items." + ib.getSlot() + ".data", ib.getItemStack().getData().getData());

            }else {
                continue;
            }
        }
        try {
            config.save(dealerConfig);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Button getButton(ConfigurationSection cs, int slot) {
        String t = cs.getString("BT");
        switch (t) {
            case "GB": {
                String name = ChatColor.translateAlternateColorCodes('&', cs.getString("name"));
                int amt = cs.getInt("amt");
                Material m = Material.getMaterial(cs.getString("type"));
                List<String> lore = cs.getStringList("lore");
                byte data = Byte.valueOf(cs.getString("data"));
                int cost = cs.getInt("cost");
                int chance = cs.getInt("chance");
                int prize = cs.getInt("prize");
                int radius = 0;
                Effect effect = Effect.valueOf(cs.getString("effect"));
                PotionEffect pe = null;
                if (cs.isSet("pe.type")){
                    PotionEffectType pet = PotionEffectType.getByName("pe.type");
                    int i = cs.getInt("pe.level");
                    int dur = cs.getInt("pe.duration");
                    pe = new PotionEffect(pet, dur, i);
                    radius = cs.getInt("pe.radius");
                }

                ItemStack is = new ItemStack(m, amt);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(name);
                im.setLore(lore);
                is.setItemMeta(im);
                MaterialData md = is.getData();
                md.setData(data);
                is.setData(md);

                GambleButton gb = new GambleButton(is, cost, chance, prize, radius, effect);

                gb.setSlot(slot);
                gb.setPotionEffect(pe);
                return gb;
            }
            case "IB": {
                String name = cs.getString("name");
                int amt = cs.getInt("amt");
                Material m = Material.getMaterial(cs.getString("type"));
                List<String> lore = cs.getStringList("lore");
                byte data = (byte) cs.get("data");
                ItemStack is = new ItemStack(m, amt);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(name);
                im.setLore(lore);
                is.setItemMeta(im);
                MaterialData md = is.getData();
                md.setData(data);
                is.setData(md);
                IconButton ib = new IconButton(is);
                ib.setSlot(slot);
                return ib;
            }
        }
        return null;
    }

    public List<NPCDealer> loadDealers(){
        List<NPCDealer> dealers = Lists.newArrayList();

        for (String s : config.getConfigurationSection("Villagers").getKeys(false)){
            if (s.equalsIgnoreCase("-1")){
                continue;
            }
            String name = ChatColor.translateAlternateColorCodes('&', config.getString("Villagers." + s + ".name"));
            int invSize = config.getInt("Villagers." + s + ".invSize");
            String invName = config.getString("Villagers." + s + ".invName");
            Location l = LocationUtil.parseString(config.getString("Villagers." + s + ".location"));
            ConfigurationSection css = config.getConfigurationSection("Villagers." + s + ".items");
            Button[] butts = new Button[invSize];
            for (String s2 : css.getKeys(false)){
                ConfigurationSection csss = css.getConfigurationSection(s2);
                Button b = getButton(csss, Integer.parseInt(s2));
                butts[Integer.parseInt(s2)] = b;
            }
            MainMenu mm = new MainMenu(invName, invSize);
            mm.setButtons(butts);
            mm.set(true);
            NPCDealer dealer = (NPCDealer)CustomEntityType.NPCDEALER.spawn(l);
            dealer.setCustomName(name);
            dealer.setGUI(mm);
            dealers.add(dealer);
        }

        return dealers;
    }

}
