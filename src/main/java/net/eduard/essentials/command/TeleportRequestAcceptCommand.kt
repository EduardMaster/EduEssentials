package net.eduard.essentials.command

import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.modules.Mine
import net.eduard.essentials.EduEssentials
import org.bukkit.entity.Player

class TeleportRequestAcceptCommand : CommandManager("teleportaccept", "tpaccept") {

    var messageAccepted = "§aPedido aceito."
    var messageTeleported = "§aTeleportando com sucesso"
    var messageNotInvited = "§cVoce nao possui um pedido de teleporte."
    var messageNotInvitedBy = "§cVocê não foi requisitado pelo jogador %player."

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
            val inviter = request.inviter
            if (convidado != player) {
                player.sendMessage(messageNotInvitedBy
                    .replace("%player" , inviter.name ))
                return
            }
            request.accept()
            EduEssentials.getInstance().manager.teleportRequests.remove(inviter)
            inviter.teleport(convidado)
            convidado.sendMessage(messageAccepted)
            inviter.sendMessage(messageTeleported)

        } else {
            player.sendMessage(messageNotInvited)
        }

    }

    init {
        usage = "/tpaccept <player>"
    }
}