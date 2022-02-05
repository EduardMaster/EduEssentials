
package net.eduard.essentials.command.vip;

import net.eduard.api.lib.manager.CommandManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CraftCommand extends CommandManager {

	public String message = "§6Voce abriu a Mesa de Craft!";
	public CraftCommand() {
		super("craft","craftar");
	}



	@Override
	public void playerCommand(@NotNull Player player, @NotNull String[] args) {
		player.openWorkbench(player.getLocation(), true);
		player.sendMessage(message);
	}

}
