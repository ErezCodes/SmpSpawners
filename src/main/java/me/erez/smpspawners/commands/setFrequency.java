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
import org.bukkit.scheduler.BukkitScheduler;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class setFrequency implements CommandExecutor {
    private Main plugin;

    public setFrequency(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("setFrequency").setExecutor(this);
    }

    BukkitScheduler bukkitScheduler = Bukkit.getScheduler();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "You do not have a permission to use this command");
            return true;
        }

        try{
            double value = Double.parseDouble(args[0]);
            if (value < 0.05){
                player.sendMessage(ChatColor.GOLD + "The minimum value is 0.05 seconds");
                return true;
            }
            Main.frequency = value;
            plugin.reloadConfig();
            plugin.getConfig().set("frequency", value);
            plugin.saveConfig();
            player.sendMessage(ChatColor.GREEN + "You have changed the spawners producing frequency to " + value + "s");

            bukkitScheduler.cancelTask(Main.producingTask);
            Main.producingTask = bukkitScheduler.scheduleSyncRepeatingTask(plugin, () -> {

                //Bukkit.broadcastMessage("produced");
                for (CustomSpawner customSpawner : Main.CustomSpawners){
                    customSpawner.produceSpawn();
                }

            }, 0, (long) value * 20);



        } catch (Exception e){
            player.sendMessage(ChatColor.RED + "Invalid number");
        }


        return true;

    }



}