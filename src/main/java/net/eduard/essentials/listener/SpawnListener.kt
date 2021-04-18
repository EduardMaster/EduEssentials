package net.eduard.essentials.listener

import net.eduard.api.lib.game.SoundEffect
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerRespawnEvent

import net.eduard.api.lib.manager.EventsManager
import net.eduard.essentials.EduEssentials
import org.bukkit.Location

class SpawnListener : EventsManager() {

    @EventHandler
    fun onPlayerJoinEvent(e: PlayerJoinEvent) {


        val player = e.player
        if (!player.hasPermission("spawn.join")) return
        if (!EduEssentials.getInstance().getBoolean("teleport.on join")) return
        if (EduEssentials.getInstance().getBoolean("teleport.only on first join")) {
            if (player.hasPlayedBefore()) {
                return
            }
        }
        if (EduEssentials.getInstance().configs.contains("Spawn")) {
            player.teleport(EduEssentials.getInstance().configs.config.get("Spawn", Location::class.java))

            EduEssentials.getInstance().configs.get("teleport.sound on join", SoundEffect::class.java).create(player)
        }


    }

    @EventHandler
    fun onPlayerRespawnEvent(e: PlayerRespawnEvent) {

        val player = e.player
        val name = player.world.name.toLowerCase()
        if (!player.hasPermission("spawn.respawn")) return
        if (!EduEssentials.getInstance().getBoolean("teleport.on respawn")) return
        if (!EduEssentials.getInstance().getBoolean("teleport.respawn in world.$name")) return
        if (!EduEssentials.getInstance().configs.contains("Spawn")) return
        e.respawnLocation =
            EduEssentials.getInstance().configs.config.get("Spawn", Location::class.java)

        EduEssentials.getInstance().asyncDelay(1) {
            EduEssentials.getInstance().configs.get(
                "teleport.sound on respawn",
                SoundEffect::class.java
            ).create(player)
        }


    }
}
