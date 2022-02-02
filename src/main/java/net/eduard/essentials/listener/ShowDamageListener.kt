package net.eduard.essentials.listener

import net.eduard.api.lib.manager.EventsManager
import net.eduard.api.lib.modules.Extra
import net.eduard.essentials.EduEssentials
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.util.Vector

class ShowDamageListener : EventsManager() {


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onDamage(e: EntityDamageByEntityEvent) {
        val damage = e.finalDamage
        if (e.entity is ArmorStand) return
        if (damage == 0.0) return
        createTempHologram(e.entity.location, damage)
    }

    fun createTempHologram(location: Location, dano: Double) {
        (location.world.spawnEntity(location.add(0.0, 1.0, 0.0), EntityType.ARMOR_STAND) as ArmorStand)
            .apply {
                customName = "§f-§f" + Extra.formatMoney(dano) + "§c♥"
                isCustomNameVisible = true
                setGravity(true)
                isMarker = true
                isVisible = false
                isSmall = true
                velocity = Vector(0.0, 0.5, 0.0)
                removeWhenFarAway=true
                EduEssentials.getInstance().asyncDelay(40){
                    remove()
                }
            }
    }
}