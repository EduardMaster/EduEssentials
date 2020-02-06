
package net.eduard.essentials.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class ListCommand extends CommandManager {

	public String message = "§aTem $players jogadores online!";
	public ListCommand() {
		super("list");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		sender.sendMessage( message.replace("$players", ""+Mine.getPlayers().size()));
		return true;
	}
}
