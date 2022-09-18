package me.toto7735.movingchannel.utils;

import me.toto7735.movingchannel.MovingChannel;
import me.toto7735.movingchannel.listeners.Listeners;
import net.nuggetmc.tplus.api.TerminatorPlusAPI;
import net.nuggetmc.tplus.api.utils.MojangAPI;
import net.nuggetmc.tplus.bot.Bot;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Stage3 {

    public static boolean started;
    public static Bot bot;

    private static Map<Player, ItemStack[]> playerInventoryHashMap = new HashMap<>();
    private static Map<Player, ItemStack[]> playerArmorContentHashMap = new HashMap<>();

    public static void stage_3() {
        started = true;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.getWorld().setSpawnLocation(new Location(Bukkit.getWorld("world"), 46.5, 174.00, 8.5, 0, 0));
            playerInventoryHashMap.put(onlinePlayer, onlinePlayer.getInventory().getContents());
            playerArmorContentHashMap.put(onlinePlayer, onlinePlayer.getInventory().getArmorContents());
            onlinePlayer.sendTitle("§c§lAdventure III", "§eRUN!", 5, 30, 10);
            onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
            onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999, 0, true, false, false));
            onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999999, 255, true, false, false));
            onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 99999999, 255, true, false, false));
            onlinePlayer.setGameMode(GameMode.ADVENTURE);
        }
        Utils.pasteSchematic(new Location(Bukkit.getWorld("world"), 319.5, 124, 31.5), new File("plugins\\FastAsyncWorldEdit\\schematics\\stage_3_1.schem"));
        Utils.pasteSchematic(new Location(Bukkit.getWorld("world"), 335.5, 124, -63.5), new File("plugins\\FastAsyncWorldEdit\\schematics\\stage_3_2.schem"));
        Utils.pasteSchematic(new Location(Bukkit.getWorld("world"), 537.5, 18, -69.5), new File("plugins\\FastAsyncWorldEdit\\schematics\\stage_3_3.schem"));
        Utils.pasteSchematic(new Location(Bukkit.getWorld("world"), 537.5, 25, -62.5), new File("plugins\\FastAsyncWorldEdit\\schematics\\stage_3_4.schem"));
        Utils.pasteSchematic(new Location(Bukkit.getWorld("world"), 529.5, 227, 131), new File("plugins\\FastAsyncWorldEdit\\schematics\\stage_3_5.schem"));
        new BukkitRunnable() {
            public void run() {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.teleport(new Location(Bukkit.getWorld("world"), 332.5, 119, 24.5, 180, 0));
                    onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999, 0, true, false, false));
                    onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 99999999, 255, true, false, false));
                    Utils.sendMessageAfter(onlinePlayer, "&c[YOUTUBE] &fAlgorithm: &eWhy don't you give up on what you're trying to do right now?", 20);

                    Wither wither = (Wither) onlinePlayer.getWorld().spawnEntity(new Location(Bukkit.getWorld("world"), 332.5, 119, 30.5, 180, 0), EntityType.WITHER);
                    wither.setInvulnerable(true);
                    wither.setCustomName("§c[YOUTUBE] §fAlgorithm");
                    wither.setCustomNameVisible(true);
                    wither.getBossBar().removeAll();
                    wither.setAI(false);

                    Utils.sendMessageAfter(onlinePlayer, "&c[YOUTUBE] &fAlgorithm: &f&lOr, run away from me.", 100);
                    new BukkitRunnable() {
                        public void run() {
                            onlinePlayer.removePotionEffect(PotionEffectType.SLOW);
                            new BukkitRunnable() {
                                public void run() {
                                    wither.remove();
                                    for (int i = 0; i < 10; ++i) wither.getWorld().spawnParticle(Particle.SMOKE_LARGE, wither.getLocation(), 10, Math.sin(new Random().nextInt(2)), 0, Math.tan(new Random().nextInt(2)));
                                    wither.getWorld().playSound(wither.getLocation(), Sound.ENTITY_WITHER_SPAWN, 10, 2);
                                    bot = Bot.createBot(new Location(Bukkit.getWorld("world"), 332.5, 119, 30.5, 180, 0), "§c§lALGORITHM", MojangAPI.getSkin("NightMob"));
                                    bot.setItem(new ItemStack(Material.NETHERITE_HELMET), EquipmentSlot.HEAD);
                                    bot.setItem(new ItemStack(Material.NETHERITE_CHESTPLATE), EquipmentSlot.CHEST);
                                    bot.setItem(new ItemStack(Material.NETHERITE_LEGGINGS), EquipmentSlot.LEGS);
                                    bot.setItem(new ItemStack(Material.NETHERITE_BOOTS), EquipmentSlot.FEET);
                                    bot.setItem(new ItemStack(Material.NETHERITE_SWORD), EquipmentSlot.HAND);
                                    bot.setItem(new ItemStack(Material.NETHERITE_SWORD), EquipmentSlot.OFF_HAND);

                                    Bukkit.getWorld("world").spawnParticle(Particle.SMOKE_LARGE, bot.getLocation(), 10);
                                }
                            }.runTaskLater(MovingChannel.getInstance(), 40);
                        }
                    }.runTaskLater(MovingChannel.getInstance(), 150);
                }
            }
        }.runTaskLater(MovingChannel.getInstance(), 100);
    }

    public static void stop() {
        started = false;
        TerminatorPlusAPI.getBotManager().reset();
        for (Entity current : Bukkit.getWorld("world").getEntities()) if (current instanceof Item || current instanceof Arrow || current instanceof TNTPrimed) current.remove();
        Listeners.used.clear();
    }

    public static void resetInventory(Player player) {
        ItemStack[] pi = playerInventoryHashMap.get(player);
        ItemStack[] pi2 = playerArmorContentHashMap.get(player);
        player.getInventory().setContents(pi);
        player.getInventory().setArmorContents(pi2);
        player.updateInventory();
    }

}
