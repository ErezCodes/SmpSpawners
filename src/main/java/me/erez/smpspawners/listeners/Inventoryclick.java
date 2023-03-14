package me.erez.smpspawners.listeners;

import me.erez.smpspawners.CustomSpawner;
import me.erez.smpspawners.Main;
import me.erez.smpspawners.Utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Inventoryclick implements Listener {
    private Main plugin;

    public Inventoryclick(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }



    @EventHandler
    public void click(InventoryClickEvent event) {


        if (event.getView().getTitle().split("§").length != 1)
            return;

        if (event.getView().getTitle().contains("spawner stash"))
            event.setCancelled(true);

        ItemStack itemStack = event.getCurrentItem();
        ItemMeta meta = itemStack.getItemMeta();
        if (!meta.hasLore()) return;
        String lore = meta.getLore().get(0);
        lore = ChatColor.stripColor(lore);
        if (!Utils.Strings.isValidUUID(lore)) return;
        CustomSpawner customSpawner = Main.findSpawnerWithUUID(lore);
        if (customSpawner == null)
            return;

        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getView().getPlayer();
        Inventory playerInventory = player.getInventory();

        if (playerInventory.firstEmpty() == -1) return;

        Material material = itemStack.getType();
        int amount = itemStack.getAmount();
        playerInventory.addItem(new ItemStack(material, amount));
        inventory.setItem(event.getSlot(), null);
        if (material.equals(customSpawner.getMat1())) {
            customSpawner.setStash1(customSpawner.getStash1() - amount);
            customSpawner.setMaxed1(false);
        }
        else {
            customSpawner.setStash2(customSpawner.getStash2() - amount);
            customSpawner.setMaxed2(false);
        }


    }




}