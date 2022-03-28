package me.wyvern.warper.items;

import me.wyvern.warper.enchants.Ec;
import me.wyvern.warper.util.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemManager {
    public static HashMap <String, ItemStack> ItemMap = new HashMap<>();

    public static ItemStack warper;

    
    
    public static void init() {
        createWarper();
        ItemMap.put("WARPER", warper);
    }

    private static void createWarper() {
        ItemStack i = new ItemStack(Material.STICK);
        ItemMeta m = i.getItemMeta();
        m.setDisplayName(Color.colorize("&#51fb27W&#73f62ea&#96f035r&#b8eb3cp&#dbe543e&#fde04ar"));
        m.addEnchant(Ec.glow, 4921, true);
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        m.setLore(new ArrayList <String>() {
            {
                this.add(Color.colorize("&8Testing"));
            }
        });
        i.setItemMeta(m);
        warper = i;
    }
}
