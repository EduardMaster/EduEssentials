
package net.eduard.essentials.command.vip;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class FeedCommand extends CommandManager {
	
	public String message = "§2Fome saciada";
	public String messageTarget = "§2Fome do $player foi saciada";
	public FeedCommand() {
		super("feed","fome","semfome");
		setUsage("§c/feed [player]");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				player.setFoodLevel(20);
				player.setExhaustion(0);
				player.setSaturation(20);
				sender.sendMessage(message);
			}else {
				sendUsage(sender);
			}
		}else {
			if (Mine.existsPlayer(sender, args[0])) {
				Player player = Mine.getPlayer(args[0]);
				player.setFoodLevel(20);
				player.setExhaustion(0);
				player.setSaturation(20);
				player.sendMessage(message);
				sender.sendMessage(messageTarget);
			}
		}
		return true;
	}

}
