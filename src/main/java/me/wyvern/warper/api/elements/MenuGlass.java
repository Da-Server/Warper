package me.wyvern.warper.api.elements;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuGlass {
    public static ItemStack black;

    public static void init() {
        ItemStack temp = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta tempMeta = temp.getItemMeta();
        tempMeta.setDisplayName(" ");
        temp.setItemMeta(tempMeta);
        black = temp;

    }
}
