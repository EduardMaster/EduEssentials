package net.eduard.essentials.objects

import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask

class TeleportRequest(var inviter : Player,
var invited : Player) {
    lateinit var expireTask: BukkitTask
    var start = System.currentTimeMillis()
    var state = TeleportRequestState.WAITING

    fun deny(){
        state = TeleportRequestState.DENIED
        expireTask.cancel()
    }
    fun accept(){
        state = TeleportRequestState.ACCEPTED
        expireTask.cancel()
    }
}
enum class TeleportRequestState {
    WAITING , ACCEPTED , DENIED, EXPIRED
}