package me.shadow5353.simpleparkour.managers;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Jacob on 07-05-2018.
 */
public class GameManager {
    private ArrayList<Player> players = new ArrayList<>();

    private GameManager() { }

    static GameManager instance = new GameManager();

    public static GameManager getInstance() {
        return instance;
    }

    public boolean getPlayer(Player player) {
        return players.contains(player);
    }

    public void addPlayer(Player player) {
        if (!(getPlayer(player))) {
            players.add(player);

            player.setGameMode(GameMode.SURVIVAL);
        }
     }

     public void removePlayer(Player player) {
        if (getPlayer(player)) {
            players.remove(player);
        }
     }
}
