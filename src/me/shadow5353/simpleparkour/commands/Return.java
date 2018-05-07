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
public class Return extends SimpleCommands {
    private MessageManager message = new MessageManager();
    private SettingsManager settings = SettingsManager.getInstance();
    private GameManager game = GameManager.getInstance();

    @Override
    public void onCommand(Player p, String[] args) {
        if (!(p.hasPermission("simpleparkour.return"))) {
            message.noPermission(p);
        } else if (!(game.getPlayer(p))) {
            message.error(p, "You are not in a parkour course");
        } else {
            String uuid = p.getUniqueId().toString();
            if (settings.getData().getConfigurationSection("coords." + uuid) == null) {
                message.error(p, "You do not have a checkpoint");
            } else {
                World world = Bukkit.getServer().getWorld(settings.getData().getString("coords" + "." + uuid + ".world"));
                int x = settings.getData().getInt("coords." + uuid + ".x");
                int y = settings.getData().getInt("coords." + uuid + ".y");
                int z = settings.getData().getInt("coords." + uuid + ".z");
                int yaw = settings.getData().getInt("coords." + uuid + ".yaw");
                int pitch = settings.getData().getInt("coords." + uuid + ".pitch");

                Location loc = new Location(world, x, y, z, yaw, pitch);
                p.teleport(loc);
                message.good(p, "You have been teleported to your last checkpoint!");
            }
        }
    }

    public Return() {
        super("Return to checkpoint", "","return");
    }
}
