
package net.eduard.essentials.command.vip;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand extends CommandManager {

    public HealCommand(){
        super("heal","vida");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (Mine.onlyPlayer(sender)){
            Player p = (Player) sender;
            Mine.refreshLife(p);

        }

        return true;
    }
}
