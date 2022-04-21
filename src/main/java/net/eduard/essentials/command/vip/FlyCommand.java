
package net.eduard.essentials.command.vip;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;
import org.jetbrains.annotations.NotNull;

public class FlyCommand extends CommandManager {
    public String messageOn = "§6Você pode voar!";
    public String messageOff = "§6Você não pode mais voar!";
    public String messageTarget = "§6Você alternou o modo de voo do jogador $player";
    public String adminPermission = "fly.admin";

    public FlyCommand() {
        super("fly", "voar");
        setUsage("/fly [player]");
    }

    @Override
    public void command(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player){
                Player player = (Player) sender;
                if (player.getAllowFlight()) {
                    player.setFlying(false);
                    player.setAllowFlight(false);
                    player.sendMessage(messageOff);

                } else {
                    player.setAllowFlight(true);
                    player.sendMessage(messageOn);
                }
            }else {
                sendUsage(sender);
            }
        } else {
            if (!sender.hasPermission(adminPermission)) {
                sender.sendMessage(getPermissionMessage());
                return;
            }
            String player = args[0];
            if (Mine.existsPlayer(sender, player)) {
                Player target = Bukkit.getPlayer(player);
                if (target.getAllowFlight()) {
                    target.setFlying(false);
                    target.setAllowFlight(false);
                    target.sendMessage(messageOff);

                } else {
                    target.setAllowFlight(true);
                    target.sendMessage(messageOn);
                }
                sender.sendMessage(messageTarget.replace("$player", target.getName()));

            }
        }
    }



}
