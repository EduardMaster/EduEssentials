
package net.eduard.essentials.command.staff;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;


public class SetWarpCommand extends CommandManager {

    public String message = "§aVoce setou a warp $warp";

    public SetWarpCommand() {
        super("setwarp");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
                             String[] args) {
        if (Mine.onlyPlayer(sender)) {
            Player p = (Player) sender;
            if (args.length == 0) {
                sendUsage(sender);
            } else {

                String name = args[0];
                String path = "warps." + name;
                Main.getInstance().getStorage().set(path, p.getLocation());
                sender.sendMessage(message.replace("$warp", name));
            }

        }
        return true;
    }

}
