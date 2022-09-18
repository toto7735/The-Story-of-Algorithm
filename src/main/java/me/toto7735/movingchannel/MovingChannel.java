package me.toto7735.movingchannel;

import me.toto7735.movingchannel.commands.Commands;
import me.toto7735.movingchannel.items.Items;
import me.toto7735.movingchannel.listeners.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

// Very hardcoded project.
public class MovingChannel extends JavaPlugin {
    private static MovingChannel instance;

    @Override
    public void onEnable() {
        instance = this;
        this.getCommand("start").setExecutor(new Commands());
        this.getCommand("resetinventory").setExecutor(new Commands());
        this.getCommand("removetnt").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        Bukkit.getPluginManager().registerEvents(new Items(), this);
    }

    public static MovingChannel getInstance() {
        return instance;
    }

}
