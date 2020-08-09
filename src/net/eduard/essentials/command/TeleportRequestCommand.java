package net.eduard.essentials.command;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.EduEssentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TeleportRequestCommand extends CommandManager {

    private int cooldown = 30;

    public TeleportRequestCommand() {
        super("teleportrequest", "tpa");
        setUsage("/tpa <jogador>");
    }


    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (Mine.onlyPlayer(sender)){
            Player p = (Player) sender;


            if (args.length == 0) {

                sendUsage(p);

            } else {

                if (!p.hasPermission("teleport.delay.bypass")) {

                    if (EduEssentials.getInstance().getRequestsDelay().containsKey(p)) {
                        long dif = System.currentTimeMillis() - EduEssentials.getInstance().getRequestsDelay().get(p) + (cooldown * 1000);
                        if (dif < 0) {
                            //RexAPI.sendActionBar(p, "§cAguarde " + cooldown + " para usar o tpa novamente.");
                            return false;
                        }
                    }
                }
                if (Mine.existsPlayer(sender, args[0])) {
                    final Player target = Bukkit.getPlayer(args[0]);
                    if (target == p) {
                        sender.sendMessage("");
                        return false;
                    }
                    Player recipient = p;
                    sender.sendMessage("§ePedido enviado para §6" + recipient.getName() + ".");

                    recipient.sendMessage(" ");
                    recipient.sendMessage("§6" + sender.getName() + " §epediu para ir ate voce.");

                    TextComponent TpaAceitar = new TextComponent("§ePara aceitar o pedido, use §6/tpaccept");
                    TpaAceitar.setBold(Boolean.valueOf(true));
                    TpaAceitar.setHoverEvent(
                            new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6/tpaccept").create()));
                    TpaAceitar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
                    recipient.spigot().sendMessage(TpaAceitar);

                    TextComponent TpaNegar = new TextComponent("§ePara aceitar o pedido, use §6/tpdeny.");
                    TpaNegar.setBold(Boolean.valueOf(true));
                    TpaNegar.setHoverEvent(
                            new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6/tpdeny.").create()));
                    TpaNegar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny"));
                    recipient.spigot().sendMessage(TpaNegar);

                    recipient.sendMessage("§eEste pedido ser§ expirado em 1 minuto.");
                    recipient.sendMessage(" ");

                    EduEssentials.getInstance().getRequests().put(recipient, target);

                }

            }


        }
        return true;
    }


}
