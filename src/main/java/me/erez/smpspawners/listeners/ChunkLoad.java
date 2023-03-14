package me.erez.smpspawners.listeners;

import me.erez.smpspawners.CustomSpawner;
import me.erez.smpspawners.Main;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class ChunkLoad implements Listener {
    private Main plugin;

    public ChunkLoad(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }



    @EventHandler
    public void chunkloadevent(ChunkLoadEvent event) {

        if (!event.isNewChunk()) return;

        Chunk chunk = event.getChunk();
        for (BlockState state : chunk.getTileEntities()) {
            if (state instanceof CreatureSpawner) {
                CreatureSpawner spawner = (CreatureSpawner)state;
                Location location = spawner.getLocation();
                CustomSpawner customSpawner = new CustomSpawner(location, spawner.getSpawnedType());
                customSpawner.initialize();
                Main.CustomSpawners.add(customSpawner);
            }

        }





    }

    public static Map<EntityType, Integer> getSpawners(Chunk chunk) {
        Map<EntityType, Integer> spawners = new EnumMap<>(EntityType.class);
        for (BlockState state : chunk.getTileEntities())
        {
            if (state instanceof CreatureSpawner)
            {
                CreatureSpawner spawner = (CreatureSpawner)state;
                spawners.put(spawner.getSpawnedType(), spawners.getOrDefault(spawner.getSpawnedType(), 0) + 1);
            }
        }
        return spawners;
    }

}