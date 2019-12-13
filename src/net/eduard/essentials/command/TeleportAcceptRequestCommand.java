package net.eduard.essentials.command;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                    if (Main.getInstance().getRequests().containsValue(p)) {
                        Player convidado = Main.getInstance().getRequests().get(p);
                        Main.getInstance().getRequests().remove(p);
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