package me.shadow5353.simpleparkour.commands;

import me.shadow5353.simpleparkour.managers.GameManager;
import me.shadow5353.simpleparkour.managers.MessageManager;
import me.shadow5353.simpleparkour.managers.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Created by Jacob on 07-05-2018.
 */
public class Leave extends SimpleCommands {
    private MessageManager message = new MessageManager();
    private SettingsManager settings = SettingsManager.getInstance();
    private GameManager game = GameManager.getInstance();

    @Override
    public void onCommand(Player p, String[] args) {
        if(!(p.hasPermission("simpleparkour.leave"))){
            message.noPermission(p);
        } else if(!(game.getPlayer(p))) {
            message.error(p, "You are not in a parkour course");
        } else {
            game.removePlayer(p);

            World world = Bukkit.getServer().getWorld(settings.getParkour().getString("lobby.world"));
            int x = settings.getParkour().getInt("lobby.x");
            int y = settings.getParkour().getInt("lobby.y");
            int z = settings.getParkour().getInt("lobby.z");
            int yaw = settings.getParkour().getInt("lobby.yaw");
            int pitch = settings.getParkour().getInt("lobby.pitch");

            Location loc = new Location(world, x, y, z, yaw, pitch);
            p.teleport(loc);
            message.good(p, "You have been teleported to the lobby!");
        }
    }

    public Leave() {
        super("Leave the parkour course", "<Course name>", "leave");
    }
}
