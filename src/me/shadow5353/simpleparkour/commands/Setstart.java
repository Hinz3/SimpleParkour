package me.shadow5353.simpleparkour.commands;

import me.shadow5353.simpleparkour.managers.MessageManager;
import me.shadow5353.simpleparkour.managers.SettingsManager;
import org.bukkit.entity.Player;

/**
 * Created by Jacob on 07-05-2018.
 */
public class Setstart extends SimpleCommands {
    private MessageManager message = new MessageManager();
    private SettingsManager settings = SettingsManager.getInstance();

    @Override
    public void onCommand(Player p, String[] args) {
        if(!(p.hasPermission("simpleparkour.setstart"))){
            message.noPermission(p);
        } else if(args.length == 0){
            message.error(p, "Please specify a name!");
        } else {

            String name = args[0];
            if (settings.getParkour().getString("parkour." + name + ".name") == null) {
                message.error(p, name + "does not exist");
            } else {
                settings.getParkour().set("parkour." + name + ".name", name);
                settings.getParkour().set("parkour." + name + ".start" + ".world", p.getWorld().getName());
                settings.getParkour().set("parkour." + name + ".start" + ".x", p.getLocation().getX());
                settings.getParkour().set("parkour." + name + ".start" + ".y", p.getLocation().getY());
                settings.getParkour().set("parkour." + name + ".start" + ".z", p.getLocation().getZ());
                settings.getParkour().set("parkour." + name + ".start" + ".yaw", p.getLocation().getYaw());
                settings.getParkour().set("parkour." + name + ".start" + ".pitch", p.getLocation().getPitch());
                settings.saveParkour();

                message.good(p, "Saved the new start location for " + name + "!");
            }
        }
    }

    public Setstart() {
        super("Set the start for a course name", "<Course Name>", "setstart");
    }
}
