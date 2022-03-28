package me.wyvern.warper.commands;

import me.wyvern.warper.items.ItemManager;
import me.wyvern.warper.util.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GetCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("warper.get")) {
                if (args.length >= 1) {
                    String arg = args[0];
                    int i = 0;
                    for(String s : ItemManager.ItemMap.keySet()) {
                        if(arg.equalsIgnoreCase(s)) {
                            player.getInventory().addItem(ItemManager.ItemMap.get(s));
                            player.sendMessage(Color.colorize("Gave you " + ItemManager.ItemMap.get(s).getItemMeta().getDisplayName()));
                            break;
                        }
                        i++;
                    }
                    if(i >= 1) {
                        player.sendMessage(Color.colorize("&cNo item exists called " + arg));
                    }
                }
                else {
                    player.sendMessage(Color.colorize("&cNot enough arguments"));
                }
            } else {
                player.sendMessage(Color.colorize("&cNo permission"));
            }
        } else {
            sender.sendMessage(Color.colorize("&cYou need to be a player"));
        }
        return false;
    }

    @Override
    public List <String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList <>(ItemManager.ItemMap.keySet());
    }
}
