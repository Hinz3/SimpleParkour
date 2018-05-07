package me.shadow5353.simpleparkour.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class SettingsManager {

       private SettingsManager() { }
      
       static SettingsManager instance = new SettingsManager();
      
       public static SettingsManager getInstance() {
               return instance;
       }
      
       Plugin p;
      
       FileConfiguration data;
       File dfile;
       
       FileConfiguration stats;
       File sfile;
       
       FileConfiguration parkour;
       File pfile;
      
       public void setup(Plugin p) {
              
               if (!p.getDataFolder().exists()) {
                       p.getDataFolder().mkdir();
               }
              
               dfile = new File(p.getDataFolder(), "checkpoints.yml");
              
               if (!dfile.exists()) {
                       try {
                               dfile.createNewFile();
                       }
                       catch (IOException e) {
                               Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create checkpoints.yml!");
                       }
               }
              
               data = YamlConfiguration.loadConfiguration(dfile);
               
               sfile = new File(p.getDataFolder(), "stats.yml");
               
               if (!sfile.exists()) {
                       try {
                               sfile.createNewFile();
                       }
                       catch (IOException e) {
                               Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create stats.yml!");
                       }
               }
              
               stats = YamlConfiguration.loadConfiguration(sfile);
               
               pfile = new File(p.getDataFolder(), "parkour.yml");
               
               if (!pfile.exists()) {
                       try {
                               pfile.createNewFile();
                       }
                       catch (IOException e) {
                               Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create parkour.yml!");
                       }
               }
              
               parkour = YamlConfiguration.loadConfiguration(pfile);
       }
      
       public FileConfiguration getData() {
               return data;
       }
       
       public FileConfiguration getStats() {
           return stats;
           }
      
       public FileConfiguration getParkour() {
           return parkour;
           }
       public void saveData() {
               try {
                       data.save(dfile);
               }
               catch (IOException e) {
                       Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save checkpoints.yml!");
               }
       }
       
       public void saveStats() {
           try {
                   stats.save(sfile);
           }
           catch (IOException e) {
                   Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save stats.yml!");
                   }
           }
       
       public void saveParkour() {
           try {
                   parkour.save(pfile);
           }
           catch (IOException e) {
                   Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save parkour.yml!");
                   }
           }
      
       public void reloadData() {
               data = YamlConfiguration.loadConfiguration(dfile);
               }
       
       public void reloadStats() {
    	   stats = YamlConfiguration.loadConfiguration(sfile);
           }
       
       public void reloadParkour() {
           parkour = YamlConfiguration.loadConfiguration(pfile);
           }
      
       public PluginDescriptionFile getDesc() {
               return p.getDescription();
       }
}