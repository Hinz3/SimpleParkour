package me.shadow5353.simpleparkour.commands;

import me.shadow5353.simpleparkour.managers.MessageManager;
import me.shadow5353.simpleparkour.managers.SettingsManager;
import org.bukkit.entity.Player;

/**
 * Created by Jacob on 08-05-2018.
 */
public class Create extends SimpleCommands {
    private MessageManager message = new MessageManager();
    private SettingsManager settings = SettingsManager.getInstance();

    @Override
    public void onCommand(Player p, String[] args) {
        if(!(p.hasPermission("simpleparkour.create"))){
            message.noPermission(p);
        } else if(args.length == 0){
            message.error(p, "Please specify a name!");
        } else {
            String name = args[0];
            if (settings.getParkour().getString("parkour." + name + ".name") != null) {
                message.error(p, name + " do already exist");
            } else {
                settings.getParkour().set("parkour." + name + ".name", name);
                settings.getParkour().set("parkour." + name + ".reward", "");
                settings.getParkour().set("parkour." + name + ".start" + ".world", p.getWorld().getName());
                settings.getParkour().set("parkour." + name + ".start" + ".x", p.getLocation().getX());
                settings.getParkour().set("parkour." + name + ".start" + ".y", p.getLocation().getY());
                settings.getParkour().set("parkour." + name + ".start" + ".z", p.getLocation().getZ());
                settings.getParkour().set("parkour." + name + ".start" + ".yaw", p.getLocation().getYaw());
                settings.getParkour().set("parkour." + name + ".start" + ".pitch", p.getLocation().getPitch());
                settings.saveParkour();

                message.good(p, "Created parkour course " + name + "!");
            }
        }
    }

    public Create() {
        super("Create a parkour course", "<Course Name>", "create");
    }
}
