package net.eduard.essentials.core

import org.bukkit.entity.Player

class Fake(
    var player: Player,
    var originalName: String = player.name,
    var fakeName: String? = null
)