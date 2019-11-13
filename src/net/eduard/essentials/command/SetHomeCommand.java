package net.eduard.essentials.command;

import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.config.Config;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.Main;

public class SetHomeCommand extends CommandManager {
	public SetHomeCommand() {
		super("sethome");
	}
	public String message = "�bVoce setou um Home $home";
	public int maxHomes = 100;
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (Mine.onlyPlayer(sender)) {

			Player p = (Player) sender;
			String home = "home";
			if (args.length >= 1) {
				home = args[0];
			}
			// home.limit.5
			String path = p.getUniqueId().toString() + "." + home;
			Set<String> size = Main.getInstance().getConfigs().getKeys(p.getUniqueId().toString());
			int amount = size.size();
			if (!Main.getInstance().getConfigs().contains(path)) {
				if (!Mine.hasPerm(p, getPermission(), 100, amount + 1)) {

					p.sendMessage(
							"�cVoce n�o tem permiss�o para setar mais Homes!");
					Main.getInstance().getConfigs().remove(path);
					return true;
				}
			}
			Main.getInstance().getConfigs().set(path, p.getLocation());
			sender.sendMessage(message.replace("$home", home));

		}

		return true;
	}

}
