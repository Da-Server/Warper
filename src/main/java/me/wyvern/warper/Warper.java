package me.wyvern.warper;

import me.wyvern.warper.enchants.Ec;
import me.wyvern.warper.commands.GetCommand;
import me.wyvern.warper.enchants.Glow;
import me.wyvern.warper.events.Events;
import me.wyvern.warper.items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;

public final class Warper extends JavaPlugin {

    public static FileConfiguration warps;

    public static Warper instance;
    public static double[][] points;
    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginCommand("get").setExecutor(new GetCommand());
        Bukkit.getPluginCommand("get").setTabCompleter(new GetCommand());
        ItemManager.init();
        Bukkit.getPluginManager().registerEvents(new Events(), this);
        registerGlow();
        int rad = 10;
        int particleCount = 50;
        int currentPos = 0;
        points = new double[(int)(Math.pow(particleCount, 2)*2+particleCount)][];
        for (double i = 0;i <= Math.PI;i += Math.PI / particleCount) {
            double radius = Math.sin(i) * rad;
            double y = Math.cos(i) * rad;
            for (double a = 0;a < Math.PI * 2;a += Math.PI / particleCount) {
                double x = Math.cos(a) * radius;
                double z = Math.sin(a) * radius;
                points[currentPos] = new double[]{x,y,z};
                currentPos++;
            }
        }

        initializeWarps();

        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glow glow = Ec.glow;
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void initializeWarps() {
        File warpFile = new File(getDataFolder(), "warps.yml");
        if(!warpFile.exists()) {
            warpFile.getParentFile().mkdirs();
            saveResource("warps.yml", false);
        }
        warps = new YamlConfiguration();
        try {
            warps.load(warpFile);
        } catch(Exception e) {

        }
        warps.options().copyDefaults(true);
        saveResource("warps.yml", false);
    }
}
