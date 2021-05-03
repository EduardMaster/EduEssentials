
package net.eduard.essentials.command.staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class TeleportCommand extends CommandManager {
    public TeleportCommand() {
        super("teleport");
    }

    public String message = "§6Voce se teleportou ate o §e$target";
    public String messageTarget = "§6O jogador §a$player §6foi teleportado ate o §e$target";

    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {
        if (args.length == 0) {
            return false;
        }
        if (args.length == 1) {
            if (!(sender instanceof Player)) return true;
            Player player = (Player) sender;
            if (Mine.existsPlayer(sender, args[0])) {
                Player target = Mine.getPlayer(args[0]);
                System.out.println(target == null);
                sender.sendMessage(message.replace("$target",
                        target.getDisplayName()));
                player.teleport(target);
            }


            return false;
        }
        if (!Mine.existsPlayer(sender, args[0])) return true;
        if (!Mine.existsPlayer(sender, args[1])) return true;
        Player player = Mine.getPlayer(args[0]);
        Player target = Mine.getPlayer(args[1]);
        player.teleport(target);
        player.sendMessage(message.replace("$target",
                target.getDisplayName()));
        sender.sendMessage(messageTarget
                .replace("$player", player.getDisplayName())
                .replace("$target", target.getDisplayName()));


        return true;
    }
}
