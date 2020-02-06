
package net.eduard.essentials.events;

import java.util.HashMap;
import java.util.Map;


import net.eduard.api.lib.modules.MineReflect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.EventsManager;

public class ComboCounter extends EventsManager {

	@EventHandler
	public void event(PlayerMoveEvent e) {

	}

	@EventHandler
	public void event(PlayerJoinEvent e) {
	}
	private static Map<Player, Integer> combos = new HashMap<>();

	public static Map<Player, Integer> getCombos() {
		return combos;
	}

	private static Map<Player, Long> ultimoTapa = new HashMap<>();

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player entity = (Player) e.getEntity();
			if (getCombos().containsKey(entity)) {
				entity.sendMessage("§aSeu combo final foi este: §2"
						+ getCombos().get(entity));
			getCombos().put(entity, 0);

			}

		}
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			
			if (getCombos().containsKey(p)) {
				if (ultimoTapa.containsKey(p)) {
					Long tempo = ultimoTapa.get(p);
					long agora = Mine.getNow();
					if (tempo + 50 * 1 > agora) {
						return;
					}
				}
				int combo = 1;
			getCombos().put(p,
						combo = (getCombos().get(p) + 1));
				MineReflect.sendActionBar(p, "§aVoce acertou mais um combo! §cCombo atual: " + combo);
				ultimoTapa.put(p, System.currentTimeMillis());
			}

		}
	}

}
