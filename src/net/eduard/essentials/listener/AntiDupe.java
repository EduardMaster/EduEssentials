package net.eduard.essentials.listener;

import net.eduard.api.lib.manager.EventsManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import net.eduard.essentials.EduEssentials;

public class AntiDupe extends EventsManager {
	
	@EventHandler
	public void bloquearRestones(BlockPlaceEvent e) {
		if (!EduEssentials.getInstance().getBoolean("hopper-blocked"))return;
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getItemInHand() != null) {
			if (e.getItemInHand().getType() == Material.REDSTONE) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(EduEssentials.getInstance().message("redstone-blocked"));
			}
		}
	}

	@EventHandler
	public void bloquearRestoneTocha(BlockPlaceEvent e) {
		if (!EduEssentials.getInstance().getBoolean("restone-blocked"))return;
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getItemInHand() != null) {
			if (e.getItemInHand().getType() == Material.REDSTONE_TORCH_ON
					|| e.getItemInHand().getType() == Material.REDSTONE_TORCH_OFF
					|| e.getItemInHand().getType().name().startsWith("PISTON")) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(EduEssentials.getInstance().message("redstone-blocked"));
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void pegarRestone(PlayerPickupItemEvent e) {
		if (!EduEssentials.getInstance().getBoolean("restone-blocked"))return;
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getItem().getItemStack().getType() == Material.REDSTONE) {
			e.setCancelled(true);
			e.getItem().remove();
			
			e.getPlayer().sendMessage(EduEssentials.getInstance().message("redstone-blocked"));
		}
	}

	
	@EventHandler
	public void bloquearBigorna(PlayerInteractEvent e) {
		if (!EduEssentials.getInstance().getBoolean("anvil-blocked"))return;
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.ANVIL) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(EduEssentials.getInstance().message("anvil-disabled"));
			}
		}
	}

	@EventHandler
	public void bloquearMesaDeEncantamento(PlayerInteractEvent e) {
		if (!EduEssentials.getInstance().getBoolean("enchament-table-blocked"))return;
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(EduEssentials.getInstance().message("enchantment-table-disabled"));
			}
		}
	}

	@EventHandler
	public void bloquearFunil(PlayerInteractEvent e) {
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.HOPPER) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(EduEssentials.getInstance().message("hopper-disabled"));
			}
		}
	}

}
