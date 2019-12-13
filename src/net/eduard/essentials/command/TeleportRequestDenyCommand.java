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

                    if (Main.getInstance().getRequests().containsValue(target)) {
                        Player convidado = Main.getInstance().getRequests().get(target);
                        if (convidado.equals(p)) {
                            Main.getInstance().getRequests().remove(target);


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
