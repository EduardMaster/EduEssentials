
package net.eduard.essentials.command.staff;

import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DeleteWarpCommand extends CommandManager {

	public String message = "§aVoce deletou a warp $warp";

	public DeleteWarpCommand(){
		super("delwarp");
	}

	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {
		if (args.length == 0) {
			sendUsage(sender);
		} else {



			String name = args[0];
			String path = "warps." + name;
			if(Main.getInstance().getStorage().contains(path)){
				Main.getInstance().getStorage().remove(path);
				sender.sendMessage(message.replace("$warp", name));
			}


		}
		return true;
	}

}
