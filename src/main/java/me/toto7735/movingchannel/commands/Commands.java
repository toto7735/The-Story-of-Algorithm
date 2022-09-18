package me.toto7735.movingchannel.commands;

import me.toto7735.movingchannel.utils.Stage1;
import me.toto7735.movingchannel.utils.Stage4;
import me.toto7735.movingchannel.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        switch (s) {
            case "removetnt" -> {
                int removed = 0;
                for (Entity entity : Bukkit.getWorld("world").getEntities()) {
                    if (!entity.getType().equals(EntityType.PRIMED_TNT)) continue;
                    entity.remove();
                    ++removed;
                }
                sender.sendMessage("Removed " + removed + " TNT(s)!");
                break;
            }
            case "start" -> {
//                Utils.start();
//
//                Stage1.stage_1();
                Stage4.stage_4();
                break;
            }


        }
        return false;
    }

}

