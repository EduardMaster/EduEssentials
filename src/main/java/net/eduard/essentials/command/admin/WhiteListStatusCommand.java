
package net.eduard.essentials.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.lib.manager.CommandManager;

public class WhiteListStatusCommand extends CommandManager {

	public String message = " §6Jogadores na WhiteList: ";
	public String prefix = " §a";

	public WhiteListStatusCommand() {
		super("status", "estado");

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage(message);
		for (OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
			sender.sendMessage(prefix + player.getName());
		}
		return true;
	}



}
