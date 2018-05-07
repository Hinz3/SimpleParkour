package me.shadow5353.simpleparkour.managers;

import me.shadow5353.simpleparkour.commands.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Jacob on 07-05-2018.
 */
public class CommandManager implements CommandExecutor {
    private MessageManager message = new MessageManager();

    private ArrayList<SimpleCommands> cmds = new ArrayList();

    public CommandManager() {
        // Add commands
        cmds.add(new Join());
        cmds.add(new Info());
        cmds.add(new Leave());
        cmds.add(new Return());
        cmds.add(new Reset());
        cmds.add(new Create());
        cmds.add(new Remove());
        cmds.add(new Setstart());
        cmds.add(new Setlobby());
        cmds.add(new Reload());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            message.error(sender, "Only players can use SimpleParkour!");
            return true;
        }

        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("parkour")) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.GOLD + "---------------------------------------------");
                p.sendMessage(ChatColor.GOLD + "/parkour" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Show a list of commands");
                for (SimpleCommands mc : cmds) p.sendMessage(ChatColor.GOLD + "/parkour " + aliases(mc) + " " + mc.getUsage() + ChatColor.BLACK + ": " + ChatColor.YELLOW + mc.getMessage());
                p.sendMessage(ChatColor.GOLD + "---------------------------------------------");
                return true;
            }

            SimpleCommands c = getCommand(args[0]);

            if (c == null) {
                message.error(p,"That command doesn't exist!");
                return true;
            }

            Vector<String> a = new Vector<>(Arrays.asList(args));
            a.remove(0);
            args = a.toArray(new String[a.size()]);

            c.onCommand(p, args);

            return true;
        }
        return true;
    }

    private String aliases(SimpleCommands cmd) {
        String fin = "";

        for (String a : cmd.getAliases()) {
            fin += a + " | ";
        }

        return fin.substring(0, fin.lastIndexOf(" | "));
    }

    private SimpleCommands getCommand(String name) {
        for (SimpleCommands cmd : cmds) {
            if (cmd.getClass().getSimpleName().equalsIgnoreCase(name)) return cmd;
            for (String alias : cmd.getAliases()) if (name.equalsIgnoreCase(alias)) return cmd;
        }
        return null;
    }
}
