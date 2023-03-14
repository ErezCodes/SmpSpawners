package me.erez.smpspawners.listeners;

import me.erez.smpspawners.CustomSpawner;
import me.erez.smpspawners.Main;
import me.erez.smpspawners.Utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.ArrayList;

public class SpawnerPickUp implements Listener {
    private Main plugin;

    public SpawnerPickUp(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }



    @EventHandler
    public void blockBreak(BlockBreakEvent event) {

        if (!event.getBlock().getType().equals(Material.SPAWNER)) return;
        Location location = event.getBlock().getLocation();


        for (CustomSpawner customSpawner : Main.CustomSpawners){
            if (Utils.Locations.locationsEqual(location, customSpawner.getLocation())) {

                EntityType type = customSpawner.getType();

                ArrayList<String> lore = new ArrayList<>();
                String mat1 = Utils.Strings.normalizeSpigotText(customSpawner.getMat1().name());
                lore.add(ChatColor.LIGHT_PURPLE + (mat1 + ": " + customSpawner.getStash1()));
                if (customSpawner.getStash2() != -1){
                    String mat2 = Utils.Strings.normalizeSpigotText(customSpawner.getMat2().name());
                    lore.add(ChatColor.LIGHT_PURPLE + (mat2 + ": " + customSpawner.getStash2()));
                }

                ItemStack mobSpawner = Utils.Items.createGuiItemComplex(Material.SPAWNER,
                        ChatColor.DARK_PURPLE + Utils.Strings.normalizeSpigotText(type.name() + " Spawner"), lore);
                Utils.Spawners.setSpawnerEntityType(mobSpawner, type);

                Main.CustomSpawners.remove(customSpawner);
                location.getWorld().dropItemNaturally(location, mobSpawner);
            }
        }

        event.setExpToDrop(0);

    }


    @EventHandler
    public void blockExplode(EntityExplodeEvent event){
        for (Block exploded : event.blockList()){
            if (exploded.getType().equals(Material.SPAWNER)){
                Location location = exploded.getLocation();
                for (CustomSpawner customSpawner : Main.CustomSpawners){
                    if (Utils.Locations.locationsEqual(location, customSpawner.getLocation())) {

                        EntityType type = customSpawner.getType();

                        ArrayList<String> lore = new ArrayList<>();
                        String mat1 = Utils.Strings.normalizeSpigotText(customSpawner.getMat1().name());
                        lore.add(ChatColor.LIGHT_PURPLE + (mat1 + ": " + customSpawner.getStash1()));
                        if (customSpawner.getStash2() != -1){
                            String mat2 = Utils.Strings.normalizeSpigotText(customSpawner.getMat2().name());
                            lore.add(ChatColor.LIGHT_PURPLE + (mat2 + ": " + customSpawner.getStash2()));
                        }

                        ItemStack mobSpawner = Utils.Items.createGuiItemComplex(Material.SPAWNER,
                                ChatColor.DARK_PURPLE + Utils.Strings.normalizeSpigotText(type.name() + " Spawner"), lore);
                        Utils.Spawners.setSpawnerEntityType(mobSpawner, type);

                        Main.CustomSpawners.remove(customSpawner);
                        location.getWorld().dropItemNaturally(location, mobSpawner);
                    }
                }
            }
        }
    }




}