package me.shadow5353.simpleparkour;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleParkour extends JavaPlugin{
	SettingsManager settings = SettingsManager.getInstance();
	MessageManager msg = MessageManager.getInstance();
	public void onEnable(){
		settings.setup(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(cmd.getName().equalsIgnoreCase("parkour")){
			if(!(sender.hasPermission("simpleparkour.use"))){
				msg.perm(sender);
				return true;
			}
			if(args.length == 0){
				sender.sendMessage(ChatColor.YELLOW + "-------------------------------------------");
				sender.sendMessage(ChatColor.GOLD + "/parkour" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Show a list of commands");
				sender.sendMessage(ChatColor.GOLD + "/parkour stats" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Show your parkour stats");
				sender.sendMessage(ChatColor.GOLD + "/parkour list" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Show a list parkour course");
				sender.sendMessage(ChatColor.GOLD + "/parkour join <name>" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Join a parkour course");
				sender.sendMessage(ChatColor.GOLD + "/parkour leave" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Leave a parkour course");
				sender.sendMessage(ChatColor.GOLD + "/parkour rest" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Resets your checkpoint");
				sender.sendMessage(ChatColor.GOLD + "/parkour return" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Return to your last checkpoint");
				if(sender.hasPermission("parkour.admin")){
					sender.sendMessage(ChatColor.YELLOW + "------------ " + ChatColor.BLACK + "[" + ChatColor.GOLD + "Admin commands" + ChatColor.BLACK + "]" + ChatColor.YELLOW + " ------------");
					sender.sendMessage(ChatColor.GOLD + "/parkour create <name>" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Create a parkour course");
					sender.sendMessage(ChatColor.GOLD + "/parkour remove <name>" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Remove a parkour course");
					sender.sendMessage(ChatColor.GOLD + "/parkour setstart <name>" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Set start location for a course");
					sender.sendMessage(ChatColor.GOLD + "/parkour setlobby" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Set lobby location");
					sender.sendMessage(ChatColor.GOLD + "/parkour reload" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Reloads the storage"); //Done
					}
				sender.sendMessage(ChatColor.YELLOW + "-------------------------------------------");
				
				return true;
			}
			if(args[0].equalsIgnoreCase("stats")){
				if(!(sender instanceof Player)){
					msg.error(sender, "Only players can use this command");
					return true;
				}
				Player p = (Player) sender;
				if(!(p.hasPermission("simpleparkour.stats"))){
					msg.perm(p);
					return true;
				}
				StringBuilder stbl = new StringBuilder();
				String uuid = p.getUniqueId().toString();
				
				p.sendMessage(ChatColor.YELLOW + "----------------" + ChatColor.BLACK + " [" + ChatColor.GOLD + "Stats" + ChatColor.BLACK + "] " + ChatColor.YELLOW + "----------------");
				p.sendMessage(ChatColor.GOLD + "Player name: " + Bukkit.getServer().getPlayer(settings.getStats().getString("stats." + uuid)));
				p.sendMessage(ChatColor.GOLD + "Course completed: " + settings.getStats().getInt("stats." + uuid + ".completed"));
				p.sendMessage(ChatColor.YELLOW + "-------------------------------------------");
				
				//Maybe more to add here
			}
			if(args[0].equalsIgnoreCase("list")){
				if(!(sender instanceof Player)){
					msg.error(sender, "Only players can use this command");
					return true;
				}
				Player p = (Player) sender;
				if(!(p.hasPermission("simpleparkour.list"))){
					msg.perm(p);
					return true;
				}
				//More to add here
			}
			if(args[0].equalsIgnoreCase("join")){
				if(!(sender instanceof Player)){
					msg.error(sender, "Only players can use this command");
					return true;
				}
				Player p = (Player) sender;
				if(!(p.hasPermission("simpleparkour.join"))){
					msg.perm(p);
					return true;
				}
				//More to add here
			}
			if(args[0].equalsIgnoreCase("leave")){
				if(!(sender instanceof Player)){
					msg.error(sender, "Only players can use this command");
					return true;
				}
				Player p = (Player) sender;
				if(!(p.hasPermission("simpleparkour.leave"))){
					msg.perm(p);
					return true;
				}
				//More to add here
			}
			if(args[0].equalsIgnoreCase("reset")){
				if(!(sender instanceof Player)){
					msg.error(sender, "Only players can use this command");
					return true;
				}
				Player p = (Player) sender;
				if(!(p.hasPermission("simpleparkour.reset"))){
					msg.perm(p);
					return true;
				}
				//More to add here
			}
			if(args[0].equalsIgnoreCase("return")){
				if(!(sender instanceof Player)){
					msg.error(sender, "Only players can use this command");
					return true;
				}
				Player p = (Player) sender;
				if(!(p.hasPermission("simpleparkour.return"))){
					msg.perm(p);
					return true;
				}
				//More to add here
			}
			if(args[0].equalsIgnoreCase("create")){
				if(!(sender instanceof Player)){
					msg.error(sender, "Only players can use this command");
					return true;
				}
				Player p = (Player) sender;
				if(!(p.hasPermission("simpleparkour.create"))){
					msg.perm(p);
					return true;
				}
				if(args.length == 1){
					msg.error(p, "Please specify a name!");
					return true;
				}
				
				String name = args[1];
				settings.getParkour().set("parkour.list", name);
				settings.getParkour().set("parkour." + name + ".name", name);
				settings.getParkour().set("parkour." + name + ".start" + ".world", p.getWorld().getName());
				settings.getParkour().set("parkour." + name + ".start" + ".x", p.getLocation().getX());
				settings.getParkour().set("parkour." + name + ".start" + ".y", p.getLocation().getY());
				settings.getParkour().set("parkour." + name + ".start" + ".z", p.getLocation().getZ());
				settings.getParkour().set("parkour." + name + ".start" + ".yaw", p.getLocation().getYaw());
				settings.getParkour().set("parkour." + name + ".start" + ".pitch", p.getLocation().getPitch());
				settings.saveParkour();
				
				msg.good(p, "Created parkour course " + name + "!");
			}
			
			if(args[0].equalsIgnoreCase("setstart")){
				if(!(sender instanceof Player)){
					msg.error(sender, "Only players can use this command");
					return true;
				}
				Player p = (Player) sender;
				if(!(p.hasPermission("simpleparkour.create"))){
					msg.perm(p);
					return true;
				}
				if(args.length == 1){
					msg.error(p, "Please specify a name!");
					return true;
				}
				
				String name = args[1];
				if(settings.getParkour().getString("parkour." + name + ".name") == null){
					msg.error(p, name + "does not exist");
					return true;
				}
				
				settings.getParkour().set("parkour." + name + ".start" + ".world", p.getWorld().getName());
				settings.getParkour().set("parkour." + name + ".start" + ".x", p.getLocation().getX());
				settings.getParkour().set("parkour." + name + ".start" + ".y", p.getLocation().getY());
				settings.getParkour().set("parkour." + name + ".start" + ".z", p.getLocation().getZ());
				settings.getParkour().set("parkour." + name + ".start" + ".yaw", p.getLocation().getYaw());
				settings.getParkour().set("parkour." + name + ".start" + ".pitch", p.getLocation().getPitch());
				settings.saveParkour();
				
				msg.good(p, "Saved the new start location for " + name + "!");
			}
			if(args[0].equalsIgnoreCase("remove")){
				if(!(sender instanceof Player)){
					msg.error(sender, "Only players can use this command");
					return true;
				}
				Player p = (Player) sender;
				if(!(p.hasPermission("simpleparkour.remove"))){
					msg.perm(p);
					return true;
				}
				if(args.length == 1){
					msg.error(p, "Please specify a name!");
					return true;
				}
				
				String name = args[1];
				if(settings.getParkour().getString("parkour." + name + ".name") == null){
					msg.error(p, name + "does not exist");
					return true;
				}
				settings.getParkour().set("parkour." + name + ".name", null);
				settings.getParkour().set("parkour." + name + ".start" + ".world", null);
				settings.getParkour().set("parkour." + name + ".start" + ".x", null);
				settings.getParkour().set("parkour." + name + ".start" + ".y", null);
				settings.getParkour().set("parkour." + name + ".start" + ".z", null);
				settings.getParkour().set("parkour." + name + ".start" + ".yaw", null);
				settings.getParkour().set("parkour." + name + ".start" + ".pitch", null);
				settings.saveParkour();
			}
			if(args[0].equalsIgnoreCase("reload")){
				Player p = (Player) sender;
				if(!(p.hasPermission("simpleparkour.reload"))){
					msg.perm(p);
					return true;
				}
				settings.reloadData();
				settings.reloadParkour();
				settings.reloadStats();
				
				msg.good(sender, "All files have been reloaded!");
				return true;
			}
		}
		return true;
	}

}
