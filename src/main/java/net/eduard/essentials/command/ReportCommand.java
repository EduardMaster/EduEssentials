
package net.eduard.essentials.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;
import org.jetbrains.annotations.NotNull;

public class ReportCommand extends CommandManager {

    public String message = "§6O jogador §e$target §6foi reportado por §a$sender  §6motido: §c$reason";
    public ReportCommand() {
        super("report","reportar");
    }

    @Override
    public void command(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length <= 1) {
           sendUsage(sender);
           return;
        }
        if (Mine.existsPlayer(sender, args[0])) {
            Player target = Mine.getPlayer(args[0]);
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                builder.append(args[i] + " ");
            }
            broadcast(message.replace("$target", target.getDisplayName())
                    .replace("$sender", sender.getName())
                    .replace("$reason", builder.toString()));
        }
    }

}
