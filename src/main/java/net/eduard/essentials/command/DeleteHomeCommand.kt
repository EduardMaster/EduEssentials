package net.eduard.essentials.command

import net.eduard.api.lib.manager.CommandManager
import net.eduard.essentials.EduEssentials
import org.bukkit.entity.Player

class DeleteHomeCommand : CommandManager("deletehome","delhome",
    "deletarcasa") {

    var message = "§aVoce deletou sua home §2%home!"
    var messageError = "§cNão existe a home %home!"

    override fun playerCommand(player: Player, args: Array<String>) {
        if (args.isEmpty()) {
            sendUsage(player)
            return
        }
        val homeName = args[0].toLowerCase()
        val path = "homes." + player.name.toLowerCase() + "." + homeName
        if (EduEssentials.getInstance().storage.contains(path)) {
            EduEssentials.getInstance().storage.remove(path)
            player.sendMessage(message.replace("%home", homeName))
        } else {
            player.sendMessage(messageError.replace("%home", homeName))
        }
    }
}