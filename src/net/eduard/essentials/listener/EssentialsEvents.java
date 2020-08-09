
package net.eduard.essentials.listener;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.EventsManager;
import net.eduard.essentials.EduEssentials;

public class EssentialsEvents extends EventsManager {
	private static final HashMap<Player, Long> lastCommand = new HashMap<>();
	@EventHandler
	public void onSignChangeEvent(SignChangeEvent e) {

		Player p = e.getPlayer();
		if (p.hasPermission("sign.color")) {
			for (int i = 0; i < e.getLines().length; i++) {
				e.getLines()[i] = ChatColor.translateAlternateColorCodes('&', e.getLines()[i]);
			}

		}
	}



	@EventHandler
	public void event(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (!p.hasPermission("command.delay.bypass")) {
			if (lastCommand.containsKey(p)) {
				Long teste = lastCommand.get(p);
				long agora = System.currentTimeMillis();
				boolean test = agora > (teste + 1000 * 3);
				if (!test) {
					p.sendMessage(EduEssentials.getInstance().message("command-cooldown"));
					e.setCancelled(true);

				} else {
					lastCommand.put(p, System.currentTimeMillis());
				}
			} else {
				lastCommand.put(p, System.currentTimeMillis());
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChatTabCompleteEvent(PlayerChatTabCompleteEvent e) {
		for (String msg : EduEssentials.getInstance().getMessages("blocked-tab-commads")) {
			if (e.getChatMessage().toLowerCase().startsWith(msg.toLowerCase())) {
				e.getTabCompletions().clear();
			}

		}

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		for (String msg : EduEssentials.getInstance().getMessages("blocked-commads")) {
			if (e.getMessage().toLowerCase().startsWith(msg.toLowerCase())) {
				e.setCancelled(true);
				p.sendMessage(Mine.MSG_NO_PERMISSION);
			}

		}

	}

	@EventHandler
	public void event(PlayerMoveEvent e) {

	}

	@EventHandler
	public void event(PlayerJoinEvent e) {
	}

}
