
package net.eduard.essentials.listener;

import net.eduard.essentials.EduEssentials;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.EventsManager;

import java.util.ArrayList;

public class DoubleJump extends EventsManager {

    private static final ArrayList<Player> players = new ArrayList<>();


    @EventHandler
    public void controlar(PlayerMoveEvent e) {
        Player gameMode = e.getPlayer();
        if (gameMode.getGameMode() != GameMode.CREATIVE) {

            if (!Mine.equals(e.getFrom(), e.getTo())) {
                if (EduEssentials.getInstance().getBoolean("doublejump.enabled")) {
                    if (Mine.isOnGround(gameMode)) {
                        if (gameMode.isOnGround() && gameMode.getVelocity().getY() > -0.1) {
                            players.remove(gameMode);
                            gameMode.setAllowFlight(true);
                        }
                        if (!players.contains(gameMode) && !Mine.isFalling(gameMode)) {
                            gameMode.setAllowFlight(true);
                        }
                    } else if (Mine
                            .isFlying(gameMode) & Mine.isFalling(gameMode)) {
                        gameMode.setAllowFlight(false);
                    }
                }
            }
        }

    }

    @EventHandler
    public void habilitar(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof Player & e.getCause() == DamageCause.FALL) {
            Player p = (Player) entity;

            if (players.contains(p)) {
                e.setCancelled(true);
                p.setAllowFlight(true);
                players.remove(p);
            }
        }

    }

    @EventHandler
    public void ativar(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE) {

            if (!players.contains(p)) {
                if (EduEssentials.getInstance().getBoolean("doublejump.enabled")) {
                    EduEssentials.getInstance().getDoubleJump().create(p);
                    players.add(p);
                    e.setCancelled(true);
                    p.setAllowFlight(false);
                }
            }

        }

    }

}
