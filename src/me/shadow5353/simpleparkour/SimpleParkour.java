package me.shadow5353.simpleparkour;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class SimpleParkour extends JavaPlugin implements Listener{
	SettingsManager settings = SettingsManager.getInstance();
	MessageManager msg = MessageManager.getInstance();
	ArrayList<Player> player = new ArrayList<Player>();
	private ArrayList<Player> jumpers = new ArrayList<Player>();
	public void onEnable(){
		settings.setup(this);
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(cmd.getName().equalsIgnoreCase("parkour")){
			if(!(sender.hasPermission("simpleparkour.use"))){
				msg.perm(sender);
				return true;
			}
			if(args.length == 0){
				sender.sendMessage(ChatColor.YELLOW + "--------------------------------------------------");
				sender.sendMessage(ChatColor.GOLD + "/parkour" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Show a list of commands"); //Done
				sender.sendMessage(ChatColor.GOLD + "/parkour stats" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Show your parkour stats");
				sender.sendMessage(ChatColor.GOLD + "/parkour list" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Show a list parkour course");
				sender.sendMessage(ChatColor.GOLD + "/parkour join <name>" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Join a parkour course"); //Done
				sender.sendMessage(ChatColor.GOLD + "/parkour leave" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Leave a parkour course"); //Done
				sender.sendMessage(ChatColor.GOLD + "/parkour lobby" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Leave a parkour course");
				sender.sendMessage(ChatColor.GOLD + "/parkour rest" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Resets your checkpoint"); //done
				sender.sendMessage(ChatColor.GOLD + "/parkour return" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Return to your last checkpoint"); //done
				if(sender.hasPermission("parkour.admin")){
					sender.sendMessage(ChatColor.YELLOW + "------------------ " + ChatColor.BLACK + "[" + ChatColor.GOLD + "Admin commands" + ChatColor.BLACK + "]" + ChatColor.YELLOW + " -----------------");
					sender.sendMessage(ChatColor.GOLD + "/parkour create <name>" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Create a parkour course"); //Done
					sender.sendMessage(ChatColor.GOLD + "/parkour remove <name>" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Remove a parkour course"); //Done
					sender.sendMessage(ChatColor.GOLD + "/parkour setstart <name>" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Set start location for a course"); //Done
					sender.sendMessage(ChatColor.GOLD + "/parkour setlobby" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Set lobby location"); //Done
					sender.sendMessage(ChatColor.GOLD + "/parkour reload" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Reloads the storage"); //Done
					}
				sender.sendMessage(ChatColor.YELLOW + "--------------------------------------------------");
				
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
				
				p.sendMessage(ChatColor.YELLOW + "------------------" + ChatColor.BLACK + " [" + ChatColor.GOLD + "Stats" + ChatColor.BLACK + "] " + ChatColor.YELLOW + "------------------");
				p.sendMessage(ChatColor.GOLD + "Player name: " + ChatColor.YELLOW + p.getName());
				p.sendMessage(ChatColor.GOLD + "Course played: " + ChatColor.YELLOW + settings.getStats().getInt("stats." + uuid + ".played"));
				p.sendMessage(ChatColor.GOLD + "Course completed: " + ChatColor.YELLOW + settings.getStats().getInt("stats." + uuid + ".completed"));
				p.sendMessage(ChatColor.GOLD + "Course left: " + ChatColor.YELLOW + settings.getStats().getInt("stats." + uuid + ".left"));
				p.sendMessage(ChatColor.YELLOW + "-------------------------------------------");
				return true;
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
				if(args.length == 1){
					msg.error(p, "Please specify a name!");
					return true;
				}
				if(player.contains(p)){
					msg.error(p, "You are already in a course!");
					return true;
				}
				String name = args[1];
				if(settings.getParkour().getString("parkour." + name + ".name") == null){
					msg.error(p, name + " do not exist!");
					return true;
				}
				player.add(p);
				World world = Bukkit.getServer().getWorld(settings.getParkour().getString("parkour." + name + ".start.world"));
				int x = settings.getParkour().getInt("parkour." + name + ".start.x");
				int y = settings.getParkour().getInt("parkour." + name + ".start.y");
				int z = settings.getParkour().getInt("parkour." + name + ".start.z");
				int yaw = settings.getParkour().getInt("parkour." + name + ".start.yaw");
				int pitch = settings.getParkour().getInt("parkour." + name + ".start.pitch");
				
				Location loc = new Location(world, x, y, z, yaw, pitch);
				p.teleport(loc);
				String uuid = p.getUniqueId().toString();
				settings.getStats().set("stats." + uuid + ".played", + 1);
				msg.good(p, "You have joined " + name + "!");
				return true;
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
				if(!(player.contains(p))){
					msg.error(p, "You are not in a parkour course");
					return true;
				}
				
				player.remove(p);
				
				World world = Bukkit.getServer().getWorld(settings.getParkour().getString("lobby.world"));
				int x = settings.getParkour().getInt("lobby.x");
				int y = settings.getParkour().getInt("lobby.y");
				int z = settings.getParkour().getInt("lobby.z");
				int yaw = settings.getParkour().getInt("lobby.yaw");
				int pitch = settings.getParkour().getInt("lobby.pitch");
				
				Location loc = new Location(world, x, y, z, yaw, pitch);
				p.teleport(loc);
				msg.good(p, "You have been teleported to the lobby!");
				return true;
			}
			if(args[0].equalsIgnoreCase("lobby")){
				if(!(sender instanceof Player)){
					msg.error(sender, "Only players can use this command");
					return true;
				}
				Player p = (Player) sender;
				if(!(p.hasPermission("simpleparkour.lobby"))){
					msg.perm(p);
					return true;
				}
				
				World world = Bukkit.getServer().getWorld(settings.getParkour().getString("lobby.world"));
				int x = settings.getParkour().getInt("lobby.x");
				int y = settings.getParkour().getInt("lobby.y");
				int z = settings.getParkour().getInt("lobby.z");
				int yaw = settings.getParkour().getInt("lobby.yaw");
				int pitch = settings.getParkour().getInt("lobby.pitch");
				
				Location loc = new Location(world, x, y, z, yaw, pitch);
				p.teleport(loc);
				msg.good(p, "You have left a parkour course!");
				msg.info(p, "There have been removed a point from your stats!");
				String uuid = p.getUniqueId().toString();
				settings.getStats().set("stats." + uuid + ".completed", - 1);
				return true;
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
				String uuid = p.getUniqueId().toString();
				if(settings.getData().getConfigurationSection("coords." + uuid) == null){
					msg.error(p, "You do not have a checkpoint");
					return true;
				}
				settings.getData().set("coords." + uuid + ".enable", "false");
				settings.getData().set("coords." + uuid + ".world", null);
				settings.getData().set("coords." + uuid + ".x", null);
				settings.getData().set("coords." + uuid + ".y", null);
				settings.getData().set("coords." + uuid + ".z", null);
				settings.getData().set("coords." + uuid + ".yaw", null);
				settings.getData().set("coords." + uuid + ".pitch", null);
				msg.good(p, "Your checkpoint have been reset!");
				return true;
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
				if(!(player.contains(p))){
					msg.error(p, "You are not in a parkour course");
					return true;
				}
				String uuid = p.getUniqueId().toString();
				if(settings.getData().getConfigurationSection("coords." + uuid) == null){
					msg.error(p, "You do not have a checkpoint");
					return true;
				}
				World world = Bukkit.getServer().getWorld(settings.getData().getString("coords" + "." + uuid + ".world"));
				int x = settings.getData().getInt("coords." + uuid + ".x");
				int y = settings.getData().getInt("coords." + uuid + ".y");
				int z = settings.getData().getInt("coords." + uuid + ".z");
				int yaw = settings.getData().getInt("coords." + uuid + ".yaw");
				int pitch = settings.getData().getInt("coords." + uuid + ".pitch");
				
				Location loc = new Location(world, x, y, z, yaw, pitch);
				p.teleport(loc);
				msg.good(p, "You have been teleported to your last checkpoint!");
				return true;
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
				if(settings.getParkour().getString("parkour." + name + ".name") != null){
					msg.error(p, name + " do already exist");
					return true;
				}
				
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
				if(!(p.hasPermission("simpleparkour.setstart"))){
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
			if(args[0].equalsIgnoreCase("setlobby")){
				if(!(sender instanceof Player)){
					msg.error(sender, "Only players can use this command");
					return true;
				}
				Player p = (Player) sender;
				if(!(p.hasPermission("simpleparkour.setlobby"))){
					msg.perm(p);
					return true;
				}
				
				settings.getParkour().set("lobby.world", p.getWorld().getName());
				settings.getParkour().set("lobby.x", p.getLocation().getX());
				settings.getParkour().set("lobby.y", p.getLocation().getY());
				settings.getParkour().set("lobby.z", p.getLocation().getZ());
				settings.getParkour().set("lobby.yaw", p.getLocation().getYaw());
				settings.getParkour().set("lobby.pitch", p.getLocation().getPitch());
				settings.saveParkour();
				
				msg.good(p, "New lobby location set!");
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
				msg.good(p, name + " have been removed!");
				return true;
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
	
	@EventHandler
	 public void onSignChange(SignChangeEvent e) {
		 if(e.getLine(0).equalsIgnoreCase("%checkpoint%")){
			 if(!(e.getPlayer().hasPermission("simpleparkour.sign"))){
				 msg.perm(e.getPlayer());
				 return;
			 }
			 e.setLine(0, "-------------");
			 e.setLine(1, "§4Checkpoint");
			 e.setLine(2, "Right Click");
			 e.setLine(3, "-------------");
			 return;
		 }
		 if(e.getLine(0).equalsIgnoreCase("%end%")){
			 if(!(e.getPlayer().hasPermission("simpleparkour.sign"))){
				 msg.perm(e.getPlayer());
				 return;
			 }
			 e.setLine(0, "-------------");
			 e.setLine(1, "§4Finish");
			 e.setLine(2, "Right Click");
			 e.setLine(3, "-------------");
			 return;
		 }
	 }
	
	@EventHandler
	 public void interact(PlayerInteractEvent e){
		 if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
		 if (e.getClickedBlock().getState() instanceof Sign) {
			 Sign s = (Sign) e.getClickedBlock().getState();
			 if(s.getLine(1).equalsIgnoreCase("§4Checkpoint")){
				 Player p = e.getPlayer();
				 if(!(player.contains(e.getPlayer()))) {
					 msg.error(p, "You are not in a parkour course!");
					 return;
				 }
				 String uuid = p.getUniqueId().toString();
				 settings.getData().set("coords." + uuid + ".enable", "true");
				 settings.getData().set("coords." + uuid + ".world", p.getWorld().getName());
				 settings.getData().set("coords." + uuid + ".x", p.getLocation().getX());
			     settings.getData().set("coords." + uuid + ".y", p.getLocation().getY());
				 settings.getData().set("coords." + uuid + ".z", p.getLocation().getZ());
				 settings.getData().set("coords." + uuid + ".yaw", p.getLocation().getYaw());
				 settings.getData().set("coords." + uuid + ".pitch", p.getLocation().getPitch());
				 settings.saveData();
				 msg.good(p, "Checkpoint Saved!");
				 return;
			 }
			 if(s.getLine(1).equalsIgnoreCase("§4Finish")){
				 Player p = e.getPlayer();
				 if(!(player.contains(e.getPlayer()))) {
					 msg.error(p, "You are not in a parkour course!");
					 return;
				 }
		           
		        player.remove(p);
					
				World world = Bukkit.getServer().getWorld(settings.getParkour().getString("lobby.world"));
				int x = settings.getParkour().getInt("lobby.x");
				int y = settings.getParkour().getInt("lobby.y");
				int z = settings.getParkour().getInt("lobby.z");
				int yaw = settings.getParkour().getInt("lobby.yaw");
				int pitch = settings.getParkour().getInt("lobby.pitch");
					
				Location loc = new Location(world, x, y, z, yaw, pitch);
			    p.teleport(loc);
		        p.sendMessage(ChatColor.YELLOW + "-------------------------------------------");
				p.sendMessage(ChatColor.GOLD + "You have completed a parkour course!");
				p.sendMessage(ChatColor.GOLD + "There have been added 10 points!");
				p.sendMessage(ChatColor.YELLOW + "-------------------------------------------");
				String uuid = p.getUniqueId().toString();
				settings.getStats().set("stats." + uuid + ".points", + 10);
				settings.getStats().set("stats." + uuid + ".completed", + 1);
				settings.saveStats();
				return;
			 }
		 }
	 }
	
	@EventHandler
   public void onPlayerDamage(EntityDamageEvent e) {
           if (e.getEntity() instanceof Player) {
                   Player p = (Player) e.getEntity();
                   if (e.getCause() == DamageCause.FALL && player.contains(p)) {
                	   e.setDamage(0.0);
                   }
           }
   }

}
