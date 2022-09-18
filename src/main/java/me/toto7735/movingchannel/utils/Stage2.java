package me.toto7735.movingchannel.utils;

import me.toto7735.movingchannel.MovingChannel;
import net.nuggetmc.tplus.TerminatorPlus;
import net.nuggetmc.tplus.api.TerminatorPlusAPI;
import net.nuggetmc.tplus.api.utils.MojangAPI;
import net.nuggetmc.tplus.bot.Bot;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Stage2 {

    public static boolean started;

    private static Map<Player, ItemStack[]> playerInventoryHashMap = new HashMap<>();
    private static Map<Player, ItemStack[]> playerArmorContentHashMap = new HashMap<>();

    public static void stage_2() {
        started = true;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.getWorld().setSpawnLocation(new Location(Bukkit.getWorld("world"), 46.5, 174.00, 8.5, 0, 0));
            playerInventoryHashMap.put(onlinePlayer, onlinePlayer.getInventory().getContents());
            playerArmorContentHashMap.put(onlinePlayer, onlinePlayer.getInventory().getArmorContents());
            onlinePlayer.sendTitle("§c§lAdventure II", "§eFight the Algorithm's minions!", 5, 30, 10);
            onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
            onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999, 0, true, false, false));
        }
        Utils.pasteSchematic(new Location(Bukkit.getWorld("world"), 108.5, 112, -20.5), new File("plugins\\FastAsyncWorldEdit\\schematics\\stage_2.schem"));
        new BukkitRunnable() {
            public void run() {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.teleport(new Location(Bukkit.getWorld("world"), 112.5, 114, 9.5, -90, 0));
                    onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999, 0, true, false, false));
                    Utils.sendMessageAfter(onlinePlayer, "&c[YOUTUBE] &6Little Angry Algorithm&f: WELL, I wonder if you can do this TOO.", 20);
                }
                spawnBotAfter(50);
                spawnBotAfter(100);
                spawnBotAfter(150);
                spawnBotAfter(200);
                spawnBotAfter(250);
                spawnBotAfter(300);
                spawnBotAfter(350);
                spawnBotAfter(400);
                spawnBotAfter(450);
                spawnBotAfter(500);
            }
        }.runTaskLater(MovingChannel.getInstance(), 100);
    }

    public static void stop() {
        started = false;
        TerminatorPlusAPI.getBotManager().reset();
        for (Entity current : Bukkit.getWorld("world").getEntities()) if (current instanceof Item || current instanceof Arrow || current instanceof TNTPrimed) current.remove();
    }

    public static void spawnBotAfter(int ticks) {
        new BukkitRunnable() {
            public void run() {
                Bot bot = Bot.createBot(new Location(Bukkit.getWorld("world"), 123.5, 114, 17.5, -90, 0), "§c[YT] §0Load", MojangAPI.getSkin("vytho"));
                bot.setItem(new ItemStack(Material.DIAMOND_HELMET), EquipmentSlot.HEAD);
                bot.setItem(new ItemStack(Material.IRON_CHESTPLATE), EquipmentSlot.CHEST);
                bot.setItem(new ItemStack(Material.WOODEN_SWORD), EquipmentSlot.HAND);
                Bukkit.getWorld("world").spawnParticle(Particle.SMOKE_LARGE, bot.getLocation(), 10);
            }
        }.runTaskLater(MovingChannel.getInstance(), ticks);
    }

    public static void resetInventory(Player player) {
        ItemStack[] pi = playerInventoryHashMap.get(player);
        ItemStack[] pi2 = playerArmorContentHashMap.get(player);
        player.getInventory().setContents(pi);
        player.getInventory().setArmorContents(pi2);
        player.updateInventory();
    }

}
