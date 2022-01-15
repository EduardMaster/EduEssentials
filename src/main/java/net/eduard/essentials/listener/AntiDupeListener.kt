package net.eduard.essentials.listener

import net.eduard.api.lib.manager.EventsManager
import net.eduard.essentials.EduEssentials
import org.bukkit.Material
import org.bukkit.entity.Boat
import org.bukkit.entity.Minecart
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerPickupItemEvent
import org.bukkit.event.vehicle.VehicleEnterEvent

class AntiDupeListener : EventsManager() {

    @EventHandler
    fun bloquearRestones(e: BlockPlaceEvent) {
        if (!EduEssentials.getInstance().getBoolean("disable.hopper")) return
        if (e.player.hasPermission("antidupe.admin")) return
        if (e.itemInHand != null && e.itemInHand.type == Material.REDSTONE) {
            e.isCancelled = true
            e.player.sendMessage(EduEssentials.getInstance().message("blocked.redstone"))
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun bloquearRestoneTocha(e: BlockPlaceEvent) {
        if (!EduEssentials.getInstance().getBoolean("disable.restone")) return
        if (e.player.hasPermission("antidupe.admin")) return
        if (e.itemInHand != null && e.itemInHand.type == Material.REDSTONE_TORCH_ON ||
            e.itemInHand.type == Material.REDSTONE_TORCH_OFF ||
            e.itemInHand.type.name.startsWith("PISTON")
        ) {
            e.isCancelled = true
            e.player.sendMessage(EduEssentials.getInstance().message("blocked.redstone"))
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun pegarRestone(e: PlayerPickupItemEvent) {
        if (!EduEssentials.getInstance().getBoolean("disable.restone")) return
        if (e.player.hasPermission("antidupe.admin")) return
        if (e.item.itemStack.type == Material.REDSTONE) {
            e.isCancelled = true
            e.item.remove()
            e.player.sendMessage(EduEssentials.getInstance().message("blocked.redstone"))
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun bloquearBigorna(e: PlayerInteractEvent) {
        if (!EduEssentials.getInstance().getBoolean("disable.anvil")) return
        if (e.player.hasPermission("antidupe.admin")) return
        if (e.action == Action.RIGHT_CLICK_BLOCK && e.clickedBlock.type == Material.ANVIL) {
            e.isCancelled = true
            e.player.sendMessage(EduEssentials.getInstance().message("blocked.anvil"))
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun bloquearMesaDeEncantamento(e: PlayerInteractEvent) {
        if (!EduEssentials.getInstance().getBoolean("disable.enchanment-table")) return
        if (e.player.hasPermission("antidupe.admin")) return
        if (e.action == Action.RIGHT_CLICK_BLOCK && e.clickedBlock.type == Material.ENCHANTMENT_TABLE) {
            e.isCancelled = true
            e.player.sendMessage(EduEssentials.getInstance().message("blocked.enchantment-table"))
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun bloquearMoldura(e: PlayerInteractEvent) {
        if (!EduEssentials.getInstance().getBoolean("disable.enchanment-table")) return
        if (e.player.hasPermission("antidupe.admin")) return
        if (e.action == Action.RIGHT_CLICK_BLOCK && e.clickedBlock.type == Material.ENCHANTMENT_TABLE) {
            e.isCancelled = true
            e.player.sendMessage(EduEssentials.getInstance().message("blocked.enchantment-table"))
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun bloquearFunil(e: PlayerInteractEvent) {
        if (e.player.hasPermission("antidupe.admin")) return
        if (!EduEssentials.getInstance().getBoolean("disable.hopper")) return
        if (e.action == Action.RIGHT_CLICK_BLOCK && e.clickedBlock.type == Material.HOPPER) {
            e.isCancelled = true
            e.player.sendMessage(EduEssentials.getInstance().message("blocked.hopper"))
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun bloquearAbrirBauDentroDeVeiculo(e: PlayerInteractEvent) {
        val player = e.player!!
        if (player.hasPermission("antidupe.admin")) return
        if (!EduEssentials.getInstance().getBoolean("disable.vehicle-inventory")) return
        if (player.vehicle == null) return
        if (e.action == Action.RIGHT_CLICK_BLOCK && e.clickedBlock.type == Material.CHEST) {
            e.isCancelled = true
            player.sendMessage(EduEssentials.getInstance().message("blocked.vehicle-inventory"))
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun bloquearBarco(e: VehicleEnterEvent) {
        if (e.vehicle !is Boat) return
        if (e.entered is Player) return
        val player = e.entered as Player
        if (player.hasPermission("antidupe.admin")) return
        if (!EduEssentials.getInstance().getBoolean("disable.boat")) return
        e.isCancelled = true
        player.sendMessage(EduEssentials.getInstance().message("blocked.boat"))
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun bloquearCarrinho(e: VehicleEnterEvent) {
        if (e.vehicle !is Minecart) return
        if (e.entered is Player) return
        val player = e.entered as Player
        if (player.hasPermission("antidupe.admin")) return
        if (!EduEssentials.getInstance().getBoolean("disable.cart")) return
        e.isCancelled = true
        player.sendMessage(EduEssentials.getInstance().message("blocked.cart"))
    }
}