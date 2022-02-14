package net.eduard.essentials.command

import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.modules.Mine
import net.eduard.essentials.EduEssentials
import org.bukkit.entity.Player

class TeleportRequestDenyCommand : CommandManager("teleportdeny", "tpdeny") {

    var messageNotInvited = "§cVoce nao possui um pedido de teleporte."
    var messageDenied = "§cVoce recusou o pedido de teleporte de %player."
    var messagePlayerDenied = "§cO jogador %player recusou seu pedido de teleporte."
    override fun playerCommand(quemEstaNegando: Player, args: Array<String>) {
        if (args.isEmpty()) {
            sendUsage(quemEstaNegando)
            return
        }
        if (!Mine.existsPlayer(quemEstaNegando, args[0])) return
        val quemConvidou = Mine.getPlayer(args[0])
        val request = EduEssentials.getInstance().manager.teleportRequests[quemConvidou] ?: return
        val convidado = request.invited
        if (convidado == quemEstaNegando) {
            request.deny()
            EduEssentials.getInstance().manager.teleportRequests.remove(quemConvidou)
            quemConvidou.sendMessage(messagePlayerDenied.replace("%player", quemEstaNegando.name))
            quemEstaNegando.sendMessage(messageDenied.replace("%player", quemConvidou.name))
        } else {
            quemEstaNegando.sendMessage(messageNotInvited)
        }
    }

    init {
        usage = "/tpdeny <player>"
    }
}