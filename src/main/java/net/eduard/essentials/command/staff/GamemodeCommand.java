package net.eduard.essentials.command.staff;

import net.eduard.api.lib.modules.Extra;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class GamemodeCommand extends CommandManager {

	public GamemodeCommand() {
		super("gamemode", "gm");
	}
	public String message = "§6Seu gamemode agora é: $gamemode";
	public String messageTarget = "§6O gamemode do $player agora é: $gamemode";

	public String getGamemode(Player player) {
		return Extra.toTitle(player.getGameMode().name());
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.getGameMode() == GameMode.CREATIVE) {
					player.setGameMode(GameMode.SURVIVAL);
				} else {
					player.setGameMode(GameMode.CREATIVE);
				}
				sender.sendMessage( message.replace("$gamemode", getGamemode(player)));

			} else
				return false;

		} else {
			GameMode gm = null;
			for (GameMode gameMode : GameMode.values()) {
				if (args[0].equalsIgnoreCase("" + gameMode.getValue())
						|| (args[0].equalsIgnoreCase(gameMode.name()))) {
					gm = gameMode;
				}
			}
			Player p = null;
			if (sender instanceof Player) {
				p = (Player) sender;
			}
			if (args.length >= 2) {
				if (Mine.existsPlayer(sender, args[1])) {
					p = Mine.getPlayer(args[1]);
					sender.sendMessage(
							messageTarget.replace("$gamemode", getGamemode(p))
									.replace("$player", p.getDisplayName()));
				} else
					return true;;

			}
			if (p == null) {
				return false;
			}
			p.setGameMode(gm);
			sender.sendMessage(message.replace("$gamemode", getGamemode(p)));

		}
		return true;
	}
}
