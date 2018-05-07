package me.shadow5353.simpleparkour.commands;

import me.shadow5353.simpleparkour.managers.GameManager;
import me.shadow5353.simpleparkour.managers.MessageManager;
import me.shadow5353.simpleparkour.managers.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Created by Jacob on 08-05-2018.
 */
public class Join extends SimpleCommands {
    private MessageManager message = new MessageManager();
    private SettingsManager settings = SettingsManager.getInstance();
    private GameManager game = GameManager.getInstance();

    @Override
    public void onCommand(Player p, String[] args) {
        if (!(p.hasPermission("simpleparkour.join"))) {
            message.noPermission(p);
        } else if (args.length == 0) {
            message.error(p, "Please specify a name!");
        } else if (game.getPlayer(p)) {
            message.error(p, "You are already in a course!");
        } else {
            String name = args[0];
            if (settings.getParkour().getString("parkour." + name + ".name") == null) {
                message.error(p, name + " do not exist!");
            } else {
                game.addPlayer(p);

                World world = Bukkit.getServer().getWorld(settings.getParkour().getString("parkour." + name + ".start.world"));
                int x = settings.getParkour().getInt("parkour." + name + ".start.x");
                int y = settings.getParkour().getInt("parkour." + name + ".start.y");
                int z = settings.getParkour().getInt("parkour." + name + ".start.z");
                int yaw = settings.getParkour().getInt("parkour." + name + ".start.yaw");
                int pitch = settings.getParkour().getInt("parkour." + name + ".start.pitch");

                String uuid = p.getUniqueId().toString();
                settings.getData().set("coords." + uuid + ".enable", "true");
                settings.getData().set("coords." + uuid + ".world", world.getName());
                settings.getData().set("coords." + uuid + ".x", x);
                settings.getData().set("coords." + uuid + ".y", y);
                settings.getData().set("coords." + uuid + ".z", z);
                settings.getData().set("coords." + uuid + ".yaw", yaw);
                settings.getData().set("coords." + uuid + ".pitch", pitch);
                settings.saveData();

                Location loc = new Location(world, x, y, z, yaw, pitch);
                p.teleport(loc);
                message.good(p, "You have joined " + name + "!");
            }
        }
    }

    public Join() {
        super("Join a parkour course", "<Course Name>", "join");
    }
}
