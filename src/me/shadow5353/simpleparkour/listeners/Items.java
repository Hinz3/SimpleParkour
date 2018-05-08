package me.shadow5353.simpleparkour.listeners;

import me.shadow5353.simpleparkour.managers.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by Jacob on 08-05-2018.
 */
public class Items implements Listener {
    private GameManager game = GameManager.getInstance();

    @EventHandler
    public void onItemClick(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if (!(event.getAction() == Action.RIGHT_CLICK_AIR)) return;
        if (!(game.getPlayer(player))) return;
        if (!(event.getItem().getType() == Material.EMERALD));

        player.performCommand("p return");
    }
}
