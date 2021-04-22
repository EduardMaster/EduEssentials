package net.eduard.essentials.listener

import net.eduard.api.lib.manager.EventsManager
import net.eduard.api.lib.modules.Mine
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.metadata.FixedMetadataValue

class ComboSystem : EventsManager() {

    init{
        for (player in Mine.getPlayers()){
            player.removeMetadata("combo",plugin)
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun event(e: EntityDamageByEntityEvent) {
        e.isCancelled=false
        var combo = 0.0
        //Mine.broadcast("Combo: "+combo)
        // Mine.broadcast("Dano: "+e.damage)
        e.entity.removeMetadata("combo", plugin)
        if (e.damager.hasMetadata("combo")){
            combo = e.damager.getMetadata("combo")[0].asDouble()
            //Mine.broadcast("Combo retornado: "+combo)
        }
        e.damage = e.damage * combo
        //Mine.broadcast("Dano Final: "+e.damage)
        combo++
        if (e.damager is Player){
            e.damager.sendMessage("§aSeu combo é: $combo")
        }
        e.damager.setMetadata("combo", FixedMetadataValue(plugin,combo))

    }
}