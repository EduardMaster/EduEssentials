package net.eduard.essentials.listener;

import java.util.Collection;

import net.eduard.essentials.EduEssentials;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.EventsManager;

public class AutoPickup extends EventsManager {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void pickupMineDrops(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (!EduEssentials.getInstance().getBoolean("auto-pickup.enabled")) {
            return;
        }
        if (player.getItemInHand() == null)
            return;
        if (player.getItemInHand().getType() == Material.AIR)
            return;

        if (Mine.isFull(player.getInventory())) {
            player.sendMessage(EduEssentials.getInstance().message("inventory-full"));
            Collection<ItemStack> lista = e.getBlock().getDrops(player.getItemInHand());
            for (ItemStack item : lista) {
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), item);
            }
        } else {
            Collection<ItemStack> lista = e.getBlock().getDrops(player.getItemInHand());
            for (ItemStack item : lista) {
                player.getInventory().addItem(item);
            }
            new BukkitRunnable() {

                @Override
                public void run() {
                    Collection<Entity> drops = e.getBlock().getLocation().getWorld()
                            .getNearbyEntities(e.getBlock().getLocation(), 1, 1, 1);
                    for (Entity entidade : drops) {
                        if (entidade instanceof Item) {
                            Item drop = (Item) entidade;
                            drop.setPickupDelay(300);
                            drop.remove();
                        }
                    }

                }
            }.runTaskLater(EduEssentials.getInstance(), 1);
        }
    }



    @EventHandler(priority = EventPriority.HIGHEST)
    public void pickupMobDrops(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) return;
        Player player = e.getEntity().getKiller();
        if (player == null)
            return;
        if (player.hasPermission("autopickup.mob-drops")) {
            boolean dropsMonstros = EduEssentials.getInstance()
                    .getStorage().getBoolean("autopickup." + player.getName().toLowerCase() + ".mob-drops");

            if (!dropsMonstros)
                return;
            PlayerInventory playerInventory = player.getInventory();
            int slotsVasio = Mine.getEmptySlotsAmount(playerInventory);
            if (e.getDrops().size() < slotsVasio) {
                for (ItemStack item : e.getDrops()) {
                    playerInventory.addItem(item);
                }

            }
            e.getDrops().clear();
        }

    }

    @EventHandler
    public void Quebrar(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPermission("autopickup.block-drops")) return;
        boolean dropsBlocos = EduEssentials.getInstance()
                .getStorage().getBoolean("autopickup." + player.getName().toLowerCase() + ".block-drops");
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
        }.runTaskAsynchronously(EduEssentials.getInstance());

    }

    private static final String titulo = "§0Auto Pickup";

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
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();
        if (e.getInventory().getName().equals(titulo)) {
            e.setCancelled(true);
            boolean dropsMonstros = EduEssentials.getInstance().getStorage()
                    .getBoolean("autopickup." + player.getName().toLowerCase() + ".mob-drops");
            boolean dropsBlocos = EduEssentials.getInstance().getStorage()
                    .getBoolean("autopickup." + player.getName().toLowerCase() + ".block-drops");
            int slot = e.getRawSlot();
            if (slot == Mine.getPosition(2, 6)) {
                EduEssentials.getInstance().getStorage()
                        .set("autopickup." + player.getName().toLowerCase() + ".block-drops", !dropsBlocos);
                abrir(player);
            }
            if (slot == Mine.getPosition(2, 4)) {
                EduEssentials.getInstance().getStorage()
                        .set("autopickup." + player.getName().toLowerCase() + ".mob-drops", !dropsMonstros);
                abrir(player);
            }

        }
    }


}
