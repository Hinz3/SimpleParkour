package me.shadow5353.simpleparkour;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class Signs implements Listener{
	
	@EventHandler
    public void onSignChange(SignChangeEvent e) {
		Player p = e.getPlayer();
		if(p.hasPermission("simpleparkour.sign")){
			if(e.getLine(0).equals("[c]")){
				e.setLine(0, "test");
			}
		}
	}

}
