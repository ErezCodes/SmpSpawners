package me.erez.smpspawners.listeners;

import me.erez.smpspawners.CustomSpawner;
import me.erez.smpspawners.Main;
import me.erez.smpspawners.Utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PlacingSpawner implements Listener {
    private Main plugin;

    public PlacingSpawner(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }



    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {

        if (!event.getBlock().getType().equals(Material.SPAWNER)) return;

        Player player = event.getPlayer();
        ItemStack spawner;
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.SPAWNER))
            spawner = player.getInventory().getItemInOffHand();
        else spawner = player.getInventory().getItemInMainHand();


        Location location = event.getBlock().getLocation();
        EntityType type = Utils.Spawners.getSpawnerEntityType(spawner);
        ItemMeta meta = spawner.getItemMeta();

        CustomSpawner customSpawner;

        if (meta.hasLore()){
            ArrayList<String> lore = (ArrayList<String>) meta.getLore();
            int stash1 = Integer.parseInt(lore.get(0).split(":")[1].replaceFirst(" ", ""));
            int stash2 = -1;

            if (lore.size() == 2)
                stash2 = Integer.parseInt(lore.get(1).split(":")[1].replaceFirst(" ", ""));

            customSpawner = new CustomSpawner(location, type, stash1, stash2);
        }
        else
            customSpawner = new CustomSpawner(location, type);
        customSpawner.initialize();
        Main.CustomSpawners.add(customSpawner);

        //Bukkit.broadcastMessage(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().get(0));
    }




}