package net.eduard.essentials.command.staff

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.modules.Mine
import net.eduard.essentials.EduEssentials

class SetSpawnCommand : CommandManager("setspawn") {
    init {
        permission = "spawn.set"
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (Mine.noConsole(sender)) return true;
        val player = sender as Player
        EduEssentials.getInstance().configs.set("Spawn", player.location)
        EduEssentials.getInstance().configs.saveConfig()
        player.sendMessage(EduEssentials.getInstance().message("Spawn setted"))


        return true
    }

}
