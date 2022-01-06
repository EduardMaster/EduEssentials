package net.eduard.essentials.command

import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.modules.Mine
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class ListCommand : CommandManager("list", "players", "jogadores", "onlines") {
    var message = "§aTem %players jogadores online!"
    override fun command(sender: CommandSender, args: Array<String>) {
        sender.sendMessage(message.replace("%players", "" + Mine.getPlayers().size))
    }
}