package me.erez.smpspawners.commands;

import me.erez.smpspawners.CustomSpawner;
import me.erez.smpspawners.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class frequency implements CommandExecutor {
    private Main plugin;

    public frequency(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("frequency").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        player.sendMessage("The current frequency of how often the spawners produce a spawn is " + Main.frequency + " seconds");


        return true;

    }



}