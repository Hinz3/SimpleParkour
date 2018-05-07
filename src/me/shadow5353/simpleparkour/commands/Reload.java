package me.shadow5353.simpleparkour.commands;

import me.shadow5353.simpleparkour.managers.MessageManager;
import me.shadow5353.simpleparkour.managers.SettingsManager;
import org.bukkit.entity.Player;

/**
 * Created by Jacob on 07-05-2018.
 */
public class Reload extends SimpleCommands {
    private MessageManager message = new MessageManager();
    private SettingsManager settings = SettingsManager.getInstance();

    @Override
    public void onCommand(Player p, String[] args) {
        if(!(p.hasPermission("simpleparkour.reload"))){
            message.noPermission(p);
        } else {
            settings.reloadData();
            settings.reloadParkour();
            settings.reloadStats();

            message.good(p, "All files have been reloaded!");
        }
    }

    public Reload() {
        super("Reload the config","","reload");
    }
}
