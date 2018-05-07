package me.shadow5353.simpleparkour.commands;

import org.bukkit.entity.Player;

/**
 * Created by Jacob on 07-05-2018.
 */
public abstract  class SimpleCommands {

    public abstract void onCommand(Player p, String[] args);

    private String message, usage;
    private String[] aliases;

    public SimpleCommands(String message, String usage, String... aliases) {
        this.message = message;
        this.usage = usage;
        this.aliases = aliases;
    }

    public final String getMessage() {
        return message;
    }

    public final String getUsage() {
        return usage;
    }

    public final String[] getAliases() {
        return aliases;
    }
}