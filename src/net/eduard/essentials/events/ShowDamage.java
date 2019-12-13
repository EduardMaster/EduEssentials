
package net.eduard.essentials.events;

import net.eduard.api.lib.modules.Extra;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.api.lib.manager.EventsManager;
public class ShowDamage extends EventsManager {




	@EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
	public void event(EntityDamageByEntityEvent e) {
		// if (e.getDamager() instanceof Player) {
		// Player p = (Player) e.getDamager();
		double dano = e.getFinalDamage();
		if (e.getEntity() instanceof ArmorStand)
			return;
		if (dano == 0)
			return;
		createTempArmourStand(e.getEntity().getLocation(), dano);
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			
			//
			
		}else {
			if (e.getDamager() instanceof Projectile) {
				Projectile projectile = (Projectile) e.getDamager();
				
				if (projectile.getShooter() instanceof Player) {
					Player p = (Player) projectile.getShooter();
				//
				}
				
			} 
	
		}
		//Mine.sendActionBar(p, "§1" + (formato.format(dano / 2)) + "§c♥" );
	}
	//@EventHandler
	public void event(PlayerMoveEvent e){
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		p.sendMessage("§a----------------------------");
		p.sendMessage("§aPitch: "+loc.getPitch());
		p.sendMessage("§aYaw: "+loc.getYaw());
		p.sendMessage(" ");

	}



	public  void createTempArmourStand(Location location, double dano) {

		ArmorStand armor = (ArmorStand) location.getWorld().spawnEntity(location.add(0,2,0), EntityType.ARMOR_STAND);
		armor.setVisible(false);
		armor.setCustomName("§f-§f" + (Extra.MONEY.format(dano)) + "§c♥" );
		armor.setCustomNameVisible(true);
		armor.setGravity(false);
		armor.setSmall(true);
		//armor.setVelocity(new Vector(0, 1, 0));
		new BukkitRunnable() {

			@Override
			public void run() {
				armor.remove();
			}
		}.runTaskLaterAsynchronously(getPlugin(), 40);
	}

}
