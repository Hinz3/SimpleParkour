package me.shadow5353.simpleparkour;

import java.util.ArrayList;
import java.util.HashMap;

import me.shadow5353.simpleparkour.listeners.CreateSigns;
import me.shadow5353.simpleparkour.listeners.PlayerDamage;
import me.shadow5353.simpleparkour.listeners.SignInteract;
import me.shadow5353.simpleparkour.managers.CommandManager;
import me.shadow5353.simpleparkour.managers.MessageManager;
import me.shadow5353.simpleparkour.managers.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleParkour extends JavaPlugin implements Listener{
	SettingsManager settings = SettingsManager.getInstance();

	public void onEnable(){
		getCommand("parkour").setExecutor(new CommandManager());

		settings.setup(this);
		Bukkit.getServer().getPluginManager().registerEvents(new CreateSigns(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SignInteract(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerDamage(), this);
	}

	public static Plugin getPlugin() {
		return Bukkit.getServer().getPluginManager().getPlugin("SimpleParkour");
	}

}
