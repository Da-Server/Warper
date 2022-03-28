package me.wyvern.warper.commands;

import me.wyvern.warper.Warper;
import me.wyvern.warper.util.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetWarp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if(strings.length == 1) {
                String args = strings[0];
                int i= 0;
                if(Warper.warps.getStringList("warps").contains(args)) {
                    for(String str : Warper.warps.getKeys(true)) {
                        if(str.equalsIgnoreCase(args)) {
                            int x = p.getLocation().getBlockX();
                            int y = p.getLocation().getBlockY();
                            int z = p.getLocation().getBlockZ();
                            String world = p.getWorld().getName();
                            ItemStack item = p.getInventory().getItemInMainHand();
                            Warper.warps.getConfigurationSection(str).set("x", x);
                            Warper.warps.getConfigurationSection(str).set("y", y);
                            Warper.warps.getConfigurationSection(str).set("z", z);
                            Warper.warps.getConfigurationSection(str).set("world", world);
                            Warper.warps.getConfigurationSection(str).set("item", item);

                            p.sendMessage(Color.colorize("&cMADE WARP"));
                            break;

                        }
                        i++;
                    }
                } else {
                    Warper.warps.createSection(args);
                    int x = p.getLocation().getBlockX();
                    int y = p.getLocation().getBlockY();
                    int z = p.getLocation().getBlockZ();
                    String world = p.getWorld().getName();
                    ItemStack item = p.getInventory().getItemInMainHand();
                    Warper.warps.getConfigurationSection(args).set("x", x);
                    Warper.warps.getConfigurationSection(args).set("y", y);
                    Warper.warps.getConfigurationSection(args).set("z", z);
                    Warper.warps.getConfigurationSection(args).set("world", world);
                    Warper.warps.getConfigurationSection(args).set("item", item);

                    p.sendMessage(Color.colorize("&cMADE WARP"));

                }

            }
        }
        return false;
    }
}
