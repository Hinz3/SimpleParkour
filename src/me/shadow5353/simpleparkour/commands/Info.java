package me.shadow5353.simpleparkour.commands;

import me.shadow5353.simpleparkour.SimpleParkour;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

/**
 * Created by Jacob on 08-05-2018.
 */
public class Info extends SimpleCommands {

    @Override
    public void onCommand(Player p, String[] args) {
        if (p.hasPermission("staffnotes.info")) {
            PluginDescriptionFile pdf = SimpleParkour.getPlugin().getDescription();

            p.sendMessage(ChatColor.GOLD + "---------------------------------------------");
            p.sendMessage(ChatColor.GOLD + "Name: " + ChatColor.YELLOW + "SimpleParkour");
            p.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.YELLOW + pdf.getVersion());
            p.sendMessage(ChatColor.GOLD + "Author: " + ChatColor.YELLOW + "shadow5353");
            p.sendMessage(ChatColor.GOLD + "Description: " + ChatColor.YELLOW + pdf.getDescription());
            p.sendMessage(ChatColor.GOLD + "---------------------------------------------");
        }
    }

    public Info() {
        super("Information about the plugin", "", "info");
    }
}
