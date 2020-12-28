
package net.eduard.essentials.listener;

import net.eduard.api.lib.modules.Extra;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import net.eduard.api.lib.manager.EventsManager;
import org.bukkit.util.Vector;

public class ShowDamage extends EventsManager {


	@EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
	public void event(EntityDamageByEntityEvent e) {


		double dano = e.getFinalDamage();
		if (e.getEntity() instanceof ArmorStand)
			return;
		if (dano == 0)
			return;
		createTempArmourStand(e.getEntity().getLocation(), dano);
		if (e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
		}else {
			if (e.getDamager() instanceof Projectile) {
				Projectile projectile = (Projectile) e.getDamager();
				
				if (projectile.getShooter() instanceof Player) {
					Player p = (Player) projectile.getShooter();
				}
			} 
	
		}

	}




	public  void createTempArmourStand(Location location, double dano) {
		ArmorStand armor = (ArmorStand) location.getWorld()
				.spawnEntity(location.add(0,1,0), EntityType.ARMOR_STAND);


		armor.setCustomName("§f-§f" + (Extra.formatMoney(dano)) + "§c♥" );
		armor.setCustomNameVisible(true);
		armor.setGravity(true);
		armor.setMarker(true);
		armor.setVisible(false);
		armor.setSmall(true);
		armor.setVelocity(new Vector(0, 0.5, 0));
		new BukkitRunnable() {

			@Override
			public void run() {
				armor.remove();
			}
		}.runTaskLaterAsynchronously(getPlugin(), 40);
	}

}
