package net.eduard.essentials.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.eduard.api.lib.manager.CommandManager;

public class KillMobsCommand extends CommandManager{
	private String message = "§aLimpando os Mobs";

	public KillMobsCommand() {
		super("killmobs","matarmobs");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		for (World world : Bukkit.getWorlds()) {
			for (LivingEntity entity : world.getLivingEntities()) {
				if (entity instanceof Player) {
					continue;
				}
				entity.remove();
			}
		}
		sender.sendMessage(message);
		return true;
	}
}
