package me.toto7735.movingchannel.utils;

import me.toto7735.movingchannel.MovingChannel;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Road1 {

    public static boolean started;

    private static Map<Player, ItemStack[]> playerInventoryHashMap = new HashMap<>();
    private static Map<Player, ItemStack[]> playerArmorContentHashMap = new HashMap<>();

    private static List<Entity> entities = new ArrayList<>();

    public static void start() {
        Bukkit.getWorld("map").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        Bukkit.getWorld("map").setTime(10710);
        started = true;
        new BukkitRunnable() {
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm s resettimers");
            }
        }.runTaskLater(MovingChannel.getInstance(), 400);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getWorld().setSpawnLocation(new Location(Bukkit.getWorld("map"), -1434.5, 81.00, -1331.5, 0, 0));
            playerInventoryHashMap.put(player, player.getInventory().getContents());
            playerArmorContentHashMap.put(player, player.getInventory().getArmorContents());
            player.teleport(new Location(Bukkit.getWorld("map"), -1479.6, 70.9375, -1274.4, -68, 0.5F));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 255, true, false, false));
            Utils.sendMessageAfter(player, "&etoto&f: where is this??", 20);
            Utils.sendMessageAfter(player, "&etoto&f: .....Dang Algorithm", 100);
        }

        Zombie zombie1 = (Zombie) Bukkit.getWorld("map").spawnEntity(new Location(Bukkit.getWorld("map"), -1457, 75, -1257, 125, 10), EntityType.ZOMBIE);
        zombie1.setMaxHealth(50);
        zombie1.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        zombie1.setCustomNameVisible(true);
        zombie1.setCustomName("§c[Lv.2K] §fCombie");
        Zombie zombie2 = (Zombie) Bukkit.getWorld("map").spawnEntity(new Location(Bukkit.getWorld("map"), -1459, 74, -1276, 77, 9), EntityType.ZOMBIE);
        zombie2.setMaxHealth(25);
        zombie2.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
        zombie2.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        zombie2.setCustomNameVisible(true);
        zombie2.setCustomName("§c[Lv.1K] §fEombie");
        entities.add(zombie1);
        entities.add(zombie2);

        // portal
        new BukkitRunnable() {
            public void run() {
                for (int i = 0; i < 30; ++i) {
                    Bukkit.getWorld("map").spawnParticle(Particle.PORTAL, new Location(Bukkit.getWorld("map"), -1423.5 + (Math.sin(i) / 10), 80, -1226.5 + (Math.cos(i) / 10)), 10, 0, 1, 0, 10);
                }
            }
        }.runTaskTimerAsynchronously(MovingChannel.getInstance(), 0, 1);
        
    }

    public static void stop() {
        started = false;
        for (Entity entity : entities) entity.remove();
        entities.clear();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m killall");
    }
    
    public static void pass() {
        started = false;
        Stage2.stage_2();
    }

    public static void resetInventory(Player player) {
        ItemStack[] pi = playerInventoryHashMap.get(player);
        ItemStack[] pi2 = playerArmorContentHashMap.get(player);
        player.getInventory().setContents(pi);
        player.getInventory().setArmorContents(pi2);
        player.updateInventory();
    }

}
