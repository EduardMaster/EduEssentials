package net.eduard.essentials.listener

import net.eduard.api.lib.kotlin.formatTime
import net.eduard.api.lib.manager.EventsManager
import net.eduard.api.lib.modules.Extra
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.io.File

class PlayerActionsListener : EventsManager() {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onChat(e: AsyncPlayerChatEvent) {
        e.player.actions.add(e.message, e.isCancelled)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onCommand(e: PlayerCommandPreprocessEvent) {
        e.player.actions.add(e.message, e.isCancelled)
    }

    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        e.player.actions.save()
        cache.remove(e.player)
    }

    class PlayerActions(val playerName: String) {
        var content = mutableListOf<String>()
        fun add(action: String, canceled: Boolean) {
            content.add("[${System.currentTimeMillis().formatTime()}] Cancelled:$canceled -> $action")
        }

        fun save() {
            val file = File("playerlogs/${playerName.toLowerCase()}.txt")
            file.parentFile.mkdirs()
            Extra.writeLines(file, content)
        }
    }

    companion object {
        val cache = mutableMapOf<Player, PlayerActions>()
        fun save(){
            cache.forEach { it.value.save() }
        }

        val Player.actions
            @Synchronized
            get() = cache.getOrPut(this) {
                val name = name.toLowerCase()
                val file = File("playerlogs/$name.txt")
                val actions = PlayerActions(this.name)
                if (file.exists()) {
                    actions.content.addAll(Extra.readLines(file))
                }
                actions
            }
    }
}