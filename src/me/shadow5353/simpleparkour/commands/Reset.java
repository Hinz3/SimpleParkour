package me.shadow5353.simpleparkour.commands;

import me.shadow5353.simpleparkour.managers.MessageManager;
import me.shadow5353.simpleparkour.managers.SettingsManager;
import org.bukkit.entity.Player;

/**
 * Created by Jacob on 07-05-2018.
 */
public class Reset extends SimpleCommands {
    private MessageManager message = new MessageManager();
    private SettingsManager settings = SettingsManager.getInstance();

    @Override
    public void onCommand(Player p, String[] args) {
        if (!(p.hasPermission("simpleparkour.reset"))) {
            message.noPermission(p);
        } else {
            String uuid = p.getUniqueId().toString();
            if (settings.getData().getConfigurationSection("coords." + uuid) == null) {
                message.error(p, "You do not have a checkpoint");
            } else {
                settings.getData().set("coords." + uuid + ".enable", "false");
                settings.getData().set("coords." + uuid + ".world", null);
                settings.getData().set("coords." + uuid + ".x", null);
                settings.getData().set("coords." + uuid + ".y", null);
                settings.getData().set("coords." + uuid + ".z", null);
                settings.getData().set("coords." + uuid + ".yaw", null);
                settings.getData().set("coords." + uuid + ".pitch", null);
                message.good(p, "Your checkpoint have been reset!");
            }
        }
    }

    public Reset() {
        super("Reset the players checkpoint", "", "reset");
    }
}
