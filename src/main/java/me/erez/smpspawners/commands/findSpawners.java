package me.erez.smpspawners.commands;

import me.erez.smpspawners.CustomSpawner;
import me.erez.smpspawners.Main;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class findSpawners implements CommandExecutor {
    private Main plugin;

    public findSpawners(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("findSpawners").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "You do not have a permission to use this command");
            return true;
        }

        List<World> worlds = Bukkit.getWorlds();

        for (World world : worlds){

            for (Chunk chunk : world.getLoadedChunks()){

                for (BlockState state : chunk.getTileEntities()) {
                    if (state instanceof CreatureSpawner) {
                        CreatureSpawner spawner = (CreatureSpawner)state;
                        Location location = spawner.getLocation();
                        CustomSpawner customSpawner = new CustomSpawner(location, spawner.getSpawnedType());
                        player.sendMessage("found a spawner");
                        customSpawner.initialize();
                        Main.CustomSpawners.add(customSpawner);
                    }

                }

            }

        }


//        Chunk chunk = player.getLocation().getChunk();
//
//        Map<EntityType, Integer> mapa = getSpawners(chunk);
//        for (EntityType type : mapa.keySet()){
//            player.sendMessage(type.name() + ": " + mapa.get(type));
//        }


        return true;

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