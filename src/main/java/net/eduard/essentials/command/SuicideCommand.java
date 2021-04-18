package net.eduard.essentials.command;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuicideCommand extends CommandManager {


    private String message = "Você se matou";

    public SuicideCommand() {
        super("suicide", "suicidio", "sematar", "morrer");

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (!Mine.onlyPlayer(sender)) return true;
        Player p = (Player) sender;
        p.setHealth(0);
        p.sendMessage(message);


        return false;
    }
}
