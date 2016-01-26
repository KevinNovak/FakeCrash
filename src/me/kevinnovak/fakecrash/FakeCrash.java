package me.kevinnovak.fakecrash;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class FakeCrash extends JavaPlugin implements Listener{

    // ======================
    // Enable
    // ======================
    public void onEnable() {  
        // save default config file if it doesnt exist
        saveDefaultConfig();
        
        // register the listeners
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        
        // start metrics
        if (getConfig().getBoolean("metrics")) {
            try {
                MetricsLite metrics = new MetricsLite(this);
                metrics.start();
                info("[FakeCrash] Metrics Enabled!");
            } catch (IOException e) {
                info("[FakeCrash] Failed to Start Metrics.");
            }
        } else {
            info("[FakeCrash] Metrics Disabled.");
        }
        
        // plugin is enabled
        info("[FakeCrash] Plugin Enabled!");
    }
    
    // ======================
    // Disable
    // ======================
    public void onDisable() {
        // plugin is disabled
        info("[FakeCrash] Plugin Disabled!");
    }   
    
    // ======================
    // Commands
    // ======================
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        // ======================
        // Console
        // ======================
        // if command sender is the console, let them know, cancel command
        if (!(sender instanceof Player)) {
            sender.sendMessage(convertedLang("noConsole"));
            return true;
        }

     // otherwise the command sender is a player
        //Player player = (Player) sender;
        
        // ======================
        // /finddeath
        // ======================
        if(cmd.getName().equalsIgnoreCase("crash")) {
            for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
                onlinePlayer.kickPlayer("Internal exception: java.net.SocketTimeoutException: Read timed out");
            }
        }
        return true;
    }
    
    
    
    
    // =========================
    // Convert String in Config
    // =========================
    // converts string in config, to a string with colors
    String convertedLang(String toConvert) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString(toConvert));
    }
    
    // =========================
    // Info
    // =========================
    // sends an info string to the console
    void info(String toConsole) {
        Bukkit.getServer().getLogger().info(toConsole);
    }
}
