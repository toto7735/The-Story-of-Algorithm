package me.toto7735.movingchannel.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    static Map<UUID, Map<String, Long>> itemCoolDown = new HashMap<>();

    public static boolean getAndTellCoolDown(UUID uuid, String itemName) {
        if (getCoolDown(uuid, itemName) > 0) {
            int getCoolDown = getCoolDown(uuid, itemName);
            Bukkit.getPlayer(uuid).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§ctry again after " + getCoolDown + " seconds!"));
            Bukkit.getPlayer(uuid).playSound(Bukkit.getPlayer(uuid).getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            return false;
        }
        return true;
    }

    public static int getCoolDown(UUID uuid, String itemName) {
        Map<String, Long> map = new HashMap<>();
        map.put(itemName, System.currentTimeMillis());
        itemCoolDown.putIfAbsent(uuid, map);
        itemCoolDown.get(uuid).putIfAbsent(itemName, System.currentTimeMillis());
        return (int) (itemCoolDown.get(uuid).get(itemName) - System.currentTimeMillis()) / 1000;
    }

    public static void setCoolDown(UUID uuid, String itemName, int cooldown) {
        itemCoolDown.get(uuid).put(itemName, System.currentTimeMillis() + (cooldown * 1000L));
    }

}
