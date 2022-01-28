package net.eduard.essentials.listener

import net.eduard.api.lib.manager.EventsManager
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType

class PotionListener : EventsManager() {

    @EventHandler
    fun forcaDesestacar(e: InventoryClickEvent) {
        if (e.whoClicked !is Player) return
        //val p = e.whoClicked as Player
        if (e.inventory.type != InventoryType.CHEST) return
        val item = e.currentItem?:return
        if (item.type == Material.AIR) return
        if (item.maxStackSize != 1) return
        if (item.amount > 1) {
            e.isCancelled = true
            if (e.cursor == null || e.cursor.type == Material.AIR) {
                e.currentItem.amount--
                val clone = e.currentItem.clone()
                clone.amount = 1
                e.cursor = clone
            }

        }
    }
}