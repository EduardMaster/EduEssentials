package net.eduard.essentials.command.staff;

import net.eduard.api.lib.manager.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class PullCommand extends CommandManager{


    public PullCommand()
    {
        super ("pull","puxar");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;


            if (args.length == 0) {

                p.sendMessage("§cPor favor, use /puxar <jogador>");

            } else {

                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    p.sendMessage("§cEste jogador está offline.");
                    return true;
                }

                if (target.getName() == p.getName()) {
                    p.sendMessage("§cVocê não pode teleportar a si mesmo.");
                    return true;
                }

                p.sendMessage("§aVocê puxou o jogador §7" + target.getName());

                target.teleport(p.getLocation());

            }


        }
        return false;
    }
}
