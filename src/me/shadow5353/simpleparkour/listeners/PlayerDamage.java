package me.shadow5353.simpleparkour.listeners;

import me.shadow5353.simpleparkour.managers.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Jacob on 08-05-2018.
 */
public class PlayerDamage implements Listener {
    private GameManager game = GameManager.getInstance();

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL && game.getPlayer(p)) {
                e.setDamage(0.0);
            }
        }
    }
}
