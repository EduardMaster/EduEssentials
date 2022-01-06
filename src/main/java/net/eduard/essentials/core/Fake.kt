package net.eduard.essentials.core

import org.bukkit.entity.Player

class Fake(
    var player: Player,
    var original: String = player.name,
    var fake: String? = null
)