package net.eduard.essentials.command.staff;

import net.eduard.api.lib.manager.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.bukkit.scheduler.BukkitRunnable;

public class RestartCommand extends CommandManager {

	private int restartTime = 20*60*1;
	private String message = "O servidor irá reiniciar em 1 minuto";

	public RestartCommand(){
		super ("restart","reiniciar");
	}




	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {

		Bukkit.broadcastMessage(message);
		new BukkitRunnable(){

			@Override
			public void run() {
				Bukkit.shutdown();
			}
		}.runTaskLaterAsynchronously(getPlugin(),restartTime);


	return true;
	}

}
