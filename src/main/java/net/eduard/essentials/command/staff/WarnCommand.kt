package net.eduard.essentials.command.staff

import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.modules.Extra
import net.eduard.api.lib.modules.Mine
import org.bukkit.command.CommandSender
import java.util.*

class WarnCommand : CommandManager("warn", "aviso", "avisar") {
    companion object {
        val warns = mutableMapOf<UUID, Int>()
    }
    init {
        usage = "/warp <player> [motivo...]"
        description = "Avisa os jogadores que serão mutados se continuarem"
    }

    var messageInfo = "§aO jogador %player já foi avisado %amount vezes."
    var message = "§aO jogador %player foi avisado pela %amountº vez, agora pelo Motivo: %reason."
    var messageTarget = "§cVocê será mutade se continuar a %reason este é o %amount aviso, Staff que te avisou %player."


    override fun command(sender: CommandSender, args: Array<String>) {
        if (args.isEmpty()){
            sendUsage(sender)
            return
        }
        if (args.size == 1) {
            val playerName = args[0]
            if (Mine.existsPlayer(sender, playerName)) {
                val target = Mine.getPlayer(playerName);
                val amount = warns[target.uniqueId] ?: 0
                sender.sendMessage(
                    messageInfo
                        .replace("%player", playerName)
                        .replace("%amount", amount.toString())
                )
            }
        } else {
            val playerName = args[0]
            if (Mine.existsPlayer(sender, playerName)) {
                val target = Mine.getPlayer(playerName);
                val motivo = Extra.getText(1, * args)
                var amount = warns[target.uniqueId] ?: 0
                warns[target.uniqueId] = ++amount
                sender.sendMessage(
                    message
                        .replace("%player", playerName)
                        .replace("%reason", motivo)
                        .replace("%amount", amount.toString())
                )
                target.sendMessage(
                    messageTarget
                        .replace("%player", sender.name)
                        .replace("%reason", motivo)
                        .replace("%amount", amount.toString())
                )
            }
        }
    }
}