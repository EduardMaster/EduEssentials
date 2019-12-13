package net.eduard.essentials.command;

import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.events.ComboCounter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComboCounterCommad extends CommandManager {

    public ComboCounterCommad() {
        super("combocounter");
    }

    public String messageOn = "§aVoce ativou o contador de Combos!";
    public String messageOff = "§cVoce desativou o contador de Combos!";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (ComboCounter.getCombos().containsKey(p)) {
                ComboCounter.getCombos().remove(p);
                p.sendMessage(messageOff);
            } else {
                ComboCounter.getCombos().put(p, 0);
                p.sendMessage(messageOn);
            }

        }
        return true;
    }


}