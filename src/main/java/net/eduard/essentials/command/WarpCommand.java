
package net.eduard.essentials.command;

import net.eduard.api.lib.game.Title;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.EduEssentials;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class WarpCommand extends CommandManager {

    public String message = "§aVoce foi à warp %warp";
    public String messageError = "§cEsta warp não existe: %warp";

    public WarpCommand() {
        super("warp");
    }



    public Title title = new Title("§6Warp §e%warp",
            "§2Você foi para a warp §a%warp!",
            20, 20 * 2, 20);

    @Override
    public void playerCommand(@NotNull Player player, @NotNull String[] args) {
        if (args.length == 0) {
            sendUsage(player);
            return;
        }
        String warpName = args[0];
        String path = "warps." + warpName.toLowerCase();
        Title title = this.title.copy();

        title.setTitle(title.getTitle().replace("%warp", warpName));
        title.setSubTitle(title.getSubTitle().replace("%warp", warpName));

        if (EduEssentials.getInstance().getStorage().contains(path)) {
            player.teleport(EduEssentials.getInstance().getStorage().get(path, (Location.class)));
            title.create(player);
            player.sendMessage(message.replace("%warp", warpName));
        } else {
            player.sendMessage(messageError.replace("%warp", warpName));
        }
    }


}
