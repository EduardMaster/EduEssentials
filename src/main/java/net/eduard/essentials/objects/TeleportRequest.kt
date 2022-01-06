package net.eduard.essentials.objects

import org.bukkit.entity.Player

class TeleportRequest(var inviter : Player,
var invited : Player) {
    var start = System.currentTimeMillis()
    var state = TeleportRequestState.WAITING
}
enum class TeleportRequestState {
    WAITING , ACCEPTED , DENIED, EXPIRED
}