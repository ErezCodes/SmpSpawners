package me.erez.smpspawners.listeners;

import me.erez.smpspawners.CustomSpawner;
import me.erez.smpspawners.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RightClickingSpawner implements Listener {
    private Main plugin;

    public RightClickingSpawner(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }



    @EventHandler
    public void rightClickOnASpawner(PlayerInteractEvent event) {

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

        if (!event.getClickedBlock().getType().equals(Material.SPAWNER)) return;

        CustomSpawner customSpawner = Main.findSpawnerWithLocation(event.getClickedBlock().getLocation());
        customSpawner.openGUI(event.getPlayer());


    }




}