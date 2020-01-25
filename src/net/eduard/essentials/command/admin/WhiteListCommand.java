
package net.eduard.essentials.command.admin;

import net.eduard.api.lib.manager.CommandManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class WhiteListCommand extends CommandManager {
	public static Map<Player, Long> delay = new HashMap<Player, Long>();
	public WhiteListCommand() {
		super("whitelist","listabranca");
		register(new WhiteListHelpCommand());
		register(new WhiteListAddCommand());
		register(new WhiteListRemoveCommand());
		register(new WhiteListOnCommand());
		register(new WhiteListOffCommand());
		register(new WhiteListStatusCommand());

	}

}
