package net.eduard.essentials.command.staff

import org.bukkit.entity.Player

import net.eduard.api.lib.manager.CommandManager
import net.eduard.essentials.EduEssentials

class SetSpawnCommand : CommandManager("setspawn") {
    init {
        permission = "spawn.set"
    }

    override fun playerCommand(player: Player, args: Array<String>) {
        EduEssentials.getInstance().storage["spawn"] = player.location
        EduEssentials.getInstance().storage.saveConfig()
        player.sendMessage(EduEssentials.getInstance().message("Spawn setted"))
    }
}
