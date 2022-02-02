package net.eduard.essentials.listener

import net.eduard.api.lib.manager.TimeManager
import net.eduard.essentials.EduEssentials
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import java.util.HashMap

class AntiMacroListener : TimeManager() {
    @EventHandler
    fun bloquear(e: EntityDamageByEntityEvent) {
        if (e.damager !is Player) return
        val batetor = e.damager as Player
        if (e.entity !is Player) return
        val dis = batetor.location.distance(e.entity.location)
        if (dis < 5) return
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("antimacro.admin")) {
                player.sendMessage(
                    EduEssentials.getInstance().message("macro-warn")
                        .replace(
                            "\$player", batetor.name.replace("\$distance", "" + dis)
                        )
                )
            }
        }


    }

    @EventHandler
    fun event(e: PlayerInteractEvent) {
        val player = e.player
        if (e.action == Action.LEFT_CLICK_AIR) {
            if (cliques.containsKey(player)) {
                val clique = cliques[player]!!
                cliques[player] = clique + 1
            } else {
                cliques[player] = 1
            }
        }
    }

    @EventHandler
    fun event(e: EntityDamageByEntityEvent) {
        if (e.damager !is Player) return
        val player = e.damager as Player
        if (cliques.containsKey(player)) {
            val clique = cliques[player]!!
            cliques[player] = clique + 1
        } else {
            cliques[player] = 1
        }

    }

    fun macroSuspectFound(target : Player, clique : Int){
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("antimacro.admin")) {
                player.sendMessage(
                    EduEssentials.getInstance().message("macro-suspect")
                        .replace("<jogador>", target.name)
                        .replace("<cliques>", "" + clique)
                )
            }
        }
    }
    fun macroRealFound(target : Player, clique : Int){
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("antimacro.admin")) {
                player.sendMessage(
                    EduEssentials.getInstance().message("macro-found")
                        .replace("<jogador>", target.name)
                        .replace("<cliques>", "" + clique)
                )
            }
        }
    }

    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            val clique = cliques[player] ?: 0
            // player.sendMessage("§aTestando seus cliques: "+clique);
            if (clique > 30) {
               macroRealFound(player, clique)
            } else if (clique > 15) {
                macroSuspectFound(player,clique)
            }
            if (clique != 0)
                cliques.remove(player)
        }
    }

    companion object {
        var cliques: MutableMap<Player, Int> = HashMap()
    }
}