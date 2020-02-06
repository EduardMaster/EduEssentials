
package net.eduard.essentials.command.staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class ClearInventoryCommand extends CommandManager {
	public String message = "§6Seu inventario foi limpo!";
	public String messageTarget = "§6Voce limpou o inventario do §e$player";
	public ClearInventoryCommand() {
		super("clearinventory","clearinv","cinv");
		
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				Mine.clearInventory(p);
				p.sendMessage(message);
			}else 
			return false;
		} else {
			String name = args[0];
			if (Mine.existsPlayer(sender, name)) {
				Player target = Mine.getPlayer(name);
				sender.sendMessage( messageTarget.replace("$player",
						target.getDisplayName()));
				target.sendMessage(message);
				Mine.clearInventory(target);
			}
		}

		return true;
	}
}
