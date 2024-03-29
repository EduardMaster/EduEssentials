package net.eduard.essentials.command

import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.modules.Mine
import net.eduard.essentials.EduEssentials
import net.eduard.essentials.objects.TeleportRequest
import net.eduard.essentials.objects.TeleportRequestState
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class TeleportRequestCommand : CommandManager("teleportrequest", "tpa") {



    var cooldownSeconds = 60
    override fun playerCommand(player: Player, args: Array<String>) {
        if (args.isEmpty()) {
            sendUsage(player)
            return
        }
        if (EduEssentials.getInstance().manager.teleportRequests.containsKey(player)) {
            val tpRequest = EduEssentials.getInstance().manager.teleportRequests[player]!!
            val secondsLeft = (tpRequest.start + cooldownSeconds * 1000) - System.currentTimeMillis()
            if (secondsLeft > 0 && tpRequest.state == TeleportRequestState.WAITING) {
                player.sendMessage("§cVocê já tem um convite pendente.")
                return
            }
        }
        if (!Mine.existsPlayer(player, args[0])) return
        val target = Bukkit.getPlayer(args[0])
        if (target == player) {
            player.sendMessage("§cVocê não pode enviar teleporte para só próprio.")
            return
        }
        target.sendMessage("§eO jogador ${player.name} deseja ir até você.")
        target.sendMessage("")
        val chatTpaAceitar = TextComponent("§eClique §a§lAQUI §epara aceitar.")
        chatTpaAceitar.hoverEvent =
            HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder("§aAceitar convite.").create())
        chatTpaAceitar.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept ${player.name}")
        target.spigot().sendMessage(chatTpaAceitar)

        val chatTpaNegar = TextComponent("§eClique §c§lAQUI §epara negar.")
        chatTpaNegar.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder("§cNegar convite.").create())
        chatTpaNegar.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny ${player.name}")
        target.spigot().sendMessage(chatTpaNegar)
        target.sendMessage("")
        target.sendMessage("§6O convite expira em 1 minuto.")
        target.sendMessage("")
        player.sendMessage("§eVocê enviou um convite de teleporte para ${target.name}.")
        val request = TeleportRequest(player, target)
        EduEssentials.getInstance().manager.teleportRequests[player] = request
        request.expireTask = EduEssentials.getInstance().syncDelay(20L * cooldownSeconds) {
            val request2 = EduEssentials.getInstance().manager.teleportRequests[player] ?: return@syncDelay
            if (request2.state == TeleportRequestState.WAITING) {
                player.sendMessage("§cConvite de teleporte expirado.");
                request2.state = TeleportRequestState.EXPIRED
            }
        }
    }

    init {
        usage = "/tpa <jogador>"
    }
}