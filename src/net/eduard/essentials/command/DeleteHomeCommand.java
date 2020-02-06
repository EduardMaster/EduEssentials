package net.eduard.essentials.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.Main;

public class DeleteHomeCommand extends CommandManager {
	public DeleteHomeCommand() {
		super("deletehome");
	}
	
	public String message = "§aVoce deletou sua home §2$home!";
	public String messageError = "§cNão existe a home $home!";
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (Mine.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (args.length == 0) {
				sendUsage(sender);
			}else {
				String path = p.getUniqueId().toString()+"."+args[0].toLowerCase();
				if (Main.getInstance().getConfigs().contains(path)) {
					Main.getInstance().getConfigs().remove(path);
					sender.sendMessage(message.replace("$home", ""));
					
				} else {
					sender.sendMessage( messageError.replace("$home", ""));
				}
			}
		}

		return true;
	}

}
