package net.eduard.essentials.command.vip

import net.eduard.api.lib.manager.CommandManager
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class LightningCommand : CommandManager("lightning", "luz") {
    var messageOn = "§6Luz ativada!"
    var messageOff = "§6Luz desativada!"
    override fun playerCommand(player: Player, args: Array<String>) {
        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION)
            player.sendMessage(messageOff)
        } else {
            player.addPotionEffect(PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 60 * 60 * 24, 0))
            player.sendMessage(messageOn)
        }
    }

}