package me.shadow5353.simpleparkour.listeners;

import me.shadow5353.simpleparkour.managers.GameManager;
import me.shadow5353.simpleparkour.managers.MessageManager;
import me.shadow5353.simpleparkour.managers.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Jacob on 07-05-2018.
 */
public class SignInteract implements Listener {
    private MessageManager message = new MessageManager();
    private SettingsManager settings = SettingsManager.getInstance();
    private GameManager game = GameManager.getInstance();

    @EventHandler
    public void interact(PlayerInteractEvent e){
        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getClickedBlock().getState() instanceof Sign) {

            Player p = e.getPlayer();
            Sign s = (Sign) e.getClickedBlock().getState();
            if(s.getLine(1).equalsIgnoreCase(ChatColor.GOLD + "Checkpoint")){
                if(!(game.getPlayer(p))) {
                    message.error(p, "You are not in a parkour course!");
                } else {
                    String uuid = p.getUniqueId().toString();
                    settings.getData().set("coords." + uuid + ".enable", "true");
                    settings.getData().set("coords." + uuid + ".world", p.getWorld().getName());
                    settings.getData().set("coords." + uuid + ".x", p.getLocation().getX());
                    settings.getData().set("coords." + uuid + ".y", p.getLocation().getY());
                    settings.getData().set("coords." + uuid + ".z", p.getLocation().getZ());
                    settings.getData().set("coords." + uuid + ".yaw", p.getLocation().getYaw());
                    settings.getData().set("coords." + uuid + ".pitch", p.getLocation().getPitch());
                    settings.saveData();

                    message.good(p, "Checkpoint Saved!");
                }
            }

            if(s.getLine(1).equalsIgnoreCase(ChatColor.GOLD + "Finish")){
                String uuid = p.getUniqueId().toString();
                if(!(game.getPlayer(p))) {
                    message.error(p, "You are not in a parkour course!");
                } else {
                    String name = settings.getData().getString("coords." + uuid + ".course");

                    game.removePlayer(p);

                    settings.getData().set("coords." + uuid + ".enable", "false");
                    settings.getData().set("coords." + uuid + ".course", "");
                    settings.getData().set("coords." + uuid + ".world", "");
                    settings.getData().set("coords." + uuid + ".x", "");
                    settings.getData().set("coords." + uuid + ".y", "");
                    settings.getData().set("coords." + uuid + ".z", "");
                    settings.getData().set("coords." + uuid + ".yaw", "");
                    settings.getData().set("coords." + uuid + ".pitch", "");
                    settings.saveData();

                    World world = Bukkit.getServer().getWorld(settings.getParkour().getString("lobby.world"));
                    int x = settings.getParkour().getInt("lobby.x");
                    int y = settings.getParkour().getInt("lobby.y");
                    int z = settings.getParkour().getInt("lobby.z");
                    int yaw = settings.getParkour().getInt("lobby.yaw");
                    int pitch = settings.getParkour().getInt("lobby.pitch");

                    Location loc = new Location(world, x, y, z, yaw, pitch);
                    p.teleport(loc);

                    p.sendMessage(ChatColor.YELLOW + "-------------------------------------------");
                    p.sendMessage(ChatColor.GOLD + "You have completed the course!");
                    p.sendMessage(ChatColor.YELLOW + "-------------------------------------------");

                }
            }

            if (s.getLine(1).equalsIgnoreCase(ChatColor.GOLD + "Join")) {
                String courseName = s.getLine(2);

                p.performCommand("p join " + courseName);
            }
        }
    }
}
