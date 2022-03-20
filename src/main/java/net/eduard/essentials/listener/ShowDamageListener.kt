package net.eduard.essentials.listener

import net.eduard.api.lib.event.CustomDamageEvent
import net.eduard.api.lib.manager.EventsManager
import net.eduard.api.lib.modules.Extra
import net.eduard.essentials.EduEssentials
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.util.Vector

class ShowDamageListener : EventsManager() {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onDamage(e: CustomDamageEvent) {
        if (e.defenserEntity.location.y < 0.0)return
        if (e.defenserEntity is ArmorStand)return
        createTempHologram(e.defenserEntity.location, e.calculateResult())
    }
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onDamage(e: EntityDamageEvent) {
        if (e.cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK)return
        if (e.entity.location.y < 0.0)return
        if (e.entity is ArmorStand)return
        val finalDamage = e.finalDamage
        if (finalDamage==0.0)return
        createTempHologram(e.entity.location, finalDamage)
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