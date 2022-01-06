package net.eduard.essentials.core;

import java.util.ArrayList;
import java.util.List;

import net.eduard.api.lib.manager.EventsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.lib.modules.Mine;

public class FallManager extends EventsManager {

	/**
	 * Lista de jogador que nao v§o levar dano de queda
	 */
	private List<Player> players = new ArrayList<>();

	
	/**
	 * Alterador de evento, que ao jogador cair e estiver na lista nao cancela o dano
	 * @param event Entidade toma dano Evento
	 */
	@EventHandler
	public void onPlayerTakeDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (event.getCause() == DamageCause.FALL && players.contains(player)) {
				event.setCancelled(true);
				players.remove(player);
			}
		}
	}
	/**
	 * Manipulador de evento, que quando o jogador estiver no chao e nao estiver caindo remove da lista
	 * @param event Jogador anda evento
	 */
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (event.getTo().getBlock().equals(event.getTo().getBlock())) {
			if (Mine.isOnGround(player) && !Mine.isFalling(player)) {
				players.remove(player);
			}
		}
	}

	/**
	 * 
	 * @return a Lista de jogadores
	 */
	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}
