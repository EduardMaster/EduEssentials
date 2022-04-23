package net.eduard.essentials.listener

import net.eduard.api.lib.manager.EventsManager
import net.eduard.essentials.EduEssentials
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class CommandDelayListener : EventsManager() {
    companion object {
        private val lastCommand: MutableMap<Player, Long> = HashMap()
    }

    @EventHandler
    fun commandDelay(e: PlayerCommandPreprocessEvent) {
        val player = e.player
        if (!EduEssentials.getInstance().configs.getBoolean("command-delay.enabled")) return
        if (player.hasPermission(
                EduEssentials.getInstance().configs.getString("command-delay.bypass-permission")
            )
        ) return
        if (lastCommand.containsKey(player)) {
            val lastCommandMoment = lastCommand[player]!!
            val agora = System.currentTimeMillis()
            val delay = EduEssentials.getInstance().configs.getLong("command-delay.ticks") * 50
            val test = agora > lastCommandMoment + delay
            if (!test) {
                player.sendMessage(EduEssentials.getInstance().message("command-cooldown"))
                e.isCancelled = true
            } else {
                lastCommand[player] = System.currentTimeMillis()
            }
        } else {
            lastCommand[player] = System.currentTimeMillis()
        }
    }
}