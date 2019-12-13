package net.eduard.essentials.command;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.events.ClickCounter;
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
        if (Mine.onlyPlayer(sender)) {
            Player p = (Player) sender;
            if (ClickCounter.getClicks().containsKey(p)) {
                p.sendMessage("§cVoce desativou o Contador de Cliques!");
                ClickCounter.getClicks().remove(p);

            } else {
                ClickCounter.getClicks().put(p, 0);
                p.sendMessage("§aVoce ativou o Contador de Cliques!");
            }

        }

        return true;
    }


}
