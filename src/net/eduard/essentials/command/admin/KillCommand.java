package net.eduard.essentials.command.admin;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillCommand extends CommandManager {


    private String message = "Você morreu, um Staff te assasinou";
    private String messageTarget = "Você matou o jogador $player";

    public KillCommand() {
        super("kill", "matar");
        setUsage("/kill <player>");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (args.length == 0) {
            sender.sendMessage(getUsage());


        } else {
            if (Mine.existsPlayer(sender, args[0])) {

                Player target = Bukkit.getPlayer(args[0]);


                target.setHealth(0.0D);
                target.sendMessage(message.replace("$player", sender.getName()));
                sender.sendMessage(messageTarget.replace("$player", target.getName()));

            }


        }
        return false;
    }
}
