package net.eduard.essentials.command

import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.modules.BukkitBungeeAPI
import net.eduard.api.lib.modules.Mine
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LobbyCommand : CommandManager("lobby") {

    var message = "§aConectando você para o Lobby."

    override fun playerCommand(player: Player, args: Array<String>) {
        player.sendMessage(message)
        BukkitBungeeAPI.connectToServer(player.name, "lobby")
    }
}