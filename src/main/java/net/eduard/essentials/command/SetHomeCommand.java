package net.eduard.essentials.command;

import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.EduEssentials;

public class SetHomeCommand extends CommandManager {
	public SetHomeCommand() {
		super("sethome","definircasa");
	}
	public String message = "§bVoce setou um Home $home";
	public String messageNoPermissionMoreHomes = "§cVoce não tem permissão para setar mais Homes!";
	public int maxHomes = 100;
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (Mine.onlyPlayer(sender)) {

			Player player = (Player) sender;
			String home = "home";
			if (args.length >= 1) {
				home = args[0];
			}
			// home.limit.5
			String path = "homes."+player.getUniqueId().toString() + "." + home;
			Set<String> size = EduEssentials.getInstance().getStorage().getKeys(player.getUniqueId().toString());
			int amount = size.size();
			if (!EduEssentials.getInstance().getStorage().contains(path)) {
				if (!Mine.hasPerm(player, getPermission(), maxHomes, amount + 1)) {

					player.sendMessage(
							messageNoPermissionMoreHomes);
					EduEssentials.getInstance().getStorage().remove(path);
					return true;
				}
			}
			EduEssentials.getInstance().getStorage().set(path, player.getLocation());
			sender.sendMessage(message.replace("$home", home));

		}

		return true;
	}

}
