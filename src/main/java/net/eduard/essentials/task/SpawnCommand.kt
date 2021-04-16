package net.eduard.essentials.task

import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.game.Title
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.modules.Mine
import net.eduard.essentials.EduEssentials
import org.bukkit.Location
import org.bukkit.scheduler.BukkitRunnable

class SpawnCommand : CommandManager("spawn") {

    fun teleport(player: Player) {
        player.teleport(EduEssentials.getInstance().configs.config.get("Spawn", Location::class.java))
        player.sendMessage(EduEssentials.getInstance().message("Spawn teleport"))
        EduEssentials.getInstance().configs.get("teleport.sound" , SoundEffect::class.java).create(player)
        if (EduEssentials.getInstance().getBoolean("teleport.title enabled")) {
            val title = EduEssentials.getInstance().configs.get("teleport.title",  Title::class.java)
            title.create(player)
        }

    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (Mine.noConsole(sender)) {
            val player = sender as Player
            if (EduEssentials.getInstance().configs.contains("Spawn")) {
                if (EduEssentials.getInstance().configs.getBoolean("teleport.delay.enabled")) {
                    val delay = EduEssentials.getInstance().configs.getInt("teleport.delay.seconds")!!
                    object : BukkitRunnable() {

                        override fun run() {
                            teleport(player)
                        }
                    }.runTaskLater(plugin, (delay * 20).toLong())

                    player.sendMessage(EduEssentials.getInstance().message("Spawn delay teleport").replace("\$time", "" + delay))
                } else
                    teleport(player)

            } else {
                player.sendMessage(EduEssentials.getInstance().message("Spawn not setted"))

            }

        }
        return true
    }

}
