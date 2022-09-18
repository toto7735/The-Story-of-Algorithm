package me.toto7735.movingchannel.utils;

import me.toto7735.movingchannel.MovingChannel;
import net.nuggetmc.tplus.api.event.BotDeathEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.TNT;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;

public class Stage1 {

    private static Map<Player, ItemStack[]> playerInventoryHashMap = new HashMap<>();
    private static Map<Player, ItemStack[]> playerArmorContentHashMap = new HashMap<>();

    public static boolean started;
    public static Set<Material> destories = new HashSet<>(Arrays.asList(Material.QUARTZ_STAIRS, Material.QUARTZ_SLAB, Material.SMOOTH_QUARTZ_SLAB, Material.SMOOTH_QUARTZ_STAIRS));
    public static int taskId1;
    public static int taskId2;
    public static int taskId3;
    public static Wither wither;

    public static void stage_1() {
        started = true;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.getWorld().setSpawnLocation(new Location(Bukkit.getWorld("world"), 46.5, 174.00, 8.5, 0, 0));
            playerInventoryHashMap.put(onlinePlayer, onlinePlayer.getInventory().getContents());
            playerArmorContentHashMap.put(onlinePlayer, onlinePlayer.getInventory().getArmorContents());
            onlinePlayer.sendTitle("§c§lAdventure I", "§eClear the parkour... BUT?", 5, 30, 10);
            onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
            onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999, 0, true, false, false));
        }
        Utils.pasteSchematic(new Location(Bukkit.getWorld("world"), -23.5, 105, -24.5), new File("plugins\\FastAsyncWorldEdit\\schematics\\stage_1.schem"));
        new BukkitRunnable() {
            public void run() {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.teleport(new Location(Bukkit.getWorld("world"), -19.5, 116, 0.5, -90, 0));
                    onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999, 0, true, false, false));
                    for (int i = 0; i < 9; ++i) onlinePlayer.getInventory().addItem(new ItemStack(Material.QUARTZ_BLOCK, 64));
                }
                taskId3 = destory();

                wither = (Wither) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -24.5, 116, 0.5, -90, 0), EntityType.WITHER);
                wither.setCustomName("§c[YOUTUBE] §fAlgorithm");
                wither.setCustomNameVisible(true);
                wither.setInvulnerable(true);
                wither.getBossBar().removeAll();
                wither.setAI(false);
                taskId1 = new BukkitRunnable() {
                    public void run() {
                        wither.teleport(wither.getLocation().clone().add(0.03, 0, 0));
                    }
                }.runTaskTimer(MovingChannel.getInstance(), 0, 3).getTaskId();

                taskId2 = new BukkitRunnable() {
                    public void run() {
                        for (double d = wither.getLocation().getZ() - 22; d < wither.getLocation().getZ() + 22; d += new Random().nextInt(3) + 3) {
                            Location location = wither.getLocation();
                            location.setZ(d);
                            TNTPrimed tntPrimed = (TNTPrimed) wither.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
                            tntPrimed.setFuseTicks(20);
                            tntPrimed.setVelocity(tntPrimed.getVelocity().setY(0.2 + (2 - 0.2) * new Random().nextDouble()));
                        }
                    }
                }.runTaskTimer(MovingChannel.getInstance(), 0, 20).getTaskId();
            }
        }.runTaskLater(MovingChannel.getInstance(), 100);
    }

    public static int destory() {
        return new BukkitRunnable() {
            public void run() {
                int i = 0;
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    Block block = onlinePlayer.getLocation().getBlock();
                    while (i < 12) {
                        Block relative = block.getRelative(0, i + 1, 0);
                        if (!destories.contains(relative.getType())) {
                            ++i;
                            continue;
                        }
                        if (new Random().nextInt(3) != 0) continue;
                        BlockData blockData = relative.getBlockData();
                        relative.setType(Material.AIR);
                        onlinePlayer.getWorld().spawnFallingBlock(relative.getLocation().clone().add(0.5, 0, 0.5), blockData);
                        block.getWorld().playSound(relative.getLocation().clone().add(0.5, 0, 0.5), Sound.BLOCK_CHAIN_BREAK, 0.1F, 0.5F);
                        block.getWorld().playSound(relative.getLocation().clone().add(0.5, 0, 0.5), Sound.BLOCK_CHAIN_PLACE, 0.1F, 0.5F);
                    }
                }
            }
        }.runTaskTimer(MovingChannel.getInstance(), 0, 5).getTaskId();
    }

    public static void stop() {
        started = false;
        Bukkit.getScheduler().cancelTask(taskId1);
        Bukkit.getScheduler().cancelTask(taskId2);
        Bukkit.getScheduler().cancelTask(taskId3);
        wither.remove();
        for (Entity current : Bukkit.getWorld("world").getEntities()) if (current instanceof Item || current instanceof Arrow || current instanceof TNTPrimed) current.remove();
    }

    public static void pass(Player player) {
        Road1.start();
    }

    public static void resetInventory(Player player) {
        ItemStack[] pi = playerInventoryHashMap.get(player);
        ItemStack[] pi2 = playerArmorContentHashMap.get(player);
        player.getInventory().setContents(pi);
        player.getInventory().setArmorContents(pi2);
        player.updateInventory();
    }
}

