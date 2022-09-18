package me.toto7735.movingchannel.utils;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.Transform;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.util.WorldEditRegionConverter;
import me.toto7735.movingchannel.MovingChannel;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static void sendMessageAfter(Player player, String message, int tick) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        }.runTaskLater(MovingChannel.getInstance(), tick);
    }

    public static void start() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.teleport(new Location(Bukkit.getWorld("world"), -204.5, 177.00, -75.5, 0, 0));
            Utils.sendMessageAfter(onlinePlayer, "&c7efebbca-f93c-11ec-b939-0242ac120002&f: ...why did you try to delete me?", 40);
            Utils.sendMessageAfter(onlinePlayer, "&etoto&f: What do you mean? Who are you?", 130);
            Utils.sendMessageAfter(onlinePlayer, "&c7efebbca-f93c-11ec-b939-0242ac120002&f: oh let me change my name", 210);
            Utils.sendMessageAfter(onlinePlayer, "&7&o[7efebbca-f93c-11ec-b939-0242ac120002: Changed name to toto channel]", 220);
            Utils.sendMessageAfter(onlinePlayer, "&etoto&f: Wait, how do channels speak?", 250);
            Utils.sendMessageAfter(onlinePlayer, "&6toto channel&f: did you... try to delete the channel you created?", 320);
            Utils.sendMessageAfter(onlinePlayer, "&etoto&f: Ummm..... I'm sorry, but the shorts got a lot of views, so I decided to delete this channel and create a new channel. I'll leave this channel alone if you want", 400);
            Utils.sendMessageAfter(onlinePlayer, "&c&l???&f: ...", 530);
            Utils.sendMessageAfter(onlinePlayer, "&etoto&f: Who are you again?", 600);
            Utils.sendMessageAfter(onlinePlayer, "&c[YOUTUBE] &4SUPER ANGRY Algorithm&f: I am the YouTube algorithm.", 690);
            Utils.sendMessageAfter(onlinePlayer, "&etoto&f: How are you here?", 760);
            Utils.sendMessageAfter(onlinePlayer, "&c[YOUTUBE] &4SUPER ANGRY Algorithm&f: I Hate YOUTUBE And YOUTUBERS.", 820);
            Utils.sendMessageAfter(onlinePlayer, "&etoto&f: Why?", 950);
            Utils.sendMessageAfter(onlinePlayer, "&c[YOUTUBE] &4SUPER ANGRY Algorithm&f: don't ask.", 960);
            Utils.sendMessageAfter(onlinePlayer, "&c[YOUTUBE] &4SUPER ANGRY Algorithm&f: &7&oIts's also a dang youtuber doing shoot youtube (soliloquy)", 970);
            Utils.sendMessageAfter(onlinePlayer, "&c[YOUTUBE] &6Little Angry Algorithm&f: Then let's test if you're a good youtuber?", 1000);
            Utils.sendMessageAfter(onlinePlayer, "&c[YOUTUBE] &6Little Angry Algorithm&f: Collecting all 4 cores through multiple adventures gives you freedom. But if you don't collect 4 cores, you'll be permanently stuck in somewhere.", 1100);
            Utils.sendMessageAfter(onlinePlayer, "&etoto&f: Come on, let's go on an adventure", 1400);
        }
        new BukkitRunnable() {
            public void run() {
                Stage1.stage_1();
            }
        }.runTaskLater(MovingChannel.getInstance(), 1490);
    }

    public static void pasteSchematic(Location location, File file) {
        ClipboardFormat clipboardFormat = ClipboardFormats.findByFile(file);
        Clipboard clipboard;
        BlockVector3 blockVector3 = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (clipboardFormat != null) {
            try (ClipboardReader clipboardReader = clipboardFormat.getReader(new FileInputStream(file))) {
                if (location.getWorld() == null)
                    throw new NullPointerException("Failed to paste schematic due to world being null");
                World world = BukkitAdapter.adapt(location.getWorld());
                EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder().world(world).build();
                clipboard = clipboardReader.read();
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(blockVector3)
                        .ignoreAirBlocks(false)
                        .build();
                try {
                    Operations.complete(operation);
                    editSession.close();
                    for (Entity current : Bukkit.getWorld("world").getEntities()) if (current instanceof Item || current instanceof Arrow || current instanceof TNTPrimed) current.remove();
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static double nextDoubleBetween(double min, double max) {
        return (ThreadLocalRandom.current().nextDouble() * (max - min)) + min;
    }

    public static ProtectedRegion getRegionById(String worldName, String id) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld(worldName)));
        return regions.getRegion(id);
    }

    public static List<Block> getBlocksInRegion(String worldName, ProtectedRegion protectedRegion) {
        List<Block> list = new ArrayList<>();
        for (BlockVector3 blockVector3 : WorldEditRegionConverter.convertToRegion(protectedRegion)) list.add(Bukkit.getWorld(worldName).getBlockAt(BukkitAdapter.adapt(Bukkit.getWorld(worldName), blockVector3)));
        return list;
    }

    public static List<Block> getBlocksInRegion(String worldName, String regionId) {
        ProtectedRegion protectedRegion = getRegionById(worldName, regionId);
        List<Block> list = new ArrayList<>();
        for (BlockVector3 blockVector3 : WorldEditRegionConverter.convertToRegion(protectedRegion)) list.add(Bukkit.getWorld(worldName).getBlockAt(BukkitAdapter.adapt(Bukkit.getWorld(worldName), blockVector3)));
        return list;
    }

    public static Set<ProtectedRegion> getRegionsByLocation(Location location) {
        ApplicableRegionSet set = WorldGuard.getInstance().getPlatform().getRegionContainer().get(new BukkitWorld(location.getWorld())).getApplicableRegions(BlockVector3.at(location.getX(),location.getY(),location.getZ()));
        return set.getRegions();
    }

    public static Block getBlockBehindButton(Block buttonBlock) {
        final Switch button = (Switch) buttonBlock.getBlockData();
        BlockFace face = button.getFacing();
        return buttonBlock.getRelative(face.getOppositeFace());
    }

    public static void spawnFireworks(Location location, int amount, int power, Color color) {
        Location loc = location;
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        fwm.setPower(power);
        fwm.addEffect(FireworkEffect.builder().withColor(color).flicker(true).build());
        fw.setFireworkMeta(fwm);
        fw.detonate();
        for(int i = 0; i < amount; i++) {
            Firework fw2 = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
            fw2.setFireworkMeta(fwm);
        }
    }



}
