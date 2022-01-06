package net.eduard.essentials.command

import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.modules.Mine
import net.eduard.essentials.EduEssentials
import org.bukkit.entity.Player

class SetHomeCommand : CommandManager("sethome", "definircasa") {
    var message = "§bVoce setou um Home %home"
    var messageNoPermissionMoreHomes = "§cVoce não tem permissão para setar mais Homes!"
    var maxHomes = 100

    init {
        usage = "/sethome <nome>"
    }

    override fun playerCommand(player: Player, args: Array<String>) {
        var home = "home"
        if (args.isNotEmpty()) {
            home = args[0]
        }
        val path = "homes." + player.name.toLowerCase() + "." + home.toLowerCase()
        val size = EduEssentials.getInstance().storage.getKeys("homes."+player.name.toLowerCase())
        val amount = size.size
        if (!EduEssentials.getInstance().storage.contains(path)) {
            if (!Mine.hasPerm(player, permission, maxHomes, amount + 1)) {
                player.sendMessage(messageNoPermissionMoreHomes)
                EduEssentials.getInstance().storage.remove(path)
                return
            }
        }
        EduEssentials.getInstance().storage[path] = player.location
        player.sendMessage(message.replace("%home", home))
    }

}