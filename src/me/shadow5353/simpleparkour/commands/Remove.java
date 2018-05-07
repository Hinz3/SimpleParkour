package me.shadow5353.simpleparkour.commands;

import me.shadow5353.simpleparkour.managers.MessageManager;
import me.shadow5353.simpleparkour.managers.SettingsManager;
import org.bukkit.entity.Player;

/**
 * Created by Jacob on 08-05-2018.
 */
public class Remove extends SimpleCommands {
    private MessageManager message = new MessageManager();
    private SettingsManager settings = SettingsManager.getInstance();

    @Override
    public void onCommand(Player p, String[] args) {
        if(!(p.hasPermission("simpleparkour.remove"))){
            message.noPermission(p);
        } else if(args.length == 0){
            message.error(p, "Please specify a name!");
        } else {

            String name = args[0];
            if (settings.getParkour().getString("parkour." + name + ".name") == null) {
                message.error(p, name + "does not exist");
            } else {
                settings.getParkour().set("parkour." + name + ".name", null);
                settings.getParkour().set("parkour." + name + ".start" + ".world", null);
                settings.getParkour().set("parkour." + name + ".start" + ".x", null);
                settings.getParkour().set("parkour." + name + ".start" + ".y", null);
                settings.getParkour().set("parkour." + name + ".start" + ".z", null);
                settings.getParkour().set("parkour." + name + ".start" + ".yaw", null);
                settings.getParkour().set("parkour." + name + ".start" + ".pitch", null);
                settings.saveParkour();
                message.good(p, name + " have been removed!");
            }
        }
    }

    public Remove() {
        super("Remove a parkour course", "<Course Name>", "remove");
    }
}
