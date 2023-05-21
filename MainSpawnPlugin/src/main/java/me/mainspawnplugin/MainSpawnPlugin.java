package me.mainspawnplugin;

import org.bukkit.Bukkit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MainSpawnPlugin extends JavaPlugin implements Listener {
    private Location mainSpawn;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadMainSpawn();
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("hub").setExecutor(new HubCommand(this));
        getCommand("lobby").setExecutor(new HubCommand(this));
    }

    private void loadMainSpawn() {
        if (getConfig().contains("mainSpawn")) {
            double x = getConfig().getDouble("mainSpawn.x");
            double y = getConfig().getDouble("mainSpawn.y");
            double z = getConfig().getDouble("mainSpawn.z");
            float yaw = (float) getConfig().getDouble("mainSpawn.yaw");
            float pitch = (float) getConfig().getDouble("mainSpawn.pitch");
            mainSpawn = new Location(Bukkit.getWorlds().get(0), x, y, z, yaw, pitch);
        }
    }

    private void saveMainSpawn() {
        getConfig().set("mainSpawn.x", mainSpawn.getX());
        getConfig().set("mainSpawn.y", mainSpawn.getY());
        getConfig().set("mainSpawn.z", mainSpawn.getZ());
        getConfig().set("mainSpawn.yaw", mainSpawn.getYaw());
        getConfig().set("mainSpawn.pitch", mainSpawn.getPitch());
        saveConfig();
    }

    public Location getMainSpawn() {
        return mainSpawn;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("setmainspawn")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                mainSpawn = player.getLocation();
                saveMainSpawn();
                player.sendMessage("Main spawn point set.");
            } else {
                sender.sendMessage("Only players can set the main spawn point.");
            }
            return true;
        } else if (command.getName().equalsIgnoreCase("spawn")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.teleport(mainSpawn);
                player.sendMessage("Teleported to spawn.");
            } else {
                sender.sendMessage("Only players can teleport to spawn.");
            }
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(mainSpawn);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        player.teleport(mainSpawn);
    }
}