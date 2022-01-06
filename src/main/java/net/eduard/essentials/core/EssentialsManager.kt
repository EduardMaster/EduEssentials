package net.eduard.essentials.core

import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.game.Jump
import net.eduard.api.lib.game.SoundEffect
import net.eduard.essentials.objects.AutoMessage
import net.eduard.essentials.objects.TeleportRequest
import org.bukkit.Material
import org.bukkit.entity.Player

class EssentialsManager {
    var commandEnable = mutableListOf("on","true","active")
    var commandDisable = mutableListOf("off","false","desactive")
    var soupSystem = true
    var soupSignTitle = "soup"
    var soupSignFormat = listOf("&f=======", "&aSopas!", "&2Clique!", "&f======")
    var soupSignCreation = "&6Voce criou uma placa de sopas!"
    var soupMenuTitle = "&c&lSopas gratis!"
    var soupSound = SoundEffect.create("BURP")
    var noChangeFoodLevel = false
    var soupRecoverValue = 6
    var soup = ItemBuilder(Material.MUSHROOM_SOUP).name("§aSopa")
    var soupEmpty = ItemBuilder(Material.BOWL).name("§cSopa Vazia")
    val gods = mutableSetOf<Player>()
    val teleportRequests = mutableMapOf<Player, TeleportRequest>()
    val slimeChunkActive = mutableListOf<Player>()
    var doubleJumpSystem = false
    var doubleJumpEffect = Jump(true, 0.5, 2.5, SoundEffect.create("ENDERMAN_TELEPORT"))
    var autoMessages = mutableListOf<AutoMessage>()
}