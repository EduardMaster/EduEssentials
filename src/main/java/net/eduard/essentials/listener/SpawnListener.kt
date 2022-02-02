package net.eduard.essentials.listener

import net.eduard.api.lib.game.SoundEffect
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerRespawnEvent

import net.eduard.api.lib.manager.EventsManager
import net.eduard.api.lib.modules.Mine
import net.eduard.essentials.EduEssentials
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.weather.WeatherChangeEvent

class SpawnListener : EventsManager() {


    @EventHandler
    fun teleportOnVoid(e: EntityDamageEvent) {
        if (e.entity !is Player) return
        val player = e.entity as Player
        if (EduEssentials.getInstance().getBoolean("spawn.teleport-on-void")
            && e.cause == EntityDamageEvent.DamageCause.VOID
        ) {

            e.isCancelled = true
            player.fallDistance = -player.fallDistance
            if (EduEssentials.getInstance().storage.contains("spawn")) {
                player.teleport(EduEssentials.getInstance().storage["spawn", Location::class.java])
                EduEssentials.getInstance().configs["spawn.sound-on-teleport", SoundEffect::class.java].create(player)
            }
        }
    }

    @EventHandler
    fun notRain(e: WeatherChangeEvent) {
        if (e.toWeatherState() && EduEssentials.getInstance().getBoolean("not-rain")) {
            e.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onJoin(e: PlayerJoinEvent) {
        val player = e.player
        val firstTime = !player.hasPlayedBefore()

        if (!firstTime) {
            if (!player.hasPermission(EduEssentials.getInstance().getString("spawn.join-permission"))) return
        } else {
            if (EduEssentials.getInstance().getBoolean("spawn.teleport-on-first-join-only")) return
        }

        if (EduEssentials.getInstance().storage.contains("spawn")) {
            val local = EduEssentials.getInstance().storage["spawn", Location::class.java]
            player.teleport(local)
            if (firstTime) {
                /**
                 * Necessario para resolver um Bug do Spigot de ficar voltando o jogador para uma cordenada perto do Spawn do Mundo
                 */
                EduEssentials.getInstance().syncDelay(10) {
                    player.teleport(local)
                    EduEssentials.getInstance().configs["spawn.sound-on-join", SoundEffect::class.java].create(player)
                }
                EduEssentials.getInstance().syncDelay(20) {
                    player.teleport(local)
                    EduEssentials.getInstance().configs["spawn.sound-on-join", SoundEffect::class.java].create(player)
                }
            }
        }

    }


    @EventHandler
    fun onPlayerRespawnEvent(e: PlayerRespawnEvent) {
        val player = e.player
        val name = player.world.name.toLowerCase()
        if (!player.hasPermission(EduEssentials.getInstance().getString("spawn.respawn-permission"))) return
        if (!EduEssentials.getInstance().getBoolean("spawn.teleport-on-respawn")) return
        if (!EduEssentials.getInstance().getBoolean("spawn.worlds.$name")) return
        if (!EduEssentials.getInstance().storage.contains("spawn")) return
        e.respawnLocation = EduEssentials.getInstance().storage["spawn", Location::class.java]

        EduEssentials.getInstance().asyncDelay(1) {
            EduEssentials.getInstance().configs["spawn.sound-on-respawn", SoundEffect::class.java].create(player)
        }


    }
}
