
package net.eduard.essentials.manager;

import java.util.Collection;

import net.eduard.essentials.Main;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.EventsManager;

public class AutoPickupEvents extends EventsManager {


    @EventHandler(priority = EventPriority.HIGHEST)
    public void Matar(EntityDeathEvent e) {
        Player p = e.getEntity().getKiller();

        if (p == null)
            return;
        if (p.hasPermission("autopickup.mob.drops")) {
            boolean dropsMonstros = Main.getInstance()
                    .getBoolean("autopickup." + p.getName().toLowerCase() + ".mob-drops");

            if (!dropsMonstros)
                return;
            PlayerInventory inv = p.getInventory();
            int slotsVasio = Mine.getEmptySlotsAmount(inv);
            if (e.getDrops().size() < slotsVasio) {
                for (ItemStack item : e.getDrops()) {
                    inv.addItem(item);
                }

            }
            e.getDrops().clear();
        }

    }

    @EventHandler
    public void Quebrar(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("autopickup.block.drops")) {
            boolean dropsBlocos = Main.getInstance()
                    .getBoolean("autopickup." + p.getName().toLowerCase() + ".block-drops");
            if (!dropsBlocos)
                return;
            new BukkitRunnable() {

                @Override
                public void run() {
                    Collection<Entity> drops = e.getBlock().getLocation().getWorld()
                            .getNearbyEntities(e.getBlock().getLocation(), 1, 1, 1);
                    for (Entity entidade : drops) {
                        if (entidade instanceof Item) {
                            Item drop = (Item) entidade;
                            e.getPlayer().getInventory().addItem(drop.getItemStack());
                            drop.remove();

                        }
                    }

                }
            }.runTaskAsynchronously(Main.getInstance());
        }
    }

}
