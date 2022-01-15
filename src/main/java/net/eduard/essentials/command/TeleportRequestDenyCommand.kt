package net.eduard.essentials.command

import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.modules.Mine
import net.eduard.essentials.EduEssentials
import net.eduard.essentials.objects.TeleportRequestState
import org.bukkit.entity.Player

class TeleportRequestDenyCommand : CommandManager("teleportdeny", "tpdeny") {

    var messageNotInvited = "§cVoce nao possui um pedido de teleporte."
    var messageDenied = "§cVoce recusou o pedido de teleporte de %player."
    var messagePlayerDenied = "§cO jogador %player recusou seu pedido de teleporte."
    override fun playerCommand(player: Player, args: Array<String>) {
        if (args.isEmpty()) {
            sendUsage(player)
            return
        }
        if (!Mine.existsPlayer(player, args[0])) return
        val target = Mine.getPlayer(args[0])
        if (EduEssentials.getInstance().manager.teleportRequests.containsKey(target)) {
            val request = EduEssentials.getInstance().manager.teleportRequests[target]!!
            val convidado = request.invited
            if (convidado == player) {
                request.deny()
                EduEssentials.getInstance().manager.teleportRequests.remove(target)
                target.sendMessage(messagePlayerDenied.replace("%player" , target.name))
                player.sendMessage(messageDenied.replace("%player" , convidado.name))
            } else {
                player.sendMessage(messageNotInvited)
            }
        }
    }
    init {
        usage = "/tpdeny <player>"
    }
}