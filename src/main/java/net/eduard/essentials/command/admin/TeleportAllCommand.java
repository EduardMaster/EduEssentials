
package net.eduard.essentials.command.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class TeleportAllCommand extends CommandManager {
    public TeleportAllCommand() {
        super("teleportall", "tpall");
    }

    public String message = "§6Voce teleportou todos ate você!";
    public String messageTarget = "§6Voce foi teleportado pelo jogador %player§6!";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!Mine.onlyPlayer(sender)) return true;
        Player player = (Player) sender;
        for (Player playerLoop : Mine.getPlayers()) {
            if (playerLoop.equals(player))
                continue;
            playerLoop.teleport(player);
            playerLoop.sendMessage(
                    messageTarget.replace("%player", player.getDisplayName()));
        }
        sender.sendMessage(message);

        return true;
    }

}
