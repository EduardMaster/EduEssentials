
package net.eduard.essentials.command.vip;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class BackCommand extends CommandManager {
	public BackCommand() {
		super("back", "voltar");
	}

	public static HashMap<Player, Location> locations = new HashMap<>();
	public String messageOn = "§6Voce teleportou para onde você Morreu!";
	public String messageOff = "§6Voce não morreu ainda!";

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (Mine.onlyPlayer(sender)) {
			Player player = (Player) sender;
			if (locations.containsKey(player)) {
				player.sendMessage(messageOn);
			} else {
				player.sendMessage(messageOff);
			}
		}

		return true;
	}

	@EventHandler
	public void event(PlayerDeathEvent e) {
		Player player = e.getEntity();
		locations.put(player, player.getLocation());
	}
}
