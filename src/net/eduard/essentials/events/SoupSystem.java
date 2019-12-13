
package net.eduard.essentials.events;

import net.eduard.api.lib.manager.EventsManager;
import net.eduard.essentials.Main;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.config.Config;


public class SoupSystem extends EventsManager {


    @EventHandler
    public void event(SignChangeEvent e) {

        Player p = e.getPlayer();
        if (e.getLine(0).toLowerCase().contains("Soup.sign-tag")) {
            int id = 0;
            for (String text : Main.getInstance().getConfigs().getMessages("Soup.sign-format")) {
                e.setLine(id, Mine.removeBrackets(text));
                id++;
            }
            p.sendMessage(Main.getInstance().message("Soup.create-sign"));
        }
    }

    @EventHandler
    public void event(FoodLevelChangeEvent e) {

        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (Main.getInstance().getBoolean("Soup.no-change-food-level")) {
                if (e.getFoodLevel() <= 20) {
                    e.setFoodLevel(20);
                    p.setExhaustion(0);
                    p.setSaturation(20);
                }
            }
        }


    }

    @EventHandler
    public void effect(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Config config = Main.getInstance().getConfigs();
        if (config.getBoolean("Soup.enabled")) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (e.getClickedBlock().getState() instanceof Sign) {
                    Sign sign = (Sign) e.getClickedBlock().getState();
                    if (sign.getLine(0).equalsIgnoreCase(Mine.removeBrackets(Main.getInstance().getConfigs().getMessages("SoupSystem.sign-format").get(0)))) {
                        Inventory inv = Mine.newInventory(Main.getInstance().message("Soup.menu-title"), 6 * 9);

                        for (ItemStack item : inv) {
                            if (item == null) {
                                inv.addItem(Main.getInstance().getSoup());
                            }
                        }
                        p.openInventory(inv);
                    }
                }
            }
            if (e.getItem() == null)
                return;

            if (e.getItem().getType() == Material.MUSHROOM_SOUP) {


                boolean remove = false;
                e.setCancelled(true);
                if (e.getAction().name().contains("LEFT")) {
                    e.setCancelled(false);
                }
                int value = config.getInt("Soup.recover-value");
                if (p.getHealth() < p.getMaxHealth()) {

                    double calc = p.getHealth() + value;
                    p.setHealth(calc >= p.getMaxHealth() ? p.getMaxHealth() : calc);
                    remove = true;
                }
                if (!config.getBoolean("Soup.no-change-food-level")) {
                    if (p.getFoodLevel() < 20) {
                        int calc = value + p.getFoodLevel();
                        p.setFoodLevel(calc >= 20 ? 20 : calc);
                        p.setSaturation(p.getSaturation() + 5);
                        remove = true;
                    }
                }
                if (remove) {
                    e.setUseItemInHand(Result.DENY);

                    p.setItemInHand(Main.getInstance().getSoupEmpty().clone());
                    config.getSound("Soup.sound").create(p);

                }
            }
        }
    }


}
