
package net.eduard.essentials.command.admin;

import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.command.staff.WhiteListAddCommand;

public class WhiteListCommand extends CommandManager {
	public WhiteListCommand() {
		super("whitelist");
		register(new WhiteListHelpCommand());
		register(new WhiteListAddCommand());
		register(new WhiteListRemoveCommand());
		register(new WhiteListOnCommand());
		register(new WhiteListOffCommand());
		register(new WhiteListListCommand());

	}

}
