
package net.eduard.essentials.command;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.game.Title;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.EduEssentials;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class WarpCommand extends CommandManager {

    public String message = "§aVoce foi à warp $warp";
    public String messageError = "§cEsta warp não existe: $warp";
    public WarpCommand() {
        super("warp");
    }

    public Title title = new Title( "§6Warp §e$warp", "§2Você foi para a warp §a$warp!",20, 20 * 2, 20);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
                             String[] args) {
        if (Mine.onlyPlayer(sender)) {
            Player player = (Player) sender;
            if (args.length == 0) {
                sendUsage(sender);
                return true;
            }
            String name = args[0];
            String path = "warps." + name.toLowerCase();
            Title title = this.title.copy();
            title.setTitle(title.getTitle().replace("$warp", name));
            title.setSubTitle(title.getSubTitle().replace("$warp", name));
            if (EduEssentials.getInstance().getStorage().contains(path)) {
                player.teleport(EduEssentials.getInstance().getStorage().get(path,(Location.class) ));
                title.create(player);
                sender.sendMessage(message.replace("$warp", name));
            } else {
                sender.sendMessage(messageError.replace("$warp", name));
            }


        }
        return true;
    }

}
