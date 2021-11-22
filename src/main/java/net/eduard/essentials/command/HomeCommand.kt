package net.eduard.essentials.command

import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.game.Title
import net.eduard.api.lib.modules.Mine
import net.eduard.essentials.EduEssentials
import net.eduard.api.lib.modules.MineReflect
import org.bukkit.Location
import org.bukkit.entity.Player

class HomeCommand : CommandManager("home", "casa") {

    var sound = SoundEffect.create("ENDERMAN_TELEPORT")
    var message = "§6Voce teleportado para sua Home!"
    var messageError = "§cSua home não foi setada!"


    var title = Title(
        "§6Casa §e%home",
        "§bTeleportado para sua casa §3%home!",
        20, 20 * 2, 20
    )
    init{
        usage = "/home <nome>"
    }


    fun teleport(player: Player, home: String, ) {
        val path = "homes." + player.name.toLowerCase() + "." + home.toLowerCase()
        player.teleport(EduEssentials.getInstance().storage[path, Location::class.java])
        sound.create(player)
        player.sendMessage(message.replace("%home", home))
        MineReflect.sendTitle(
            player, title.title.replace("%home", home),
            title.subTitle.replace("%home", home),
            title.fadeIn, title.stay,
            title.fadeOut
        )

    }

    override fun playerCommand(player: Player, args: Array<String>) {
        var home = "home"
        if (args.isNotEmpty()) {
            home = args[0].toLowerCase()
        }
        val path = "homes." + player.name.toLowerCase() + "." + home.toLowerCase()
        if (EduEssentials.getInstance().storage.contains(path)) {
            if (EduEssentials.getInstance().configs.getBoolean("homes.delay.enabled")) {
                val bypassPermission = EduEssentials.getInstance().configs.getString("homes.delay.permission-bypass")
                if (player.hasPermission(bypassPermission)) {
                    teleport(player, home)
                    return
                }
                EduEssentials.getInstance()
                    .syncDelay(EduEssentials.getInstance().configs.getLong("homes.delay.ticks")) {
                        teleport(player, home)
                    }
            } else {
                teleport(player, home)
            }
        } else {
            player.sendMessage(messageError.replace("%home", home))
            EduEssentials.getInstance().configs.remove(path)
        }
    }


}