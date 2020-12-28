
package net.eduard.essentials.command;

import net.eduard.api.lib.modules.MineReflect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class PingCommand extends CommandManager {

	public String message = "§6Seu ping é: §e$ping";
	public String messageTarget = "§6O ping do jogador §e$target §6é: §a$ping";
	public PingCommand() {
		super("ping","pingar","testlag");

	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				sender.sendMessage(message.replace("$ping", MineReflect.getPing(p)));
			} else
				return false;
		} else {
			String name = args[0];
			if (Mine.existsPlayer(sender, name)) {
				Player target = Mine.getPlayer(name);
				sender.sendMessage(messageTarget
						.replace("$target", target.getDisplayName())
						.replace("$ping", MineReflect.getPing(target)));
			}
		}
		return true;
	}

}
