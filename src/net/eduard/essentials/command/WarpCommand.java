
package net.eduard.essentials.command;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.game.Title;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class WarpCommand extends CommandManager {

    public String message = "§aVoce foi à warp $warp";

    public WarpCommand() {
        super("warp");
    }

    public Title title = new Title(20, 20 * 2, 20, "§6Warp e$warp", "§2Voce foi para a warp §a$warp!");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
                             String[] args) {
        if (Mine.onlyPlayer(sender)) {
            Player p = (Player) sender;
            if (args.length == 0) {
                return false;
            }
            String name = args[0];
            String path = "warps." + name;
            if (Main.getInstance().getStorage().contains(path)) {
                p.teleport((Location) Main.getInstance().getStorage().get(path));
                title.create(p);
                sender.sendMessage(message.replace("$warp", name));
            } else {

            }


        }
        return true;
    }

}
