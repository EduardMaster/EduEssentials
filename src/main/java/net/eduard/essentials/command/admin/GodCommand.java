
package net.eduard.essentials.command.admin;

import java.util.Set;
import net.eduard.api.lib.modules.Mine;
import net.eduard.essentials.EduEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import net.eduard.api.lib.manager.CommandManager;

public class GodCommand extends CommandManager {


    public GodCommand() {
        super("god", "imortal");
        setUsage("/god [on/off]");
    }

    @Override

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Set<Player> gods = EduEssentials.getGods();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if (gods.contains(player)) {

                    gods.remove(player);
                } else {
                    gods.add(player);

                }
            } else {
                String sub = args[0].toLowerCase();

                if (Mine.OPT_COMMANDS_ON.contains(sub)) {


                    if (!gods.contains(player)) {
                        gods.add(player);
                    }

                } else if (Mine.OPT_COMMANDS_OFF.contains(sub)) {

                    gods.remove(player);


                } else {
                    sendUsage(sender);
                }
            }
        }
        return true;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        Set<Player> gods = EduEssentials.getGods();
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (gods.contains(player)) {
                e.setCancelled(true);
            }
        }
    }

}
