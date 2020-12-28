
package net.eduard.essentials.command.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class TeleportAllCommand extends CommandManager {
	public TeleportAllCommand() {
		super("teleportall","tpall");
	}
	public String message = "§6Voce teleportou todos ate você!";
	public String messageTarget = "§6Voce foi teleportado pelo jogador $player§6!";
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (Mine.onlyPlayer(sender)) {
			Player p = (Player) sender;
			for (Player d : Mine.getPlayers()) {
				if (d.equals(p))
					continue;
				d.teleport(p);
				d.sendMessage(
						messageTarget.replace("$player", p.getDisplayName()));
			}
			sender.sendMessage(message);
		}
		return true;
	}

}
