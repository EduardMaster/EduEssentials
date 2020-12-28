package net.eduard.essentials.command;

import net.eduard.api.lib.modules.MineReflect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.game.SoundEffect;
import net.eduard.api.lib.game.Title;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.EduEssentials;
import org.bukkit.scheduler.BukkitRunnable;

public class HomeCommand extends CommandManager {
	public HomeCommand() {
		super("home","casa");
	}

	
	public SoundEffect sound = SoundEffect.create("ENDERMAN_TELEPORT");
	public String message = "§6Voce teleportado para sua Home!";
	public String messageError = "§cSua home não foi setada!";
	public Title title = new Title( "§6Casa §e$home", "§bTeleportado para sua casa §3$home!",20, 20 * 2, 20);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Mine.onlyPlayer(sender)) {
			Player player = (Player) sender;
			String home = "home";
			if (args.length >= 1) {
				home = args[0].toLowerCase();
			}
			String path = "homes."+player.getUniqueId().toString() + "." + home;
			if (EduEssentials.getInstance().getStorage().contains(path)) {
				final String homex = home;
				new BukkitRunnable(){

					@Override
					public void run() {
						player.teleport(EduEssentials.getInstance().getStorage().get(path,(Location.class) ));
						sound.create(player);
						sender.sendMessage(message.replace("$home", homex));
						MineReflect.sendTitle(player, title.getTitle().replace("$home", homex),
								title.getSubTitle().replace("$home", homex), title.getFadeIn(), title.getStay(),
                                title.getFadeOut());
					}
				}.runTaskLaterAsynchronously(getPlugin(),20);



			} else {
				sender.sendMessage(messageError.replace("$home", home));
				EduEssentials.getInstance().getConfigs().remove(path);
			}

		}

		return true;
	}

	

}
