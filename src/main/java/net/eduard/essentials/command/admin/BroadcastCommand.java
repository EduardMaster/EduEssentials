
package net.eduard.essentials.command.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class BroadcastCommand extends CommandManager {
	public String message = "§f$\n    %message \n";
	public BroadcastCommand() {
		super("broadcast","bc");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		StringBuilder builder = new StringBuilder();
		int id = 0;
		for (String arg : args) {
			if (id == 0)
				id++;
			else
				builder.append(" ");

			builder.append(Mine.toChatMessage(arg));
		}
		Mine.broadcast(this.message.replace("%message", builder.toString()));
		return true;
	}

}
