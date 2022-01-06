package net.eduard.essentials.command

import net.eduard.api.lib.manager.CommandManager
import net.eduard.essentials.EduEssentials
import org.bukkit.entity.Player
import java.lang.StringBuilder

class HomesCommand : CommandManager("homes", "homelist", "casas") {
    var message = "§6Suas homes: §e%homes"
    var messageError = "§cVocê não possui nenhuma home!"


    override fun playerCommand(player: Player, args: Array<String>) {
        val list = EduEssentials.getInstance().storage.getKeys("homes." + player.name.toLowerCase())
        if (list.isEmpty()) {
            player.sendMessage(messageError)
        } else {
            val builder = StringBuilder()
            for ((id, sec) in list.withIndex()) {
                if (id != 0) {
                    builder.append(", ")
                }
                builder.append(sec)
            }
            player.sendMessage(message.replace("%homes", builder.toString(),false))
        }
    }

}