package net.eduard.essentials.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.EventsManager;
import org.bukkit.scheduler.BukkitRunnable;

public class ClickCounter extends EventsManager {
    private static Map<Player, Integer> clicks = new HashMap<>();

    public static Map<Player, Integer> getClicks() {
        return clicks;
    }


    public ClickCounter() {

        new BukkitRunnable() {

            @Override
            public void run() {
                show();
            }
        }.runTaskTimerAsynchronously(getPlugin(), 20, 20);
    }

    public void show() {
        for (Player player : Mine.getPlayers()) {
            if (clicks.containsKey(player)) {
                player.sendMessage(
                        "§aSeus Clicks: §e(" + clicks.getOrDefault(player, 0) + "/Segundo)");
                clicks.put(player, 0);
            }
        }
    }

    @EventHandler
    public void event(EntityDamageByEntityEvent e) {

        if (!(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getDamager();
        if (clicks.containsKey(p)) {
            Integer click = clicks.get(p);
            if (click == null) {
                click = 0;
            }
            clicks.put(p, click + 1);
        }


    }

    @EventHandler
    public void event(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!e.getAction().name().contains("LEFT")) return;
        if (clicks.containsKey(p)) {
            Integer click = clicks.get(p);
            if (click == null) {
                click = 0;
            }
            clicks.put(p, click + 1);
        }

    }

}
