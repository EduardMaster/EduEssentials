
package net.eduard.essentials.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.lib.manager.CommandManager;

public class AplicateCommand extends CommandManager {

	public List<String> messages = new ArrayList<>();
	public AplicateCommand() {
		super("aplicate" ,"aplicar");
		messages.add("�a - Requisitos para ser da STAFF - ");

	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		for (String line : messages) {
			sender.sendMessage(line);
		}
		return true;
	}

}
