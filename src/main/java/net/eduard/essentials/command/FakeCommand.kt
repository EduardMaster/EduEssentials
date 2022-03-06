package net.eduard.essentials.command

import net.eduard.api.lib.manager.CommandManager
import net.eduard.essentials.objects.PlayerFaked
import net.eduard.api.lib.modules.MineReflect
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

class FakeCommand : CommandManager("fake", "nickfalso") {
    @EventHandler
    fun onQuitRemoveFake(e: PlayerQuitEvent) {
        val player = e.player
        removeFake(player.name)
    }

    @EventHandler
    fun onLoginResetFake(e: PlayerLoginEvent) {
        val player = e.player
        val name = player.name
        removeFake(name)
        getFake(player)
    }
    var messageReseted = "§aFake resetado!"
    var message = "§aFake ativado do jogador %player"
    var messageTaken = "§cEste nome de jogador já esta sendo usado por outro jogador."

    override fun playerCommand(player: Player, args: Array<String>) {
        if (args.isEmpty()) {
            player.sendMessage("§c/fake <jogador> §7Ativar um fake de um jogador")
            player.sendMessage("§c/fake resetar §7Resetar o fake")
            return
        }
        val cmd = args[0]
        val fakeName = args[0]
        if (cmd.equals("reset", ignoreCase = true) ||
            cmd.equals("resetar", ignoreCase = true)
        ) {
            reset(player)
            player.sendMessage(messageReseted)
        }else if (!canFake(fakeName)){
            player.sendMessage(messageTaken.replace("%player", fakeName))

        } else {

            fake(player, fakeName)
            player.sendMessage(message.replace("%player", fakeName))
        }
    }

    companion object {
        private val fakes: MutableMap<Player, PlayerFaked> = HashMap()
        fun canFake(name: String?): Boolean {
            for (playerFaked in fakes.values) {
                if (playerFaked.fakeName.equals(name, ignoreCase = true) ||
                    playerFaked.originalName.equals(name, ignoreCase = true)) {
                    return false
                }
            }
            return true
        }

        fun removeFake(name: String) {
            for (playerFaked in fakes.values) {
                if (name.equals(playerFaked.fakeName, ignoreCase = true)) {
                    reset(playerFaked.player)
                }
            }
        }

        fun getFake(player: Player): PlayerFaked {
            var playerFaked = fakes[player]
            if (playerFaked == null) {
                playerFaked = PlayerFaked(player, player.name, null)
                fakes[player] = playerFaked
            }
            return playerFaked
        }

        fun fake(player: Player, name: String?) {
            val playerFaked = getFake(player)
            playerFaked.fakeName = name
            player.displayName = name
            player.playerListName = name
            player.customName = name
            player.isCustomNameVisible = true
            MineReflect.changeName(player, name)
        }

        fun reset(player: Player) {
            val playerFaked = getFake(player)
            val name = playerFaked.originalName
            playerFaked.fakeName = name
            MineReflect.changeName(player, name)
            player.displayName = name
            player.playerListName = name
        }
    }
}