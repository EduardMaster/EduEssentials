
package net.eduard.essentials.command.staff;

import net.eduard.api.lib.modules.Extra;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class SetKitCommand extends CommandManager {

	public String message = "§6Seu inventario foi aplicado para todos jogadores!";

	public SetKitCommand() {
		super("setkit");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Mine.onlyPlayer(sender)) {
			int range = 100;
			if (args.length>=1){
				range = Extra.toInt(args[0]);
			}

			Player player = (Player) sender;
			for (Player playerLoop : Mine.getPlayerAtRange(player.getLocation(), range)) {
				if (playerLoop != player) {
					playerLoop.getInventory().setArmorContents(player.getInventory().getArmorContents());
					playerLoop.getInventory().setContents(player.getInventory().getContents());

				}
			}
			sender.sendMessage(message);
		}

		return true;
	}

}
