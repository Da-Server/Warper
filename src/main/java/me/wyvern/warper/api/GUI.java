package me.wyvern.warper.api;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class GUI implements InventoryHolder {

    int size;
    String name;

    Inventory i;

    public GUI(String name, int size) {
        i = Bukkit.createInventory(this, size, name);
    }


    @Override
    public Inventory getInventory() {
        return i;
    }

    public void setItem(int slot, ItemStack item) {
        i.setItem(slot, item);
    }

    public void fill(ItemStack item) {
        for(int i = 0; i < size -1; i++) {
            this.i.setItem(i, item);
        }
    }

    public String getName() {
        return name;
    }

}
