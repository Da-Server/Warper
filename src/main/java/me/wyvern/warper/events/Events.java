package me.wyvern.warper.events;

import me.wyvern.warper.Warper;
import me.wyvern.warper.api.guis.WarpGUI;
import me.wyvern.warper.items.ItemManager;
import me.wyvern.warper.util.Color;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Events implements Listener {

    public static ArrayList <UUID> delay = new ArrayList <>();






    private ArrayList<Material> allowedBlocks = new ArrayList<Material>() {
        {
            add(Material.AIR);
            add(Material.VOID_AIR);
            add(Material.GRASS);
            add(Material.CAVE_AIR);
            add(Material.TALL_GRASS);
            add(Material.WATER);
        }
    };


    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player p = event.getPlayer();


        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null) {

                ItemStack it = event.getItem();
                if (it.equals(ItemManager.warper)) {
                    if (!delay.contains(p.getUniqueId())) {

                        p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 2, 1);
                        p.sendMessage(Color.colorize("&7Testing warp now"));
                        delay.add(p.getUniqueId());


                        WarpGUI ui = new WarpGUI();
                        int t = 0;
                        for(String sec : Warper.warps.getKeys(true)) {
                            double x, y, z;
                            String world,name;
                            x = Warper.warps.getConfigurationSection(sec).getDouble("x");
                            y = Warper.warps.getConfigurationSection(sec).getDouble("y");
                            z = Warper.warps.getConfigurationSection(sec).getDouble("z");
                            name = Warper.warps.getConfigurationSection(sec).getString("name");
                            world = Warper.warps.getConfigurationSection(sec).getString("world");
                            ItemStack i = Warper.warps.getConfigurationSection(sec).getItemStack("item").clone();
                            ItemMeta m = i.getItemMeta();
                            m.setDisplayName(Color.colorize(sec));
                            ArrayList<String> lore = new ArrayList <String>() {
                                {
                                    add(Color.colorize("&6X: " + x + " Y: " + y + " Z: " + z));
                                    add(Color.colorize(""));
                                    add(Color.colorize("&8This is a warp"));
                                }
                            };
                            m.setLore(lore);
                            i.setItemMeta(m);
                            ui.setItem(t, i);

                            t++;

                        }
                        p.openInventory(ui.getInventory());


                    }
                }
            }
        }
    }


    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent event) {
        Player p = (Player)event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase(new WarpGUI().getName())) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null) {
                ItemStack i = event.getCurrentItem();
                if (i.getItemMeta().getLore() != null) {
                    if (i.getItemMeta().getLore().contains(Color.colorize("&8This is a warp"))) {
                        if (!delay.contains(p.getUniqueId())) {
                            Location finalPlayerLocation = p.getLocation();
                            double x, y, z;
                            String world,name;
                            x = Warper.warps.getConfigurationSection(i.getItemMeta().getDisplayName()).getDouble("x");
                            y = Warper.warps.getConfigurationSection(i.getItemMeta().getDisplayName()).getDouble("y");
                            z = Warper.warps.getConfigurationSection(i.getItemMeta().getDisplayName()).getDouble("z");
                            name = Warper.warps.getConfigurationSection(i.getItemMeta().getDisplayName()).getString("name");
                            world = Warper.warps.getConfigurationSection(i.getItemMeta().getDisplayName()).getString("world");
                            ItemStack item = Warper.warps.getConfigurationSection(i.getItemMeta().getDisplayName()).getItemStack("item");


                            Collection <Entity> e = finalPlayerLocation.getWorld().getNearbyEntities(finalPlayerLocation, 10, 10, 10);
                            ArrayList <Player> nearbyPlayers = new ArrayList <>();
                            for (Entity entity: e) {
                                if (entity instanceof Player) {
                                    nearbyPlayers.add((Player)entity);
                                }
                            }
                            final int[] time = {0};
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (time[0] >= 10) {
                                        delay.remove(p.getUniqueId());
                                        for (Player p: nearbyPlayers) {
                                            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2, 1);
                                            p.teleport(new Location(Bukkit.getWorld(world), x, y, z));
                                        }
                                        cancel();
                                    }
                                    for (double[] pos: Warper.points) {
                                        double x1 = pos[0];
                                        double y1 = pos[1];
                                        double z1 = pos[2];
                                        if (allowedBlocks.contains(finalPlayerLocation.getBlock().getType())) {
                                            finalPlayerLocation.add(x1, y1, z1);
                                            finalPlayerLocation.getWorld().spawnParticle(Particle.SPELL_WITCH, finalPlayerLocation, 0);
                                            finalPlayerLocation.subtract(x1, y1, z1);
                                        }
                                    }
                                    time[0]++;
                                }
                            }.runTaskTimer(Warper.instance, 0, 20L);
                        }
                    }
                }
            }
        }
    }



}
