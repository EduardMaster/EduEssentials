package net.eduard.essentials.listener;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.essentials.EduEssentials;

public class CombatLog implements Listener {

    private static final Set<Player> players = new HashSet<>();

    @EventHandler
    public void aoSair(PlayerQuitEvent e) {

        Player player = e.getPlayer();
        if (!EduEssentials.getInstance().getBoolean("combatlog.enabled")) {
            return;
        }
        if (!players.contains(player)) return;
        player.damage(1000);
        players.remove(player);
        Bukkit.broadcastMessage(
                EduEssentials.getInstance().message("combat.quit")
                        .replace("$player", player.getName()));


    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void aoDigitarComandos(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if (!EduEssentials.getInstance().getBoolean("combatlog.enabled")) {
            return;
        }
        if (!players.contains(player)) return;
        for (String cmd : EduEssentials.getInstance().getConfigs().getMessages("combatlog.commands-permitted")) {
            if (e.getMessage().toLowerCase().startsWith(cmd.toLowerCase())) {
                return;
            }
        }
        e.setCancelled(true);
        player.sendMessage(EduEssentials.getInstance().message("combat.try-command"));


    }

    @EventHandler
    public void aoMorrer(PlayerDeathEvent e) {

        if (e.getEntity() == null) return;
        Player morreu = (Player) e.getEntity();
        if (e.getEntity().getKiller() != null) {
            players.remove(morreu);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void aoBater(EntityDamageByEntityEvent e) {
        if (!EduEssentials.getInstance().getBoolean("combatlog.enabled")) {
            return;
        }
        int time = EduEssentials.getInstance().getConfigs().getInt("combatlog.combat-seconds");
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;

        Player entity = (Player) e.getEntity();
        Player damager = (Player) e.getDamager();

        if (!players.contains(entity)) {

            players.add(entity);
            entity.sendMessage(EduEssentials.getInstance().message("combat.started"));

            new BukkitRunnable() {

                public void run() {

                    players.remove(entity);
                    entity.sendMessage(EduEssentials.getInstance().message("combat.out"));
                    entity.playSound(entity.getLocation(), Sound.LEVEL_UP, 1, 1);

                }
            }.runTaskLaterAsynchronously(EduEssentials.getInstance(), 20L * time);
        }

        if (!players.contains(damager)) {

            players.add(damager);
            damager.sendMessage(EduEssentials.getInstance().message("combat.started"));

            new BukkitRunnable() {

                public void run() {

                    players.remove(damager);
                    damager.sendMessage(EduEssentials.getInstance().message("combat.out"));
                    damager.playSound(damager.getLocation(), Sound.LEVEL_UP, 1, 1);
                    cancel();
                }
            }.runTaskLaterAsynchronously(EduEssentials.getInstance(), 20L * time);

        }


    }
}
