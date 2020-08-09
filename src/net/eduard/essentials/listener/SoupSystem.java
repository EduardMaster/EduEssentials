
package net.eduard.essentials.listener;

import net.eduard.api.lib.manager.EventsManager;
import net.eduard.essentials.EduEssentials;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.config.Config;

import java.util.Arrays;
import java.util.List;


public class SoupSystem extends EventsManager {

    private final List<String> signFormat = Arrays.asList("&f=======", "&aSopas!", "&2Clique!", "&f======");

    @EventHandler
    public void event(SignChangeEvent e) {
        Player p = e.getPlayer();
        if (e.getLine(0).toLowerCase().contains("soup.sign-tag")) {
            int id = 0;
            for (String text : signFormat) {
                e.setLine(id, Mine.removeBrackets(text));
                id++;
            }
            p.sendMessage(EduEssentials.getInstance().message("soup.create-sign"));
        }
    }

    @EventHandler
    public void event(FoodLevelChangeEvent e) {

        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (EduEssentials.getInstance().getBoolean("soup.no-change-food-level")) {
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
        Config config = EduEssentials.getInstance().getConfigs();
        if (config.getBoolean("soup.enabled")) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (e.getClickedBlock().getState() instanceof Sign) {
                    Sign sign = (Sign) e.getClickedBlock().getState();
                    if (sign.getLine(0).equalsIgnoreCase(signFormat.get(0))) {
                        Inventory inv = Mine.newInventory(EduEssentials.getInstance().message("soup.menu-title"), 6 * 9);

                        for (ItemStack item : inv) {
                            if (item == null) {
                                inv.addItem(EduEssentials.getInstance().getSoup());
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
                int value = config.getInt("soup.recover-value");
                if (p.getHealth() < p.getMaxHealth()) {

                    double calc = p.getHealth() + value;
                    p.setHealth(Math.min(calc, p.getMaxHealth()));
                    remove = true;
                }
                if (!config.getBoolean("soup.no-change-food-level")) {
                    if (p.getFoodLevel() < 20) {
                        int calc = value + p.getFoodLevel();
                        p.setFoodLevel(Math.min(calc, 20));
                        p.setSaturation(p.getSaturation() + 5);
                        remove = true;
                    }
                }
                if (remove) {
                    e.setUseItemInHand(Result.DENY);

                    p.setItemInHand(EduEssentials.getInstance().getSoupEmpty().clone());
                    config.getSound("soup.sound").create(p);

                }
            }
        }
    }

}
