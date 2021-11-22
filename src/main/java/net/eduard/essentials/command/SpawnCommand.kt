package net.eduard.essentials.command

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
        player.teleport(EduEssentials.getInstance().storage.get("spawn", Location::class.java))
        player.sendMessage(EduEssentials.getInstance().message("Spawn teleport"))
        EduEssentials.getInstance().configs.get("spawn.sound-on-teleport", SoundEffect::class.java).create(player)
        if (EduEssentials.getInstance().getBoolean("spawn.title.enabled")) {
            val title = EduEssentials.getInstance().configs.get("spawn.title.used", Title::class.java)
            title.create(player)
        }

    }

    override fun playerCommand(player: Player, args: Array<String>) {
        if (EduEssentials.getInstance().storage.contains("spawn")) {
            if (EduEssentials.getInstance().configs.getBoolean("spawn.delay.enabled")) {
                if (player.hasPermission(EduEssentials.getInstance().configs.getString("spawn.delay.bypass-permission"))){
                    teleport(player)
                    return
                }
                val delay = EduEssentials.getInstance().configs.getInt("spawn.delay.seconds")
                object : BukkitRunnable() {
                    override fun run() {
                        teleport(player)
                    }
                }.runTaskLater(plugin, (delay * 20).toLong())

                player.sendMessage(
                    EduEssentials.getInstance().message("Spawn delay teleport")
                        .replace("\$time", "" + delay)
                )
            } else
                teleport(player)

        } else {
            player.sendMessage(EduEssentials.getInstance().message("Spawn not setted"))

        }
    }


}
