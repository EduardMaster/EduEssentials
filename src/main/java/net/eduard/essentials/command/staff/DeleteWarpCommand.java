
package net.eduard.essentials.command.staff;

import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.EduEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class DeleteWarpCommand extends CommandManager {

	public String message = "§aVoce deletou a warp $warp";
	public String messageError = "§cEsta warp $warp não existe";
	public DeleteWarpCommand(){
		super("deletewarp","delwarp");
	}

	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {
		if (args.length == 0) {
			sendUsage(sender);
		} else {



			String name = args[0];
			String path = "warps." + name.toLowerCase();
			if(EduEssentials.getInstance().getStorage().contains(path)){
				EduEssentials.getInstance().getStorage().remove(path);
				sender.sendMessage(message.replace("$warp", name));
			}else{
				sender.sendMessage(messageError.replace("$warp", name));
			}


		}
		return true;
	}

}
