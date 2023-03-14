package me.erez.smpspawners;

import me.erez.smpspawners.Files.DataManager;
import me.erez.smpspawners.Utilities.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class CustomSpawner {

    //Maybe I need to put = new Random(); not in the instructor

    private Random random = new Random();

    private Location location;
    private EntityType type;

    private Material mat1;
    private Material mat2;

    private int stash1 = 0;
    private int stash2 = 0;

    private int maxDrop1;
    private int maxDrop2;

    private boolean maxed1 = false;
    private boolean maxed2 = false;

    private String uuid = null;




    public CustomSpawner(Location location, EntityType type) {
        this.location = location;
        this.type = type;
    }

    public CustomSpawner(String uuid){
        this.uuid = uuid;
    } //from spawners.yml

    public CustomSpawner(Location location, EntityType type, int stash1, int stash2){
        this.location = location;
        this.type = type;
        this.stash1 = stash1;
        this.stash2 = stash2;
    } //placed spawner



    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public EntityType getType() {
        return type;
    }
    public void setType(EntityType type) {
        this.type = type;
    }
    public Material getMat1() {
        return mat1;
    }
    public void setMat1(Material mat1) {
        this.mat1 = mat1;
    }
    public Material getMat2() {
        return mat2;
    }
    public void setMat2(Material mat2) {
        this.mat2 = mat2;
    }
    public int getStash1() {
        return stash1;
    }
    public void setStash1(int stash1) {
        this.stash1 = stash1;
    }
    public int getStash2() {
        return stash2;
    }
    public void setStash2(int stash2) {
        this.stash2 = stash2;
    }
    public int getMaxDrop1() {
        return maxDrop1;
    }
    public void setMaxDrop1(int maxDrop1) {
        this.maxDrop1 = maxDrop1;
    }
    public int getMaxDrop2() {
        return maxDrop2;
    }
    public void setMaxDrop2(int maxDrop2) {
        this.maxDrop2 = maxDrop2;
    }
    public boolean isMaxed1() {
        return maxed1;
    }
    public void setMaxed1(boolean maxed1) {
        this.maxed1 = maxed1;
    }
    public boolean isMaxed2() {
        return maxed2;
    }
    public void setMaxed2(boolean maxed2) {
        this.maxed2 = maxed2;
    }
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }




    public void produceSpawn(){

        if (maxed1 && maxed2)
            return;

        int mobAmount = random.nextInt(3) + 1;

        for (int i = 0; i < mobAmount; i++)
            stash1 += random.nextInt(maxDrop1);

        if (stash2 != -1)
            for (int i = 0; i < mobAmount; i++)
                stash2 += random.nextInt(maxDrop2);

        checkCap();


    }

    public void initialize(){

        checkCap();
        if (uuid == null)
            uuid = UUID.randomUUID().toString();

        if (type.equals(EntityType.COW)){
            mat1 = Material.BEEF;
            maxDrop1 = 4;
            mat2 = Material.LEATHER;
            maxDrop2 = 3;
            return;
        }
        if (type.equals(EntityType.ZOMBIE)){
            mat1 = Material.ROTTEN_FLESH;
            stash2 = -1;
            maxDrop1 = 2;
            maxDrop2 = -1;
            maxed2 = true;
            return;
        }
        if (type.equals(EntityType.BLAZE)){
            mat1 = Material.BLAZE_ROD;
            stash2 = -1;
            maxDrop1 = 1;
            maxDrop2 = -1;
            maxed2 = true;
            return;
        }
        if (type.equals(EntityType.SKELETON)){
            mat1 = Material.BONE;
            mat2 = Material.ARROW;
            maxDrop1 = 3;
            maxDrop2 = 3;
            return;
        }
        if (type.equals(EntityType.IRON_GOLEM)){
            mat1 = Material.IRON_INGOT;
            stash2 = -1;
            maxDrop1 = 4;
            maxed2 = true;
            return;
        }
        if (type.equals(EntityType.MAGMA_CUBE)){
            mat1 = Material.MAGMA_CREAM;
            stash2 = -1;
            maxDrop1 = 3;
            maxed2 = true;
            return;
        }
        if (type.equals(EntityType.SPIDER) || type.equals(EntityType.CAVE_SPIDER)){
            mat1 = Material.STRING;
            mat2 = Material.SPIDER_EYE;
            maxDrop1 = 3;
            maxDrop2 = 2;
            return;
        }



    }

    public void checkCap(){

        if (stash2 == -1){
            if (stash1 >= 3456) {
                stash1 = 3456;
                maxed1 = true;
            }
            return;
        }

        if (stash1 >= 1728) {
            stash1 = 1728;
            maxed1 = true;
        }
        if (stash2 >= 1728) {
            stash2 = 1728;
            maxed2 = true;
        }


    }

    public void openGUI(Player player){

        //ChatColor blendedColor = ChatColor.of(new Color(198, 198, 198, 100));
        String title = Utils.Strings.normalizeSpigotText(ChatColor.DARK_PURPLE + Utils.Strings.normalizeSpigotText(type.name()) + " spawner stash");
        Inventory inventory = Bukkit.createInventory(null, 54, title);

        ItemStack itemStack = new ItemStack(mat1, 64);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Arrays.asList(ChatColor.BLACK + uuid));
        itemStack.setItemMeta(meta);


        if (stash2 == -1){

            int times = stash1 / 64;
            if (times != 54){
                ItemStack leftover = itemStack.clone();
                leftover.setAmount(stash1 % 64);
                inventory.setItem(times, leftover);
            }

            for (int i = 0; i < times; i++)
                inventory.setItem(i, itemStack);


            player.openInventory(inventory);
            return;

        }





        int times = stash1 / 64;


        if (times != 27){
            ItemStack leftover = itemStack.clone();
            leftover.setAmount(stash1 % 64);
            inventory.setItem(times, leftover);
        }

        for (int i = 0; i < times; i++)
            inventory.setItem(i, itemStack);




        ItemStack itemStack2 = new ItemStack(mat2, 64);
        ItemMeta meta2 = itemStack2.getItemMeta();
        meta2.setLore(Arrays.asList(ChatColor.BLACK + uuid));
        itemStack2.setItemMeta(meta2);


        int times2 = (stash2/64) + 27;

        if (times2 != 54){
            ItemStack leftover2 = itemStack2.clone();
            leftover2.setAmount(stash2 % 64);
            inventory.setItem(times2, leftover2);
        }


        for (int i = 27; i < times2; i++)
            inventory.setItem(i, itemStack2);


        player.openInventory(inventory);


    }

    public void writeToYML(){

        DataManager dataManager = Main.dataManager;
        dataManager.reloadConfig();
        String path = "spawners." + uuid;


        dataManager.getConfig().set(path + ".world", location.getWorld().getName());
        dataManager.getConfig().set(path + ".x", location.getX());
        dataManager.getConfig().set(path + ".y", location.getY());
        dataManager.getConfig().set(path + ".z", location.getZ());
        dataManager.getConfig().set(path + ".type", type.toString());
        dataManager.getConfig().set(path + ".stash1", stash1);
        dataManager.getConfig().set(path + ".stash2", stash2);

        dataManager.saveConfig();

    }

    public void readFromYML(){

        DataManager dataManager = Main.dataManager;
        dataManager.reloadConfig();
        String path = "spawners." + uuid;

        String worldStr = dataManager.getConfig().getString(path + ".world");
        int x = dataManager.getConfig().getInt(path + ".x");
        int y = dataManager.getConfig().getInt(path + ".y");
        int z = dataManager.getConfig().getInt(path + ".z");

        location = new Location(Bukkit.getWorld(worldStr), x, y, z);
        type = EntityType.valueOf(dataManager.getConfig().getString(path + ".type"));
        stash1 = dataManager.getConfig().getInt(path + ".stash1");
        stash2 = dataManager.getConfig().getInt(path + ".stash2");

    }

    @Override
    public String toString() {
        return "CustomSpawner{" +
                "location=" + location +
                ", type=" + type +
                ", mat1=" + mat1 +
                ", mat2=" + mat2 +
                ", stash1=" + stash1 +
                ", stash2=" + stash2 +
                ", maxDrop1=" + maxDrop1 +
                ", maxDrop2=" + maxDrop2 +
                ", maxed1=" + maxed1 +
                ", maxed2=" + maxed2 +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
