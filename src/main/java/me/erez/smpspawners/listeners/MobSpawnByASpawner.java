package me.erez.smpspawners.listeners;

import me.erez.smpspawners.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class MobSpawnByASpawner implements Listener {
    private Main plugin;

    public MobSpawnByASpawner(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }



    @EventHandler
    public void entitySpawned(CreatureSpawnEvent event) {

        if (!event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER)) return;

        event.setCancelled(true);





    }




}