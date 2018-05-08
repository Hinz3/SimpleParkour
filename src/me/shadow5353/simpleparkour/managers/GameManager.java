package me.shadow5353.simpleparkour.managers;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jacob on 07-05-2018.
 */
public class GameManager {
    private ArrayList<Player> players = new ArrayList<>();

    private static HashMap<String, ItemStack[]> armourContents = new HashMap<>();
    private static HashMap<String, ItemStack[]> inventoryContents = new HashMap<>();
    private static HashMap<String, Location> locations = new HashMap<>();
    private static HashMap<String, Integer> xplevel = new HashMap<>();

    private GameManager() { }

    static GameManager instance = new GameManager();

    public static GameManager getInstance() {
        return instance;
    }

    public boolean getPlayer(Player player) {
        return players.contains(player);
    }

    public void addPlayer(Player player) {
        if (!(getPlayer(player))) {
            players.add(player);

            armourContents.put(player.getName(), player.getInventory().getArmorContents()); //save armour
            inventoryContents.put(player.getName(), player.getInventory().getContents());//save Inventory
            locations.put(player.getName(), player.getLocation()); //save the Location
            xplevel.put(player.getName(), player.getLevel()); //save the xp level
            player.getInventory().clear(); //just clearing the inventory. Optional line.

            player.setGameMode(GameMode.SURVIVAL);

            ItemStack returnItem = new ItemStack(Material.EMERALD, 1);
            ItemMeta returnItemMeta = returnItem.getItemMeta();
            returnItemMeta.setDisplayName(ChatColor.GOLD + "Return to checkpoint");

            ArrayList<String> loreReturnItem = new ArrayList<>();

            loreReturnItem.add(ChatColor.DARK_PURPLE + "Right click to return to your checkpoint");

            returnItemMeta.setLore(loreReturnItem);
            returnItem.setItemMeta(returnItemMeta);

            ItemStack endItem = new ItemStack(Material.REDSTONE, 1);
            ItemMeta endItemMeta = returnItem.getItemMeta();
            endItemMeta.setDisplayName(ChatColor.GOLD + "Leave Course");

            ArrayList<String> loreEndItem = new ArrayList<>();

            loreReturnItem.add(ChatColor.DARK_PURPLE + "Right click to leave course");

            endItemMeta.setLore(loreEndItem);
            endItem.setItemMeta(endItemMeta);

            player.getInventory().addItem(returnItem);
            player.getInventory().addItem(endItem);
        }
     }

     public void removePlayer(Player player) {
        if (getPlayer(player)) {
            players.remove(player);

            player.getInventory().clear();

            // Restore Inventory

            player.getInventory().clear(); //We will once again clear the inventory
            player.teleport(locations.get(player.getName())); //Teleport to the location

            player.getInventory().setContents(inventoryContents.get(player.getName())); //restore inventory contents
            player.getInventory().setArmorContents(armourContents.get(player.getName())); //restore armour contents
            player.setLevel(xplevel.get(player.getName())); //restor XP Level

            xplevel.remove(player.getName());
            locations.remove(player.getName());
            armourContents.remove(player.getName());
            inventoryContents.remove(player.getName());
        }
     }
}
