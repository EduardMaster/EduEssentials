package net.eduard.essentials.command.admin

import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.modules.Mine
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class JailCommand : CommandManager("jail", "prender") {
    override fun command(sender: CommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            sendUsage(sender)
            return
        }
        if (Mine.existsPlayer(sender, args[0])) {
            val target = Bukkit.getPlayerExact(args[0])
            if (AdminCommand.instance.isPrisioner(target)) {
                AdminCommand.instance.removePrison(target);
                sender.sendMessage("§aO jogador " + target.name + " foi absolvido!")
            } else {
                AdminCommand.instance.prison(target);
                sender.sendMessage("§aO jogador " + target.name + " foi aprisionado!")
            }
        }
    }


}