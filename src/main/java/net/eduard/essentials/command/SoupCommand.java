package net.eduard.essentials.command;

import net.eduard.essentials.EduEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class SoupCommand extends CommandManager {


	public SoupCommand() {
		super("soup", "sopas");
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (Mine.onlyPlayer(sender)) {
			Player p = (Player) sender;
			Mine.fill(p.getInventory(), EduEssentials.getInstance().getSoup());
			p.sendMessage(EduEssentials.getInstance().message("soup"));
		}
		return true;
	}

}
