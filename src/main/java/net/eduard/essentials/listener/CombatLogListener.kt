package net.eduard.essentials.listener

import net.eduard.api.lib.manager.EventsManager
import net.eduard.api.lib.modules.Mine
import net.eduard.essentials.EduEssentials
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerQuitEvent

class CombatLogListener : EventsManager() {

    companion object {
        private val players: MutableMap<Player, Long> = mutableMapOf()
    }


    @EventHandler
    fun onQuitKillPlayer(e: PlayerQuitEvent) {
        val player = e.player
        if (!EduEssentials.getInstance().getBoolean("combatlog.enabled")) {
            return
        }
        if (!players.contains(player)) return
        players.remove(player)
        Mine.broadcast(EduEssentials.getInstance().message("combat.quit")
            .replace("\$player", player.name))
        player.health = 0.0
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun cancelCommandsWhenPvP(e: PlayerCommandPreprocessEvent) {
        val player = e.player
        if (!EduEssentials.getInstance().getBoolean("combatlog.enabled")) {
            return
        }
        if (!players.contains(player)) return
        for (cmd in EduEssentials.getInstance().configs.getMessages("combatlog.commands-permitted")) {
            if (e.message.toLowerCase().startsWith(cmd.toLowerCase())) {
                return
            }
        }
        e.isCancelled = true
        val time = EduEssentials.getInstance().configs.getInt("combatlog.combat-seconds")
        player.sendMessage(EduEssentials.getInstance().message("combat.try-command")
            .replace("%time", ""+time))
    }

    @EventHandler
    fun onDeathRemoveFromPvPList(e: PlayerDeathEvent) {
        if (e.entity == null) return
        val player = e.entity
        if (e.entity.killer != null) {
            players.remove(player)
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onStartPvP(e: EntityDamageByEntityEvent) {
        if (!EduEssentials.getInstance().getBoolean("combatlog.enabled")) {
            return
        }
        val time = EduEssentials.getInstance().configs.getInt("combatlog.combat-seconds")
        if (e.entity !is Player) return
        if (e.damager !is Player) return
        val defender = e.entity as Player
        val attacker = e.damager as Player
        if (attacker.isFlying){
            attacker.isFlying=false
            attacker.allowFlight=false
        }

        if (!players.contains(defender) && !defender.hasPermission("combatlog.bypass")) {
            players[defender] = System.currentTimeMillis()
            defender.sendMessage(EduEssentials.getInstance().message("combat.started")
                .replace("%time", ""+time))
            EduEssentials.getInstance().asyncDelay(20L * time) {
                players.remove(defender)
                defender.sendMessage(EduEssentials.getInstance().message("combat.out"))
                defender.playSound(defender.location, Sound.LEVEL_UP, 1f, 1f)
            }
        }
        if (!players.contains(attacker) && !attacker.hasPermission("combatlog.bypass")) {
            players[attacker] = System.currentTimeMillis()
            attacker.sendMessage(EduEssentials.getInstance().message("combat.started")
                .replace("%time", ""+time))
            EduEssentials.getInstance().asyncDelay(20L * time) {
                players.remove(attacker)
                attacker.sendMessage(EduEssentials.getInstance().message("combat.out"))
                attacker.playSound(attacker.location, Sound.LEVEL_UP, 1f, 1f)
            }
        }
    }

}