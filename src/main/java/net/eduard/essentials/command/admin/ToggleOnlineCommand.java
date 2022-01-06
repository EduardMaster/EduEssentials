
package net.eduard.essentials.command.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.eduard.essentials.EduEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class ToggleOnlineCommand extends CommandManager {

    @EventHandler
    public void aoSair(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (players.contains(player)) {
            players.remove(player);
        }
    }


    public String messageOn = "§6Voce esta visivel!";
    public String messageOff = "§6Voce esta invisivel!";
    public List<String> commandsOn = Arrays.asList("visible", "aparecer");
    public List<String> commandsOff = Arrays.asList("invisible", "desaparecer");
    public static List<Player> players = new ArrayList<>();

    public ToggleOnlineCommand() {
        super("toggleonline", "vanish", "v");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {
        if (!Mine.onlyPlayer(sender)) return true;
        Player player = (Player) sender;
        if (commandsOn.contains(label.toLowerCase())) {
            Mine.show(player);
            sender.sendMessage(messageOn);
            if (!players.contains(player))
                players.add(player);
        } else if (commandsOff.contains(label.toLowerCase())) {
            Mine.hide(player);
            sender.sendMessage(messageOff);
            players.remove(player);
        } else {
            if (args.length == 0) {
                if (players.contains(player)) {
                    player.chat("/" + commandsOff.get(0));

                } else {
                    player.chat("/" + commandsOn.get(0));
                }
            } else {
                String cmd = args[0].toLowerCase();
                if (EduEssentials.getInstance().getManager().getCommandEnable().contains(cmd)) {
                    player.chat("/" + commandsOn.get(0));
                } else if (EduEssentials.getInstance().getManager().getCommandDisable().contains(cmd)) {
                    player.chat("/" + commandsOff.get(0));
                } else {
                    sender.sendMessage(getUsage());
                }
            }

        }

        return true;
    }

}
