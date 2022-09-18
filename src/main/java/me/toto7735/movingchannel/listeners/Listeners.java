package me.toto7735.movingchannel.listeners;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.toto7735.movingchannel.MovingChannel;
import me.toto7735.movingchannel.utils.*;
import net.nuggetmc.tplus.api.TerminatorPlusAPI;
import net.nuggetmc.tplus.api.event.BotDamageByPlayerEvent;
import net.nuggetmc.tplus.api.utils.MojangAPI;
import net.nuggetmc.tplus.bot.Bot;
import net.raidstone.wgevents.events.RegionEnteredEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Listeners implements Listener {

    @EventHandler
    public void onChange(EntityChangeBlockEvent event) {
        if (Stage1.started) {
            if (!Stage1.destories.contains(event.getBlock().getType())) return;
            if (new Random().nextInt(3) == 0) {
                event.getBlock().getLocation().getBlock().setType(Material.AIR);
                event.getBlock().getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
                event.getBlock().getLocation().add(0, -1, 0).getBlock().setType(Material.AIR);
                event.getBlock().getLocation().getWorld().playSound(event.getBlock().getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 0.1F, 1);
            }
        } else if (Stage3.started) {
            event.setCancelled(true);
//            event.getEntity().remove();
        }
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        if (!Stage1.started) return;
        if (!Stage1.destories.contains(event.getEntity().getItemStack().getType())) return;
        List<Entity> ents = event.getEntity().getNearbyEntities(2, 2, 2);
        for (Entity e : ents) {
            e.getLocation().getBlock().setType(Material.AIR);
            e.getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
            e.getLocation().add(0, -1, 0).getBlock().setType(Material.AIR);
            e.getWorld().playSound(e.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 0.1F, 1);
            return;
        }
        event.getEntity().remove();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (Stage1.started) {
            if (!event.getTo().clone().add(0, -1, 0).getBlock().getType().equals(Material.EMERALD_BLOCK)) return;
            Stage1.stop();
            Stage1.pass(event.getPlayer());
        } else if (Road1.started) {
            if (!event.getTo().clone().add(0, -1, 0).getBlock().getType().equals(Material.PURPLE_STAINED_GLASS)) return;
            Road1.stop();
            Road1.pass();
        } else if (Stage3.started) {
            if (!event.getTo().clone().add(0, -1, 0).getBlock().getType().equals(Material.RED_CONCRETE)) return;
            event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 46.5, 174.00, 8.5, 0, 0));
            Stage3.stop();
            Stage3.resetInventory(event.getPlayer());
            Stage3.stage_3();
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (!event.getEntity().getName().equals("toto7735")) return;
        if (Stage1.started) {
            Stage1.stop();
            Stage1.resetInventory(event.getEntity());
            Stage1.stage_1();
        } else if (Road1.started) {
            Road1.stop();
            Road1.resetInventory(event.getEntity());
            Road1.start();
        } else if (Stage2.started) {
            Stage2.stop();
            Stage2.resetInventory(event.getEntity());
            Stage2.stage_2();
        } else if (Stage3.started) {
            Stage3.stop();
            Stage3.resetInventory(event.getEntity());
            Stage3.stage_3();
        }
    }

    @EventHandler
    public void onTNT(ExplosionPrimeEvent event) {
        if (!Stage1.started) return;
        event.getEntity().getWorld().spawnParticle(Particle.EXPLOSION_HUGE, event.getEntity().getLocation(), 1);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (Stage1.started) {
            event.setRespawnLocation(new Location(Bukkit.getWorld("world"), 46.5, 174, 8.5, 0, 0));
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDeath2(PlayerDeathEvent event) {
        if (!event.getEntity().getName().equals("§c[YT] §0Load")) return;
        event.getEntity().getKiller().sendTitle("", "§eCreate a §5nether portal §eto enter the §5nether§e!", 5, 30, 10);
        event.getEntity().getKiller().playSound(event.getEntity().getKiller().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
    }


    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
        if (!Stage2.started) return;
        event.setCancelled(true);
        Stage2.stop();
        Stage3.stage_3();
    }


    public static List<String> used = new ArrayList<>();

    // Stage 3
    @EventHandler
    public void onRegionEntered(RegionEnteredEvent event) {
        if (event.getPlayer() == null) return;
        World world = event.getPlayer().getWorld();
        Player player = event.getPlayer();
        if (used.contains(event.getRegion().getId())) return;
        switch (event.getRegion().getId()) {
            case "stage_3_1" -> {
                player.teleport(new Location(player.getWorld(), 332.5, 119, 8.5, 180, 0));
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 10, 1, Math.cos(1), 1, Math.sin(1));

                doorOpen(player.getWorld().getName(), "stage_3_wall_1");

                player.sendTitle("§6§lCheck Your Inventory!", "§eYou got §bSomething§e!", 5, 100, 10);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                ItemStack itemStack = new ItemStack(Material.POTION);
                PotionMeta itemMeta = (PotionMeta) itemStack.getItemMeta();
                itemMeta.setColor(Color.RED);
                itemMeta.setDisplayName("§c§lHeal Potion");
                itemMeta.setLore(Arrays.asList("§7A heal potion.", "", "§e§lRight-Click", "§7Heal §c3❤"));
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemStack.setItemMeta(itemMeta);
                player.getInventory().addItem(itemStack);
            }
            case "stage_3_2" -> {
                doorBreak(player.getWorld().getName(), "stage_3_wall_4");
                Utils.spawnFireworks(player.getLocation(), 3, 2, Color.WHITE);
                Utils.spawnFireworks(player.getLocation(), 3, 2, Color.RED);
                new BukkitRunnable() {
                    public void run() {
                        doorOpen(player.getWorld().getName(), "stage_3_wall_5");
                    }
                }.runTaskLater(MovingChannel.getInstance(), 5);

                player.sendTitle("§6§lCheck Your Inventory!", "§eYou got §bSomething§e!", 5, 100, 10);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                ItemStack itemStack = new ItemStack(Material.POTION);
                PotionMeta itemMeta = (PotionMeta) itemStack.getItemMeta();
                itemMeta.setColor(Color.RED);
                itemMeta.setDisplayName("§c§lHeal Potion");
                itemMeta.setLore(Arrays.asList("§7A heal potion.", "", "§e§lRight-Click", "§7Heal §c3❤"));
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemStack.setItemMeta(itemMeta);
                player.getInventory().addItem(itemStack);
            }
            case "stage_3_3" -> {
                doorBreak(player.getWorld().getName(), "stage_3_wall_9");
                Utils.spawnFireworks(player.getLocation(), 3, 2, Color.WHITE);
                Utils.spawnFireworks(player.getLocation(), 3, 2, Color.ORANGE);

                player.sendTitle("§6§lCheck Your Inventory!", "§eYou got §d§lSomething Special§e!", 5, 100, 10);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                ItemStack itemStack = new ItemStack(Material.CORNFLOWER);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName("§9§lParticler");
                itemMeta.setLore(Arrays.asList("§7Beautiful flower that can emit particles.", "", "§e§lLeft-Click", "§7Summons §9particles §7that do §c3 damage §7if hit!"));
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemStack.setItemMeta(itemMeta);
                player.getInventory().addItem(itemStack);
            }
            case "stage_3_4" -> {
                doorBreak(player.getWorld().getName(), "stage_3_wall_10");
                Utils.spawnFireworks(player.getLocation(), 3, 2, Color.WHITE);
                Utils.spawnFireworks(player.getLocation(), 3, 2, Color.YELLOW);

                player.sendTitle("§6§lCheck Your Inventory!", "§eYou got §bSomething§e!", 5, 100, 10);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                ItemStack itemStack = new ItemStack(Material.POTION);
                PotionMeta itemMeta = (PotionMeta) itemStack.getItemMeta();
                itemMeta.setColor(Color.RED);
                itemMeta.setDisplayName("§c§lHeal Potion");
                itemMeta.setLore(Arrays.asList("§7A heal potion.", "", "§e§lRight-Click", "§7Heal §c3❤"));
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemStack.setItemMeta(itemMeta);
                player.getInventory().addItem(itemStack);
            }
            case "stage_3_5" -> {
                doorBreak(player.getWorld().getName(), "stage_3_wall_11");
                Utils.spawnFireworks(player.getLocation(), 3, 2, Color.WHITE);
                Utils.spawnFireworks(player.getLocation(), 3, 2, Color.LIME);

                player.sendTitle("§6§lCheck Your Inventory!", "§eYou got §bSomething§e!", 5, 100, 10);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                ItemStack itemStack = new ItemStack(Material.POTION);
                PotionMeta itemMeta = (PotionMeta) itemStack.getItemMeta();
                itemMeta.setColor(Color.RED);
                itemMeta.setDisplayName("§c§lHeal Potion");
                itemMeta.setLore(Arrays.asList("§7A heal potion.", "", "§e§lRight-Click", "§7Heal §c3❤"));
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemStack.setItemMeta(itemMeta);
                player.getInventory().addItem(itemStack);
            }
            case "stage_3_6" -> {
                doorBreak(player.getWorld().getName(), "stage_3_wall_12");
                Utils.spawnFireworks(player.getLocation(), 3, 2, Color.WHITE);
                Utils.spawnFireworks(player.getLocation(), 3, 2, Color.AQUA);

                player.sendTitle("§6§lCheck Your Inventory!", "§eYou got §d§lSomething Special§e!", 5, 100, 10);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                ItemStack itemStack = new ItemStack(Material.YELLOW_CANDLE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName("§e§lCheese TNT");
                itemMeta.setLore(Arrays.asList("§7Delicious looking cheese bomb.", "", "§e§lRight-Click", "§7Jump up and deal §c20 damage", "§7to creatures within §e5 blocks§7!"));
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemStack.setItemMeta(itemMeta);
                player.getInventory().addItem(itemStack);
            }
            case "stage_3_7" -> {
                doorBreak(player.getWorld().getName(), "stage_3_wall_21");
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 999999, 10, true, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 255, true, false, false));
                Utils.spawnFireworks(player.getLocation(), 3, 2, Color.WHITE);
                Utils.spawnFireworks(player.getLocation(), 3, 2, Color.PURPLE);

                new BukkitRunnable() {
                    public void run() {
                        Stage3.stop();
                        player.removePotionEffect(PotionEffectType.BLINDNESS);
                        player.removePotionEffect(PotionEffectType.LEVITATION);
                        Stage4.stage_4();
                    }
                }.runTaskLater(MovingChannel.getInstance(), 70);
            }
            case "stage_3_bot_1" -> {
                TerminatorPlusAPI.getBotManager().reset();
                Stage3.bot = Bot.createBot(new Location(Bukkit.getWorld("world"), 336.5, 120, -66.5, -90, 0), "§c§lALGORITHM", MojangAPI.getSkin("NightMob"));
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_HELMET), EquipmentSlot.HEAD);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_CHESTPLATE), EquipmentSlot.CHEST);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_LEGGINGS), EquipmentSlot.LEGS);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_BOOTS), EquipmentSlot.FEET);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_SWORD), EquipmentSlot.HAND);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_SWORD), EquipmentSlot.OFF_HAND);
                for (int i = 0; i < 5; ++i) Bukkit.getWorld("world").spawnParticle(Particle.PORTAL, Stage3.bot.getLocation(), 10, Math.sin(3), Math.cos(3), Math.tan(3));
            }
            case "stage_3_bot_2" -> {
                TerminatorPlusAPI.getBotManager().reset();
                Stage3.bot = Bot.createBot(new Location(Bukkit.getWorld("world"), 438.5, 119, -66.5, -90, 0), "§c§lALGORITHM", MojangAPI.getSkin("NightMob"));
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_HELMET), EquipmentSlot.HEAD);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_CHESTPLATE), EquipmentSlot.CHEST);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_LEGGINGS), EquipmentSlot.LEGS);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_BOOTS), EquipmentSlot.FEET);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_SWORD), EquipmentSlot.HAND);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_SWORD), EquipmentSlot.OFF_HAND);
                for (int i = 0; i < 5; ++i) Bukkit.getWorld("world").spawnParticle(Particle.PORTAL, Stage3.bot.getLocation(), 10, Math.sin(3), Math.cos(3), Math.tan(3));
            }
            case "stage_3_bot_3" -> {
                TerminatorPlusAPI.getBotManager().reset();
                Stage3.bot = Bot.createBot(new Location(Bukkit.getWorld("world"), 533.5, 19, -67, 0, 0), "§c§lALGORITHM", MojangAPI.getSkin("NightMob"));
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_HELMET), EquipmentSlot.HEAD);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_CHESTPLATE), EquipmentSlot.CHEST);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_LEGGINGS), EquipmentSlot.LEGS);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_BOOTS), EquipmentSlot.FEET);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_SWORD), EquipmentSlot.HAND);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_SWORD), EquipmentSlot.OFF_HAND);
                for (int i = 0; i < 5; ++i) Bukkit.getWorld("world").spawnParticle(Particle.PORTAL, Stage3.bot.getLocation(), 10, Math.sin(3), Math.cos(3), Math.tan(3));
            }
            case "stage_3_bot_4" -> {
                TerminatorPlusAPI.getBotManager().reset();
                Stage3.bot = Bot.createBot(new Location(Bukkit.getWorld("world"), 492.5, 119, -66.5, -90, 0), "§c§lALGORITHM", MojangAPI.getSkin("NightMob"));
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_HELMET), EquipmentSlot.HEAD);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_CHESTPLATE), EquipmentSlot.CHEST);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_LEGGINGS), EquipmentSlot.LEGS);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_BOOTS), EquipmentSlot.FEET);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_SWORD), EquipmentSlot.HAND);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_SWORD), EquipmentSlot.OFF_HAND);
                for (int i = 0; i < 5; ++i) Bukkit.getWorld("world").spawnParticle(Particle.PORTAL, Stage3.bot.getLocation(), 10, Math.sin(3), Math.cos(3), Math.tan(3));
            }
            case "stage_3_bot_5" -> {
                TerminatorPlusAPI.getBotManager().reset();
                Stage3.bot = Bot.createBot(new Location(Bukkit.getWorld("world"), 521.5, 119, -66.5, -90, 0), "§c§lALGORITHM", MojangAPI.getSkin("NightMob"));
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_HELMET), EquipmentSlot.HEAD);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_CHESTPLATE), EquipmentSlot.CHEST);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_LEGGINGS), EquipmentSlot.LEGS);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_BOOTS), EquipmentSlot.FEET);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_SWORD), EquipmentSlot.HAND);
                Stage3.bot.setItem(new ItemStack(Material.NETHERITE_SWORD), EquipmentSlot.OFF_HAND);
                for (int i = 0; i < 5; ++i) Bukkit.getWorld("world").spawnParticle(Particle.PORTAL, Stage3.bot.getLocation(), 10, Math.sin(3), Math.cos(3), Math.tan(3));
            }
            case "stage_4_1" -> {
                Location playerCenterLocation = new Location(player.getWorld(), -952.5, 66, -1242.5);
                Location playerToThrowLocation = player.getEyeLocation();

                double x = playerToThrowLocation.getX() - playerCenterLocation.getX();
                double y = playerToThrowLocation.getY() - playerCenterLocation.getY();
                double z = playerToThrowLocation.getZ() - playerCenterLocation.getZ();

                Vector throwVector = new Vector(x, y, z);

                throwVector.normalize();
                throwVector.multiply(1.5D);
                throwVector.setY(1.0D);

                player.setVelocity(throwVector);

                switch (new Random().nextInt(4)) {
                    case 0 -> {
                        Utils.sendMessageAfter(player, "&c[STAFF] &fJack: Get out of here.", 10);
                    }
                    case 1 -> {
                        Utils.sendMessageAfter(player, "&c[STAFF] &fEpisode: Why are you here?", 10);
                    }
                    case 2 -> {
                        Utils.sendMessageAfter(player, "&c[STAFF] &fJack: Get out of the way when I say nice things", 10);
                    }
                    case 3 -> {
                        Utils.sendMessageAfter(player, "&c[STAFF] &fEpisode: ... Get out.", 10);
                    }
                }
                return;
            }
        }
        used.add(event.getRegion().getId());
    }

    @EventHandler
    public void onHit(BlockRedstoneEvent event) {
        if (event.getBlock().getType().equals(Material.REDSTONE_LAMP)) {
            for (ProtectedRegion protectedRegion : Utils.getRegionsByLocation(event.getBlock().getLocation())) {
                boolean b = true;
                for (Block block : Utils.getBlocksInRegion(event.getBlock().getWorld().getName(), protectedRegion)) {
                    if (block.getType().equals(Material.REDSTONE_LAMP) && !block.isBlockPowered()) b = false;
                }
                if (b) {
                    doorBreak(event.getBlock().getWorld().getName(), protectedRegion.getId());
                }
            }
        } else if (event.getBlock().getType().equals(Material.STONE_BUTTON)) {
            for (ProtectedRegion protectedRegion : Utils.getRegionsByLocation(event.getBlock().getLocation())) {
                if (Utils.getBlocksInRegion(event.getBlock().getWorld().getName(), protectedRegion).isEmpty()) return;
                doorBreak(event.getBlock().getWorld().getName(), protectedRegion.getId());
            }
        }
    }

    private void doorBreak(String worldName, String regionId) {
        new BukkitRunnable() {
            public void run() {
                for (Block block : Utils.getBlocksInRegion(worldName, Utils.getRegionById(worldName, regionId))) {
                    block.getWorld().spawnParticle(Particle.BLOCK_CRACK, block.getLocation(), 20, Math.sin(1), 0, Math.cos(1), block.getBlockData());
                    block.setType(Material.AIR);
                    block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 0.5F, 1);
                }
            }
        }.runTaskLater(MovingChannel.getInstance(), 1);
    }

    private void doorOpen(String worldName, String regionId) {
        List<Block> list = new ArrayList<>();
        BlockData blockData = null;
        for (Block stage_3_wall_1 : Utils.getBlocksInRegion(worldName, regionId)) {
            blockData = stage_3_wall_1.getBlockData();
            stage_3_wall_1.setType(Material.AIR);
            list.add(stage_3_wall_1);
        }
        for (Block block : list) {
            FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation().clone().add(0.5, 0, 0.5), blockData);
            fallingBlock.setVelocity(new Vector(fallingBlock.getVelocity().getX(), fallingBlock.getVelocity().getY() * 0.001, fallingBlock.getVelocity().getZ()));
        }
        World world = Bukkit.getWorld(worldName);
        new BukkitRunnable() {
            int i = 0;
            public void run() {
                if (i >= 15) this.cancel();
                world.playSound(new Location(world, 332.5, 119, 1.5), Sound.BLOCK_IRON_TRAPDOOR_OPEN, 10, 0.5F);
                world.playSound(new Location(world, 332.5, 119, 1.5), Sound.BLOCK_CHEST_OPEN, 10, 0.5F);
                ++i;
            }
        }.runTaskTimerAsynchronously(MovingChannel.getInstance(), 0, 1);
    }

    @EventHandler
    public void onBotDamageByPlayer(BotDamageByPlayerEvent event) {
        if (event.getBot().getBotName().equals("§c§lALGORITHM")) event.setCancelled(true);
    }

    // stage 4

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
//        if (!Stage4.started) return;
        if (!(event.getEntity() instanceof WitherSkull) || !(event.getEntity().getShooter() instanceof Wither) || Stage4.sad) return;
        if (((LivingEntity) event.getEntity().getShooter()).getHealth() <= ((LivingEntity) event.getEntity().getShooter()).getMaxHealth() / 2) {
            Stage4.sad();
            event.setCancelled(true);
            return;
        }
        WitherSkull skull = (WitherSkull) event.getEntity();
        if (new Random().nextInt(10) == 0) {
            for (double j = 0; j < 10; j += 0.5) {
                skull.getWorld().spawnParticle(Particle.SMOKE_LARGE, skull.getLocation(), 10, Math.cos(j), 0, Math.tan(j));
            }
            return;
        } else if (new Random().nextInt(10) == 1) {
            for (double j = 0; j < 10; j += 2) {
                skull.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, skull.getLocation(), 3, Math.sin(j), 0, Math.cos(j));
                for (Entity nearbyEntity : skull.getWorld().getNearbyEntities(skull.getLocation(), 20, 20, 20, entity -> !entity.getType().equals(EntityType.WITHER) && !entity.getType().equals(EntityType.ARMOR_STAND))) {
                    if (nearbyEntity instanceof LivingEntity) ((LivingEntity) nearbyEntity).damage(3);
                    nearbyEntity.getWorld().playSound(nearbyEntity.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 0.1F, 1);
                }
            }
        }
        ArmorStand armorStand = (ArmorStand) skull.getWorld().spawnEntity(skull.getLocation(), EntityType.ARMOR_STAND);
        armorStand.setInvisible(true);
        armorStand.setHelmet(new ItemStack(Material.NETHER_STAR));
        armorStand.setSmall(true);
        armorStand.setVelocity(skull.getVelocity());
        skull.remove();
        new BukkitRunnable() {
            public void run() {
                armorStand.getLocation().setYaw(armorStand.getEyeLocation().getYaw() + 5);
                armorStand.getLocation().setPitch(armorStand.getEyeLocation().getPitch() + 5);
                if (!armorStand.getLocation().clone().subtract(0, 1, 0).getBlock().getType().equals(Material.AIR)) {
                    armorStand.remove();
                    this.cancel();
                    return;
                }
                if (!armorStand.getWorld().getNearbyEntities(armorStand.getLocation(), 3, 3, 3, entity -> !entity.getType().equals(EntityType.WITHER) && !entity.getType().equals(EntityType.ARMOR_STAND)).isEmpty()) {
                    for (Entity nearbyEntity : armorStand.getWorld().getNearbyEntities(armorStand.getLocation(), 3, 3, 3, entity -> !entity.getType().equals(EntityType.WITHER) && !entity.getType().equals(EntityType.ARMOR_STAND))) {
                        if (nearbyEntity instanceof LivingEntity) ((LivingEntity) nearbyEntity).damage(1);
                    }
                    armorStand.remove();
                    this.cancel();
                    return;
                }
                for (int i = 0; i < 5; ++i) armorStand.getWorld().spawnParticle(Particle.PORTAL, armorStand.getLocation(), 1, Math.cos(1), 1, Math.sin(2));
            }
        }.runTaskTimer(MovingChannel.getInstance(), 0, 1);
        armorStand.getWorld().playSound(skull.getLocation(), Sound.ENTITY_WITHER_HURT, 2, 0.5F);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Firework)) return;
        event.setCancelled(true);
    }

}
