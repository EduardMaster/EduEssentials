package net.eduard.essentials.command;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.listener.ClickCounter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClickCounterCommand extends CommandManager {

    public ClickCounterCommand() {
        super("clicktest", "clickcounter");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
                             String[] args) {
        if (!Mine.onlyPlayer(sender)) return true;
        Player player = (Player) sender;
        if (ClickCounter.getClicks().containsKey(player)) {
            player.sendMessage("§cVoce desativou o Contador de Cliques!");
            ClickCounter.getClicks().remove(player);

        } else {
            ClickCounter.getClicks().put(player, 0);
            player.sendMessage("§aVoce ativou o Contador de Cliques!");
        }


        return true;
    }


}
