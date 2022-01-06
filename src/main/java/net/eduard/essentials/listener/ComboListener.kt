package net.eduard.essentials.listener

import net.eduard.api.lib.manager.EventsManager
import net.eduard.api.lib.modules.Mine
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.metadata.FixedMetadataValue

class ComboListener : EventsManager() {

    init{
        for (player in Mine.getPlayers()){
            player.removeMetadata("combo",plugin)
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    fun comboSystem(e: EntityDamageByEntityEvent) {
        var combo = 0.0
        e.entity.removeMetadata("combo", plugin)
        if (e.damager.hasMetadata("combo")){
            combo = e.damager.getMetadata("combo")[0].asDouble()

        }
        e.damage = e.damage * combo
        combo++
        if (e.damager is Player){
            e.damager.sendMessage("§aSeu combo é: $combo")
        }
        e.damager.setMetadata("combo", FixedMetadataValue(plugin,combo))

    }
}