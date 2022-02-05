package net.eduard.essentials.command;

import net.eduard.api.lib.manager.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class DiscordCommand extends CommandManager {
	public List<String> messages = new ArrayList<>();
	public DiscordCommand() {
		super("discord","dc");
		messages.add("");
		messages.add("§aAcesse o diescord www.discord.gg/algumlink");
		messages.add("");

	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		for (String msg : messages) {
			sender.sendMessage(msg);
		}
		return true;
	}
	

}
