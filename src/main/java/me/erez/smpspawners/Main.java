package me.erez.smpspawners;

import me.erez.smpspawners.Files.DataManager;
import me.erez.smpspawners.Utilities.Utils;
import me.erez.smpspawners.commands.*;
import me.erez.smpspawners.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {

    public static ArrayList<CustomSpawner> CustomSpawners = new ArrayList<>();
    public static DataManager dataManager;
    public static double frequency = 20;
    public static int producingTask;

    @Override
    public void onEnable() {
        //Commands:
        new findSpawners(this);
        new setFrequency(this);
        new frequency(this);

        //listeners
        new RightClickingSpawner(this);
        new MobSpawnByASpawner(this);
        new SpawnerPickUp(this);
        new PlacingSpawner(this);
        new Inventoryclick(this);

        producingTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {

            //Bukkit.broadcastMessage("produced");
            for (CustomSpawner customSpawner : Main.CustomSpawners){
                customSpawner.produceSpawn();
            }

        }, 0, (long) frequency * 20);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            save();
            CustomSpawners.clear();
            loadYML();
        }, 6000, 6000);

        //YMLs:
        dataManager = new DataManager(this);
        loadYML();
        this.saveDefaultConfig();
        frequency = this.getConfig().getDouble("frequency");
    }

    @Override
    public void onDisable() {
        save();
    }


    public static void loadYML(){
        try {
            dataManager.reloadConfig();
            for (String uuid : dataManager.getConfig().getConfigurationSection("spawners").getKeys(false)) {
                CustomSpawner customSpawner = new CustomSpawner(uuid);
                customSpawner.readFromYML();
                customSpawner.initialize();
                CustomSpawners.add(customSpawner);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void save(){
        dataManager.reloadConfig();

        try{dataManager.getConfig().set("spawners", null);} catch (Exception ignored){}

        for (CustomSpawner customSpawner : CustomSpawners)
            customSpawner.writeToYML();

        dataManager.saveConfig();
        CustomSpawners.clear();
    }


    public static CustomSpawner findSpawnerWithLocation(Location location){

        for (CustomSpawner customSpawner : CustomSpawners){
            if (Utils.Locations.locationsEqual(customSpawner.getLocation(), location)) {
                //Bukkit.broadcastMessage(customSpawner.toString());
                return customSpawner;
            }
        }


        return null;
    }
    public static CustomSpawner findSpawnerWithUUID(String uuid){

        for (CustomSpawner customSpawner : CustomSpawners){
            if (customSpawner.getUuid().equals(uuid)) {
                //Bukkit.broadcastMessage(customSpawner.toString());
                return customSpawner;
            }
        }


        return null;
    }

//    public static void checkForDuplicates(){
//        for (int i = 0; i < CustomSpawners.size(); i++) {
//            CustomSpawner sampleSpawner = CustomSpawners.get(i);
//            for (CustomSpawner customSpawner : CustomSpawners){
//                if (Utils.Locations.locationsEqual(sampleSpawner.getLocation(), customSpawner.getLocation()))
//                    if (sampleSpawner.getUuid().equals(customSpawner.getUuid()))
//                        CustomSpawners.remove(customSpawner);
//            }
//        }
//    }



}
