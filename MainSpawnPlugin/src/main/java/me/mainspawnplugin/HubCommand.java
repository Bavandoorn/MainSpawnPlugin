package me.mainspawnplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCommand implements CommandExecutor {
    private final MainSpawnPlugin plugin;

    public HubCommand(MainSpawnPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("hub") || command.getName().equalsIgnoreCase("lobby")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.teleport(plugin.getMainSpawn());
                player.sendMessage("Teleported to hub/lobby.");
            } else {
                sender.sendMessage("Only players can teleport to hub/lobby.");
            }
            return true;
        }
        return false;
    }
}
