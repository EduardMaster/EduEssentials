
package net.eduard.essentials.command;

import net.eduard.essentials.core.MenuAutoPickup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.manager.CommandManager;

public class AutoPickupCommand extends CommandManager {

	public AutoPickupCommand() {
		super("autopickup");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {
	
		if (sender instanceof Player) {
			Player p = (Player) sender;
			MenuAutoPickup.abrir(p);
		}
		return true;
	}

}
