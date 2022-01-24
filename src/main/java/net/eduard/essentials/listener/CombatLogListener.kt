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
    fun aoSair(e: PlayerQuitEvent) {
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
    fun aoDigitarComandos(e: PlayerCommandPreprocessEvent) {
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
    fun aoMorrer(e: PlayerDeathEvent) {
        if (e.entity == null) return
        val morreu = e.entity
        if (e.entity.killer != null) {
            players.remove(morreu)
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun aoBater(e: EntityDamageByEntityEvent) {
        if (!EduEssentials.getInstance().getBoolean("combatlog.enabled")) {
            return
        }
        val time = EduEssentials.getInstance().configs.getInt("combatlog.combat-seconds")
        if (e.entity !is Player) return
        if (e.damager !is Player) return
        val entity = e.entity as Player
        val damager = e.damager as Player
        if (!players.contains(entity)) {
            players[entity] = System.currentTimeMillis()
            entity.sendMessage(EduEssentials.getInstance().message("combat.started")
                .replace("%time", ""+time))
            EduEssentials.getInstance().asyncDelay(20L * time) {
                players.remove(entity)
                entity.sendMessage(EduEssentials.getInstance().message("combat.out"))
                entity.playSound(entity.location, Sound.LEVEL_UP, 1f, 1f)
            }
        }
        if (!players.contains(damager)) {
            players[damager] = System.currentTimeMillis()
            damager.sendMessage(EduEssentials.getInstance().message("combat.started")
                .replace("%time", ""+time))
            EduEssentials.getInstance().asyncDelay(20L * time) {
                players.remove(damager)
                damager.sendMessage(EduEssentials.getInstance().message("combat.out"))
                damager.playSound(damager.location, Sound.LEVEL_UP, 1f, 1f)
            }
        }
    }

}