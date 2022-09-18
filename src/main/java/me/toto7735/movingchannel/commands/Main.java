package me.toto7735.movingchannel.commands;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class  Main extends JavaPlugin implements Listener {

    public Main() {
    }

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("상자").setExecutor(this);
        System.out.println(ChatColor.YELLOW + "Chris Plugin load");
    }

    public void onDisable() {
        System.out.println(ChatColor.YELLOW + "Chris Plugin unload");
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.sendMessage(ChatColor.GREEN + p.getName() + " 님이 입장하였습니다");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        Inventory i = Bukkit.createInventory(null, 27, ChatColor.AQUA + "메뉴");
        if(args.length == 0) {
            p.sendMessage("/상자 열기");
        } else {
            if(args.length == 1) {
                if(args[0].equals("열기")) {
                    ItemStack itemStack = new ItemStack(Material.DIAMOND); // 새 ItemStack 생성
                    ItemMeta itemMeta = itemStack.getItemMeta(); // ItemMeta GET
                    itemMeta.setDisplayName("Test Diamond");
                    itemStack.setItemMeta(itemMeta); // ItemMeta 설정
                    i.setItem(10, itemStack);
                    p.openInventory(i);
                }
            }
        }
        return false;
    }
}
