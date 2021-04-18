
package net.eduard.essentials.command.vip;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class FlyCommand extends CommandManager {
	public String messageOn = "§6Voce pode voar!";
	public String messageOff = "§6Voce não pode mais voar!";
	public String messageTarget = "§6Voce troco o modo de voo do jogador $player";

	public FlyCommand() {
		super("fly","voar");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (Mine.onlyPlayer(sender)) {
				Player player = (Player) sender;
				if (player.getAllowFlight()) {
					player.setFlying(false);
					player.setAllowFlight(false);
					player.sendMessage(messageOff);

				} else {
					player.setAllowFlight(true);
					sender.sendMessage(messageOn);
				}
			}
		} else {
			String player = args[0];
			if (Mine.existsPlayer(sender, player)) {
				Player target = Bukkit.getPlayer(player);
				sender.sendMessage(messageTarget.replace("$player", target.getName()));

			}
		}

		return true;
	}

}
