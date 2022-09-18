package me.toto7735.movingchannel.utils;

import me.toto7735.movingchannel.MovingChannel;
import me.toto7735.movingchannel.listeners.Listeners;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import net.nuggetmc.tplus.api.TerminatorPlusAPI;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Stage4 {

    public static boolean started;
    public static boolean sad;

    private static Map<Player, ItemStack[]> playerInventoryHashMap = new HashMap<>();
    private static Map<Player, ItemStack[]> playerArmorContentHashMap = new HashMap<>();

    public static void stage_4() {

        Bukkit.getWorld("map").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        Bukkit.getWorld("map").setTime(12600);
        started = true;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            Bukkit.getWorld("map").setSpawnLocation(new Location(Bukkit.getWorld("map"), -625.5, 69, -1208.5, 110, 0));
            playerInventoryHashMap.put(onlinePlayer, onlinePlayer.getInventory().getContents());
            playerArmorContentHashMap.put(onlinePlayer, onlinePlayer.getInventory().getArmorContents());
            onlinePlayer.sendTitle("§c§lAdventure FINAL", "§cBEAT THE ALGORITHM", 5, 30, 10);
            onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 0.5F);
            onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999, 0, true, false, false));
            onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 99999999, 255, true, false, false));
            onlinePlayer.setGameMode(GameMode.ADVENTURE);
            onlinePlayer.teleport(new Location(Bukkit.getWorld("map"), -625.5, 69, -1208.5, 110, 0));
            onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999, 0, true, false, false));
            Utils.sendMessageAfter(onlinePlayer, "&c[YOUTUBE?] &4SUPER ANGRY Algorithm&f: &7Finally... I can finally enter the YouTube headquarters. &7&l(from afar)", 20);

            Wither wither = (Wither) onlinePlayer.getWorld().spawnEntity(new Location(Bukkit.getWorld("map"), -974.5, 70, -1263.5, -60, 0), EntityType.WITHER);
            wither.setCustomName("§7[abandoned] §fAlgorithm");
            wither.setCustomNameVisible(true);
            wither.getBossBar().removeAll();
            wither.setAI(true);
        }
    }

    public static void sad() {
        sad = true;

        NPCRegistry registry = CitizensAPI.getNPCRegistry();

        NPC npc = registry.createNPC(EntityType.PLAYER, "&7[fired employee] &fMax", new Location(Bukkit.getWorld("map"), -950.5, 66, -1241.5, 122, 0));
        ((SkinnableEntity) npc.getEntity()).setSkinName("semmaas");

        NPC npc2 = registry.createNPC(EntityType.PLAYER, "", new Location(Bukkit.getWorld("map"), -950.5, 66, -1241.5, 122, 0));
        ((SkinnableEntity) npc2.getEntity()).setSkinName("semmaas");
        npc2.spawn(new Location(Bukkit.getWorld("map"), -950.5, 66, -1241.5, 122, 0));
        new BukkitRunnable() {
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc2.getId());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc invisible");
                npc2.getNavigator().setTarget(new Location(Bukkit.getWorld("map"), -964, 67, -1258.5, 110, 0));
            }
        }.runTaskLater(MovingChannel.getInstance(), 1);

        new BukkitRunnable() {
            public void run() {
                npc.spawn(new Location(Bukkit.getWorld("map"), -950.5, 66, -1241.5, 122, 0));
                new BukkitRunnable() {
                    public void run() {
                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            Utils.sendMessageAfter(onlinePlayer, "&7[fired employee] &fMax&f: STOP MY FRIEND!", 100);
                            Utils.sendMessageAfter(onlinePlayer, "&c[YOUTUBE?] &4SUPER ANGRY Algorithm&f: WHO ARE YOU?", 150);
                            Utils.sendMessageAfter(onlinePlayer, "&c[YOUTUBE?] &fAlgorithm&f: Wait.... are you... Max?", 200);
                            new BukkitRunnable() {
                                public void run() {

                                    for (Entity map : Bukkit.getWorld("map").getEntities()) {
                                        if (map.getType().equals(EntityType.WITHER)) ((LivingEntity) map).setAI(false);
                                    }
                                }
                            }.runTaskLater(MovingChannel.getInstance(), 200);
                            Utils.sendMessageAfter(onlinePlayer, "&7[fired employee] &fMax&f: yeah your best friend", 250);
                            Utils.sendMessageAfter(onlinePlayer, "&c[YOUTUBE?] &fAlgorithm&f: i need revenge on youtube", 350);
                            Utils.sendMessageAfter(onlinePlayer, "&7[fired employee] &fMax&f: I understand how you feel, but revenge on YouTube is not a good idea", 450);
                            Utils.sendMessageAfter(onlinePlayer, "&c[YOUTUBE?] &fAlgorithm&f: But...", 570);
                            Utils.sendMessageAfter(onlinePlayer, "&etoto&f: What happend???", 650);
                            new BukkitRunnable() {
                                public void run() {
                                    Utils.sendMessageAfter(onlinePlayer, "&7[fired employee] &fMax&f: I understand how you feel, but revenge on YouTube is not a good idea", 0);
                                    Utils.sendMessageAfter(onlinePlayer, "&7[fired employee] &fMax&f: The act of revenge makes your heart ache more, look at the sky. The world is made of love.", 120);
                                    Utils.sendMessageAfter(onlinePlayer, "&7[fired employee] &fMax&f: So please you too stop revenge my friend...", 250);
                                    Utils.sendMessageAfter(onlinePlayer, "&etoto&f: WAIT He is breaking himself", 270);
                                    Utils.sendMessageAfter(onlinePlayer, "&7[fired employee] &fMax&f: my dude...", 330);
                                    Utils.sendMessageAfter(onlinePlayer, "&7[fired employee] &fMax&f: why are you pouring yourself out?", 400);
                                    Utils.sendMessageAfter(onlinePlayer, "&7[fired employee] &fMax&f: you've been struggling........", 460);
                                    Utils.sendMessageAfter(onlinePlayer, "&7[fired employee] &fMax&f: Without you, YouTube wouldn't be as big as it is today.", 500);
                                    Utils.sendMessageAfter(onlinePlayer, "&7[fired employee] &fMax&f: Good Bye, My Best Friend.", 560);


                                    new BukkitRunnable() {
                                        public void run() {
                                            for (Entity map : Bukkit.getWorld("map").getEntities()) {
                                                if (map.getType().equals(EntityType.WITHER)) map.remove();
                                            }
                                            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                                onlinePlayer.sendTitle("§6§lCongratulation! ", "§fYou can move your channel now!", 5, 200, 10);
                                                onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                                                Utils.spawnFireworks(onlinePlayer.getLocation().clone().add(0, 1, 0), 1, 1, Color.RED);
                                                Utils.spawnFireworks(onlinePlayer.getLocation().add(0, 1, 0), 1, 1, Color.ORANGE);
                                                Utils.spawnFireworks(onlinePlayer.getLocation().add(0, 1, 0), 1, 1, Color.YELLOW);
                                                Utils.spawnFireworks(onlinePlayer.getLocation().add(0, 1, 0), 1, 1, Color.LIME);
                                            }
                                        }
                                    }.runTaskLater(MovingChannel.getInstance(), 800);
                                }
                            }.runTaskLater(MovingChannel.getInstance(), 850);
                        }

                        npc.getNavigator().setTarget(new Location(Bukkit.getWorld("map"), -964, 67, -1258.5, 110, 0));
                    }
                }.runTaskLater(MovingChannel.getInstance(), 1);
            }
        }.runTaskLater(MovingChannel.getInstance(), 40);

    }

    public static void stop() {
        for (Entity current : Bukkit.getWorld("world").getEntities()) if (current instanceof Item || current instanceof Arrow || current instanceof TNTPrimed) current.remove();

    }

    public static void resetInventory(Player player) {
        ItemStack[] pi = playerInventoryHashMap.get(player);
        ItemStack[] pi2 = playerArmorContentHashMap.get(player);
        player.getInventory().setContents(pi);
        player.getInventory().setArmorContents(pi2);
        player.updateInventory();
    }

}

