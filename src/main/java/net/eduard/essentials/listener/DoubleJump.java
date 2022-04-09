
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

import java.util.HashSet;
import java.util.Set;

public class DoubleJump extends EventsManager {

    private static final Set<Player> players = new HashSet<>();

    @EventHandler
    public void controlar(PlayerMoveEvent e) {
        Player gameMode = e.getPlayer();
        if (gameMode.getGameMode() == GameMode.CREATIVE) return;
        if (Mine.equals(e.getFrom(), e.getTo())) return;
        if (!EduEssentials.getInstance().getManager().getDoubleJumpSystem()) return;
        if (Mine.isOnGround(gameMode)) {
            if (gameMode.isOnGround() && gameMode.getVelocity().getY() > -0.1) {
                players.remove(gameMode);
                gameMode.setAllowFlight(true);
            }
            if (!players.contains(gameMode) && !Mine.isFalling(gameMode)) {
                gameMode.setAllowFlight(true);
            }
        } else if (Mine.isFlying(gameMode) & Mine.isFalling(gameMode)) {
            gameMode.setAllowFlight(false);
        }


    }

    @EventHandler
    public void habilitar(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        if (!(entity instanceof Player)|| e.getCause() != DamageCause.FALL) return;
        Player player = (Player) entity;
        if (!EduEssentials.getInstance().getBoolean("doublejump.enabled")) return;
        if (players.contains(player)) {
            e.setCancelled(true);
            player.setAllowFlight(true);
            players.remove(player);
        }


    }

    @EventHandler
    public void ativar(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;
        if (players.contains(player)) return;
        if (!EduEssentials.getInstance().getBoolean("doublejump.enabled")) return;
        EduEssentials.getInstance().getManager().getDoubleJumpEffect().create(player);
        players.add(player);
        e.setCancelled(true);
        player.setAllowFlight(false);


    }

}
