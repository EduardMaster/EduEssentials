package net.eduard.essentials.command

import net.eduard.api.lib.manager.CommandManager
import org.bukkit.entity.Player

class SuicideCommand : CommandManager("suicide", "suicidio", "sematar", "morrer") {
    var message = "§cVocê se matou!"
    override fun playerCommand(player: Player, args: Array<String>) {
        player.health = 0.0
        player.sendMessage(message)
    }
}