
package net.eduard.essentials.command.admin;

import java.util.ArrayList;
import java.util.List;

import net.eduard.api.lib.game.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.game.Jump;
import net.eduard.api.lib.game.SoundEffect;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.api.lib.menu.Slot;
import net.eduard.api.lib.modules.VaultAPI;

public class AdminCommand extends CommandManager {

    public AdminCommand() {
        super("admin", "adm");
    }

    public void prison(Player player) {
        players.add(player);

        Location loc = player.getLocation();
        loc = loc.add(0, 10, 0);

        player.playSound(player.getLocation(), Sound.WITHER_SPAWN, 2, 1);
        loc.clone().add(0, 0, 0).getBlock().setType(Material.BEDROCK);
        loc.clone().add(0, 3, 0).getBlock().setType(Material.BEDROCK);
        loc.clone().add(0, 1, -1).getBlock().setType(Material.BEDROCK);
        loc.clone().add(-1, 1, 0).getBlock().setType(Material.BEDROCK);
        loc.clone().add(1, 1, 0).getBlock().setType(Material.BEDROCK);
        loc.clone().add(0, 1, 1).getBlock().setType(Material.BEDROCK);
        player.teleport(loc.clone().add(-0.4, 1, -0.4));
        player.sendMessage("§cVoce foi Aprisionado!");
    }

    public void removePrison(Player player) {
        players.remove(player);
        Location loc = player.getLocation();
        player.sendMessage("§aVoce foi liberto da Prisão!");
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 1);
        loc.clone().add(0, -1, 0).getBlock().setType(Material.AIR);
        loc.clone().add(0, 2, 0).getBlock().setType(Material.AIR);
        loc.clone().add(0, 0, 1).getBlock().setType(Material.AIR);
        loc.clone().add(1, 0, 0).getBlock().setType(Material.AIR);
        loc.clone().add(0, 0, -1).getBlock().setType(Material.AIR);
        loc.clone().add(-1, 0, 0).getBlock().setType(Material.AIR);

    }

    public static List<Player> players = new ArrayList<>();
    public Jump jumpEffect = new Jump(SoundEffect.create("BAT_LOOP"),
            new Vector(0, 3, 0));
    public String messageOn = "Voce entrou no Modo Admin!";
    public String messageOff = "Voce saiu do Modo Admin!";

    public Slot testAutoSoup = new Slot(
            Mine.newItem(Material.ENDER_PEARL, "Testar Auto-SoupSystem"), 3);

    public Slot testFF = new Slot(
            Mine.newItem(Material.MAGMA_CREAM, "Ativar Troca Rapida"), 2);

    public Slot testPrison = new Slot(
            Mine.newItem(Material.MAGMA_CREAM, "Aprisionar Jogador"), 1);

    public Slot testNoFall = new Slot(
            Mine.newItem(Material.FEATHER, "Testar No-Fall"), 4);

    public Slot testInfo = new Slot(
            Mine.newItem(Material.BLAZE_ROD, "Ver Informações"), 5);

    public Slot testAntKB = new Slot(
            new ItemBuilder(Material.STICK).name("Testar Knockback").addEnchant(
                    Enchantment.KNOCKBACK, 10),
            6);

    public void joinAdminMode(Player player) {
        Mine.saveItems(player);
        Mine.hide(player);
        players.add(player);
        PlayerInventory inv = player.getInventory();
        testAutoSoup.give(inv);
        testFF.give(inv);
        testNoFall.give(inv);
        testInfo.give(inv);
        testAntKB.give(inv);
        testPrison.give(inv);

        player.setGameMode(GameMode.CREATIVE);

    }

    public void leaveAdminMode(Player player) {
        Mine.getItems(player);
        Mine.show(player);
        player.setGameMode(GameMode.SURVIVAL);
        players.remove(player);
    }

    @EventHandler
    public void testNoFall(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        if (player.getItemInHand() != testNoFall.getItem()) return;
        if (e.getRightClicked() instanceof Player) {
            Player target = (Player) e.getRightClicked();
            if (players.contains(player)) {
                jumpEffect.create(target);
            }
        }
    }

    @EventHandler
    public void testFF(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getItemInHand() != testFF.getItem()) return;
        if (players.contains(player)) {
            Mine.show(player);
            Mine.makeInvunerable(player, 1);
            player.sendMessage("§6Troca rapida ativada!");
            new BukkitRunnable() {

                @Override
                public void run() {
                    Mine.hide(player);
                    player.sendMessage("§6Troca rapida desativada!");
                }
            }.runTaskLaterAsynchronously(getPlugin(), 20);

        }
    }

    @EventHandler
    public void testAutoSoup(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        if (player.getItemInHand() != testAutoSoup.getItem()) return;
        if (e.getRightClicked() instanceof Player) {
            Player target = (Player) e.getRightClicked();
            if (players.contains(player)) {
                player.openInventory(target.getInventory());

            }
        }
    }

    @EventHandler
    public void testInfo(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        if (player.getItemInHand() != testInfo.getItem()) return;
        if (e.getRightClicked() instanceof Player) {
            Player target = (Player) e.getRightClicked();
            if (players.contains(player)) {
                player.sendMessage("§6Informações do §e" + target.getName());
                player.sendMessage("§aGamemode: §2" + target.getGameMode());
                player.sendMessage("§aKills: §2"
                        + target.getStatistic(Statistic.PLAYER_KILLS));
                player.sendMessage("§aDeaths: §2"
                        + target.getStatistic(Statistic.DEATHS));
                player.sendMessage("§aIP: §2" + Mine.getIp(player));
                if (VaultAPI.hasVault() && VaultAPI.hasEconomy()) {
                    player.sendMessage("§aMoney: §2"
                            + VaultAPI.getEconomy().getBalance(player));
                }

            }
        }
    }

    @EventHandler
    public void testPrison(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        if (player.getItemInHand() != testPrison.getItem()) return;
        if (!(e.getRightClicked() instanceof Player)) return;
        Player target = (Player) e.getRightClicked();
        if (players.contains(player)) {

            Location loc = target.getLocation();
            loc.clone().add(0, 10, 0).getBlock()
                    .setType(Material.BEDROCK);
            loc.clone().add(0, 11, 1).getBlock()
                    .setType(Material.BEDROCK);
            loc.clone().add(0, 11, -1).getBlock()
                    .setType(Material.BEDROCK);
            loc.clone().add(1, 11, 0).getBlock()
                    .setType(Material.BEDROCK);
            loc.clone().add(-1, 11, 0).getBlock()
                    .setType(Material.BEDROCK);
            loc.clone().add(0, 13, 0).getBlock()
                    .setType(Material.BEDROCK);
            target.teleport(loc.add(0, 11, 0));

        }


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {
        if (Mine.onlyPlayer(sender)) {
            Player player = (Player) sender;
            if (players.contains(player)) {
                players.remove(player);
                leaveAdminMode(player);
                player.sendMessage(messageOff);
            } else {
                players.add(player);
                joinAdminMode(player);
                player.sendMessage(messageOn);
            }

        }
        return true;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            if (players.contains(player)) {
                e.setCancelled(false);
            }

        }
    }

}
