package me.shadow5353.simpleparkour.listeners;

import me.shadow5353.simpleparkour.managers.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Created by Jacob on 07-05-2018.
 */
public class CreateSigns implements Listener {
    private MessageManager message = new MessageManager();

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        if(e.getLine(0).equalsIgnoreCase("%checkpoint%")){
            if(!(e.getPlayer().hasPermission("simpleparkour.sign"))){
                message.noPermission(e.getPlayer());
                return;
            } else {
                e.setLine(0, "-------------");
                e.setLine(1, ChatColor.GOLD + "Checkpoint");
                e.setLine(2, "Right Click");
                e.setLine(3, "-------------");
            }
        }
        if(e.getLine(0).equalsIgnoreCase("%end%")){
            if(!(e.getPlayer().hasPermission("simpleparkour.sign"))){
                message.noPermission(e.getPlayer());
            } else {
                e.setLine(0, "-------------");
                e.setLine(1, ChatColor.GOLD + "Finish");
                e.setLine(2, "Right Click");
                e.setLine(3, "-------------");
            }
        }
        if(e.getLine(0).equalsIgnoreCase("%join%")) {
            if (e.getLine(1) == null) {
                message.error(e.getPlayer(), "You have to specify a course name in line 2!");
            } else if (!(e.getPlayer().hasPermission("simpleparkour.sign"))) {
                message.noPermission(e.getPlayer());
            } else {
                String courseName = e.getLine(1);

                e.setLine(0, "-------------");
                e.setLine(1, ChatColor.GOLD + "Join");
                e.setLine(2, courseName);
                e.setLine(3, "Right Click");
            }
        }
    }
}
