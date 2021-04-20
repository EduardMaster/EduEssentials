package net.eduard.essentials.command;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.EduEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportAcceptRequestCommand extends CommandManager {

    public TeleportAcceptRequestCommand() {
        super("teleportaccept", "tpaccept");
        setUsage("§c/tpaccept <player>");
    }
    public String messageAccepted = "§aPedido aceito.";
    public String messageTeleported = "§aTeleportando com sucesso";
    public String messageNotInvited = "§cVoce nao possui um pedido de teleporte.";


    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (Mine.onlyPlayer(sender)){
            Player p = (Player) sender;
            if (args.length == 0) {
                sendUsage(sender);
            } else {
                if (Mine.existsPlayer(sender, args[0])) {
                    Player target = Mine.getPlayer(args[0]);

                    if (EduEssentials.getInstance().getManager().getTpaRequests().containsValue(p)) {
                        Player convidado = EduEssentials.getInstance().getManager().getTpaRequests().get(p);
                        EduEssentials.getInstance().getManager().getTpaRequests().remove(p);
                        convidado.teleport(p);
                        p.sendMessage(messageAccepted);
                        convidado.sendMessage(messageTeleported);

                    } else {
                        sender.sendMessage(messageNotInvited);
                    }
                }


            }
        }
        return true;

    }
}