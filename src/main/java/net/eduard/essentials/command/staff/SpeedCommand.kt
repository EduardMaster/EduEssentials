package net.eduard.essentials.command.staff

import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.modules.Extra
import org.bukkit.entity.Player

class SpeedCommand : CommandManager("speed", "velocidade") {

    var message = "§aSua velocidade foi alterada para %level"

    init {
        usage = "/speed <1-10>"
    }

    override fun playerCommand(player: Player, args: Array<String>) {
        if (args.isEmpty()){
            sendUsage(player)
            return
        }
        var level = Extra.toInt(args[0]);
        if (level < 1) level = 2
        if (level > 10) level = 10
        if (player.isFlying) {
            player.flySpeed = level * 0.1f
        } else {
            player.walkSpeed = level * 0.1f
        }
        player.sendMessage(message.replace("%level", "" + level))
    }
}