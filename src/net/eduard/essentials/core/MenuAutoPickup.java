package net.eduard.essentials.core;

import net.eduard.essentials.EduEssentials;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.EventsManager;


public class MenuAutoPickup extends EventsManager {
	private static String titulo = "§0Auto Pickup";

	public static void abrir(Player player) {
		Inventory menu = Mine.newInventory(titulo, 3 * 9);
		menu.setItem(Mine.getPosition(2, 7), Mine.newItem(Material.DIAMOND_PICKAXE, "§aDrops de mineração"));
		menu.setItem(Mine.getPosition(2, 3), Mine.newItem(Material.ROTTEN_FLESH, "§aDrops de monstros e animais"));

		boolean dropsMonstros = EduEssentials.getInstance()
				.getBoolean("autopickup." + player.getName().toLowerCase() + ".mob-drops");
		boolean dropsBlocos = EduEssentials.getInstance()
				.getBoolean("autopickup." + player.getName().toLowerCase() + ".block-drops");

		ItemStack verde = new ItemStack(Material.INK_SACK, 1, (short) 10);

		ItemStack cinza = new ItemStack(Material.INK_SACK, 1, (short) 8);

		ItemStack iconeMobDrops = dropsMonstros ? verde.clone() : cinza.clone();
		ItemStack iconeBlockDrops = dropsBlocos ? verde.clone() : cinza.clone();

		Mine.setName(iconeMobDrops, dropsMonstros ? "§aAtivar" : "§cDesativar");
		Mine.setLore(iconeMobDrops, dropsMonstros ? "§fEstado: §aAtivado" : "§fEstado: §cDesativado");

		Mine.setName(iconeBlockDrops, dropsBlocos ? "§aAtivar" : "§cDesativar");
		Mine.setLore(iconeBlockDrops, dropsBlocos ? "§fEstado: §aAtivado" : "§fEstado §cDesativado");
		menu.setItem(Mine.getPosition(2, 4), iconeMobDrops);
		menu.setItem(Mine.getPosition(2, 6), iconeBlockDrops);
		player.openInventory(menu);
	}

	@EventHandler
	public void event(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory().getName().equals(titulo)) {
				e.setCancelled(true);
				boolean dropsMonstros = EduEssentials.getInstance()
						.getBoolean("autopickup." + p.getName().toLowerCase() + ".mob-drops");
				boolean dropsBlocos = EduEssentials.getInstance()
						.getBoolean("autopickup." + p.getName().toLowerCase() + ".block-drops");
				int slot = e.getRawSlot();
				if (slot == Mine.getPosition(2, 6)) {
					EduEssentials.getInstance().getConfigs()
							.set("autopickup." + p.getName().toLowerCase() + ".block-drops", !dropsBlocos);
					abrir(p);
				}
				if (slot == Mine.getPosition(2,4)) {
					EduEssentials.getInstance().getConfigs()
							.set("autopickup." + p.getName().toLowerCase() + ".mob-drops", !dropsMonstros);
					abrir(p);
				}
			}
		}
	}
}
