package net.eduard.essentials.task

import net.eduard.api.lib.game.SoundEffect
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerRespawnEvent

import net.eduard.api.lib.manager.EventsManager
import net.eduard.essentials.EduEssentials
import org.bukkit.Location
import org.bukkit.scheduler.BukkitRunnable

class SpawnEvents : EventsManager() {

    @EventHandler
    fun onPlayerJoinEvent(e: PlayerJoinEvent) {


        val player = e.player
        if (player.hasPermission("spawn.join")) {
            if (EduEssentials.getInstance().getBoolean("teleport.on join")) {
                if (EduEssentials.getInstance().getBoolean("teleport.only on first join")) {

                    if (player.hasPlayedBefore()) {
                        return
                    }
                }

                if (EduEssentials.getInstance().configs.contains("Spawn")) {
                    player.teleport(EduEssentials.getInstance().configs.config.get("Spawn" , Location::class.java))

                    EduEssentials.getInstance().configs.get("teleport.sound on join", SoundEffect::class.java).create(player)
                }

            }
        }

    }

    @EventHandler
    fun onPlayerRespawnEvent(e: PlayerRespawnEvent) {

        val player = e.player
        val name = player.world.name.toLowerCase()
        if (player.hasPermission("spawn.respawn")) {
            if (EduEssentials.getInstance().getBoolean("teleport.on respawn")) {
                if (EduEssentials.getInstance().getBoolean("teleport.respawn in world.$name")) {
                    if (EduEssentials.getInstance().configs.contains("Spawn")) {
                        e.respawnLocation = EduEssentials.getInstance().configs.config.get("Spawn", Location::class.java)
                        object : BukkitRunnable() {

                            override fun run() {
                                EduEssentials.getInstance().configs.get("teleport.sound on respawn", SoundEffect::class.java).create(player)
                            }
                        }.runTaskLaterAsynchronously(plugin, 1)
                    }
                }
            }
        }
    }
}
