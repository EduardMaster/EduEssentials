package net.eduard.essentials.objects

import org.bukkit.entity.Player

class PlayerFaked(
    var player: Player ,
    var originalName: String = player.name,
    var fakeName: String? = null
)