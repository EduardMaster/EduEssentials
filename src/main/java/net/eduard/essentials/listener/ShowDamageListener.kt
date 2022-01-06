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
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class ShowDamageListener : EventsManager() {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun event(e: EntityDamageByEntityEvent) {
        val damage = e.finalDamage
        if (e.entity is ArmorStand) return
        if (damage == 0.0) return
        createTempArmourStand(e.entity.location, damage)
    }

    fun createTempArmourStand(location: Location, dano: Double) {
        val stand = location.world.spawnEntity(location.add(0.0, 1.0, 0.0), EntityType.ARMOR_STAND) as ArmorStand
        stand.customName = "§f-§f" + Extra.formatMoney(dano) + "§c♥"
        stand.isCustomNameVisible = true
        stand.setGravity(true)
        stand.isMarker = true
        stand.isVisible = false
        stand.isSmall = true
        stand.velocity = Vector(0.0, 0.5, 0.0)
        EduEssentials.getInstance().asyncDelay(40){
            stand.remove()
        }

    }
}