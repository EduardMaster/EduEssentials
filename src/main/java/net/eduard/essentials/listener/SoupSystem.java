
package net.eduard.essentials.listener;

import net.eduard.api.lib.game.SoundEffect;
import net.eduard.api.lib.manager.EventsManager;
import net.eduard.api.lib.modules.Extra;
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
        Player player = e.getPlayer();
        if (!e.getLine(0).toLowerCase().contains("soup.sign-tag")) return;
        int id = 0;
        for (String text : signFormat) {
            e.setLine(id, Extra.removeBrackets(text));
            id++;
        }
        player.sendMessage(EduEssentials.getInstance().message("soup.create-sign"));

    }

    @EventHandler
    public void event(FoodLevelChangeEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        if (!EduEssentials.getInstance().getBoolean("soup.no-change-food-level")) return;
        if (e.getFoodLevel() == 20) return;
        e.setFoodLevel(20);
        player.setExhaustion(0);
        player.setSaturation(20);

    }

    @EventHandler
    public void effect(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Config config = EduEssentials.getInstance().getConfigs();
        if (!config.getBoolean("soup.enabled")) return;
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign) e.getClickedBlock().getState();
                if (!sign.getLine(0).equalsIgnoreCase(signFormat.get(0))) return;
                Inventory inv = Mine.newInventory(EduEssentials.getInstance().message("soup.menu-title"), 6 * 9);

                for (ItemStack item : inv) {
                    if (item == null) {
                        inv.addItem(EduEssentials.getInstance().getSoup());
                    }
                }
                player.openInventory(inv);

            }
        }
        if (e.getItem() == null)
            return;

        if (e.getItem().getType() != Material.MUSHROOM_SOUP) return;
        boolean remove = false;
        e.setCancelled(true);
        if (e.getAction().name().contains("LEFT")) {
            e.setCancelled(false);
        }
        int value = config.getInt("soup.recover-value");
        if (player.getHealth() < player.getMaxHealth()) {

            double calc = player.getHealth() + value;
            player.setHealth(Math.min(calc, player.getMaxHealth()));
            remove = true;
        }
        if (!config.getBoolean("soup.no-change-food-level")) {
            if (player.getFoodLevel() < 20) {
                int calc = value + player.getFoodLevel();
                player.setFoodLevel(Math.min(calc, 20));
                player.setSaturation(player.getSaturation() + 5);
                remove = true;
            }
        }
        if (remove) {
            e.setUseItemInHand(Result.DENY);

            player.setItemInHand(EduEssentials.getInstance().getSoupEmpty().clone());
            config.get("soup.sound", SoundEffect.class).create(player);

        }


    }

}
