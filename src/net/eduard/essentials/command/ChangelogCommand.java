package net.eduard.essentials.command;

import java.util.ArrayList;
import java.util.List;

import net.eduard.api.lib.Mine;
import net.eduard.essentials.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.lib.manager.CommandManager;
import org.bukkit.entity.Player;

public class ChangelogCommand extends CommandManager {

	private List<String> messages = new ArrayList<>();

	public ChangelogCommand() {
		super("changelog", "mudancas");
		messages.add("1.1 Servidor lançado");
		messages.add("1.2 Servidor melhorado");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		for (String msg : messages) {
			sender.sendMessage(msg);
		}

		return true;
	}

	
}
