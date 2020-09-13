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
		if (!EduEssentials.getInstance().getBoolean("disable.hopper"))return;
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getItemInHand() != null) {
			if (e.getItemInHand().getType() == Material.REDSTONE) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(EduEssentials.getInstance().message("blocked.redstone"));
			}
		}
	}

	@EventHandler
	public void bloquearRestoneTocha(BlockPlaceEvent e) {
		if (!EduEssentials.getInstance().getBoolean("disable.restone"))return;
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getItemInHand() != null) {
			if (e.getItemInHand().getType() == Material.REDSTONE_TORCH_ON
					|| e.getItemInHand().getType() == Material.REDSTONE_TORCH_OFF
					|| e.getItemInHand().getType().name().startsWith("PISTON")) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(EduEssentials.getInstance().message("blocked.redstone"));
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void pegarRestone(PlayerPickupItemEvent e) {
		if (!EduEssentials.getInstance().getBoolean("disable.restone"))return;
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getItem().getItemStack().getType() == Material.REDSTONE) {
			e.setCancelled(true);
			e.getItem().remove();
			
			e.getPlayer().sendMessage(EduEssentials.getInstance().message("blocked.redstone"));
		}
	}

	
	@EventHandler
	public void bloquearBigorna(PlayerInteractEvent e) {
		if (!EduEssentials.getInstance().getBoolean("disable.anvil"))return;
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.ANVIL) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(EduEssentials.getInstance().message("blocked.anvil"));
			}
		}
	}

	@EventHandler
	public void bloquearMesaDeEncantamento(PlayerInteractEvent e) {
		if (!EduEssentials.getInstance().getBoolean("disable.enchanment-table"))return;
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(EduEssentials.getInstance().message("blocked.enchantment-table"));
			}
		}
	}

	@EventHandler
	public void bloquearFunil(PlayerInteractEvent e) {
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (!EduEssentials.getInstance().getBoolean("disable.hopper"))return;
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.HOPPER) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(EduEssentials.getInstance().message("blocked.hopper"));
			}
		}
	}

}
