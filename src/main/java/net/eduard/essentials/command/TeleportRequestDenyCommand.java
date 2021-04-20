package net.eduard.essentials.command;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.EduEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportRequestDenyCommand extends CommandManager {


    public TeleportRequestDenyCommand() {
        super("teleportdeny", "tpdeny");
    }


    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {


        if (Mine.onlyPlayer(sender)) {
            Player p = (Player) sender;
            if (args.length == 0) {
                sendUsage(sender);
            } else {
                if (Mine.existsPlayer(sender, args[0])) {
                    Player target = Mine.getPlayer(args[0]);

                    if (EduEssentials.getInstance().getManager().getTpaRequests().containsValue(target)) {
                        Player convidado = EduEssentials.getInstance().getManager().getTpaRequests().get(target);
                        if (convidado.equals(p)) {
                            EduEssentials.getInstance().getManager().getTpaRequests().remove(target);


                            p.sendMessage("§cVoce recusou o pedido de teleport de " + convidado.getName() + ".");
                        } else {
                            sender.sendMessage("§cVoce nao possui um pedido de teleporte.");
                        }

                    } else {

                    }
                }


            }
        }

        return true;
    }


}
