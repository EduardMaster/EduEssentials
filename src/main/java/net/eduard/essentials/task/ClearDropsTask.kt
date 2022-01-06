package net.eduard.essentials.task

import net.eduard.api.lib.manager.TimeManager
import net.eduard.api.lib.modules.Extra
import net.eduard.api.lib.modules.Mine
import net.eduard.essentials.EduEssentials
import org.bukkit.Bukkit
import org.bukkit.entity.Item

class ClearDropsTask : TimeManager(20L) {
    var time = 0
    var timesToBroadcast = listOf(20)

    init {
        reset()
    }

    override fun run() {
        if (!EduEssentials.getInstance().getBoolean("clear-drops.enabled")) return
        time--
        if (time in timesToBroadcast) {
            val text = EduEssentials.getInstance().configs
                .getMessages("clear-drops.broadcast-text")
            for (line in text) {
                Mine.broadcast( line.replace("%time",
                        "" + Extra.formatTime(time * 1000L) )
                )
            }
        }
        if (time <= 0) {
            clearDrops()
            reset()
        }

    }

    fun reset() {
        time = EduEssentials.getInstance().configs.getInt("clear-drops.timer-in-seconds")
        timesToBroadcast = EduEssentials.getInstance().configs.getIntList("clear-drops.broadcast-at")
    }

    fun clearDrops() {
        val text = EduEssentials.getInstance().configs
            .getMessages("clear-drops.text")
        for (line in text) {
            for (player in Mine.getPlayers()) {
                player.sendMessage(line)
            }
        }
        for (world in Bukkit.getWorlds()) {
            for (item in world.getEntitiesByClass(Item::class.java)) {
                item.remove()
            }
        }
    }

}