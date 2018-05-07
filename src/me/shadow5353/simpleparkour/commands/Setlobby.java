package me.shadow5353.simpleparkour.commands;

import me.shadow5353.simpleparkour.managers.MessageManager;
import me.shadow5353.simpleparkour.managers.SettingsManager;
import org.bukkit.entity.Player;

/**
 * Created by Jacob on 07-05-2018.
 */
public class Setlobby extends SimpleCommands {
    private MessageManager message = new MessageManager();
    private SettingsManager settings = SettingsManager.getInstance();

    @Override
    public void onCommand(Player p, String[] args) {
        if (!(p.hasPermission("simpleparkour.setlobby"))) {
            message.noPermission(p);
        } else {
            settings.getParkour().set("lobby.world", p.getWorld().getName());
            settings.getParkour().set("lobby.x", p.getLocation().getX());
            settings.getParkour().set("lobby.y", p.getLocation().getY());
            settings.getParkour().set("lobby.z", p.getLocation().getZ());
            settings.getParkour().set("lobby.yaw", p.getLocation().getYaw());
            settings.getParkour().set("lobby.pitch", p.getLocation().getPitch());
            settings.saveParkour();

            message.good(p, "New lobby location set!");
        }
    }

    public Setlobby() {
        super("Set a lobby for the parkour", "", "setlobby");
    }
}
