package net.eduard.essentials.command

import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.modules.Mine
import net.eduard.essentials.EduEssentials
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetHomeCommand : CommandManager("sethome", "definircasa") {
    var message = "§bVoce setou um Home %home"
    var messageNoPermissionMoreHomes = "§cVoce não tem permissão para setar mais Homes!"
    var maxHomes = 100
    init{
        usage = "/sethome <nome>"
    }
    override fun onCommand(
        sender: CommandSender, command: Command,
        label: String, args: Array<String>
    ): Boolean {
        if (Mine.onlyPlayer(sender)) {
            val player = sender as Player
            var home = "home"
            if (args.isNotEmpty()) {
                home = args[0]
            }
            // home.limit.5
            val path = "homes." + player.name.toLowerCase() + "." + home.toLowerCase()
            val size = EduEssentials.getInstance().storage.getKeys(player.uniqueId.toString())
            val amount = size.size
            if (!EduEssentials.getInstance().storage.contains(path)) {
                if (!Mine.hasPerm(player, permission, maxHomes, amount + 1)) {
                    player.sendMessage(
                        messageNoPermissionMoreHomes
                    )
                    EduEssentials.getInstance().storage.remove(path)
                    return true
                }
            }
            EduEssentials.getInstance().storage[path] = player.location
            sender.sendMessage(message.replace("%home", home))
        }
        return true
    }
}