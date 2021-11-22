
package net.eduard.essentials.listener;

import net.eduard.api.lib.manager.EventsManager;
import net.eduard.api.lib.modules.Extra;
import net.eduard.essentials.EduEssentials;
import net.eduard.essentials.core.EssentialsManager;
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


public class SoupSystem extends EventsManager {


    @EventHandler
    public void soupSignCreation(SignChangeEvent e) {
        EssentialsManager manager = EduEssentials.getInstance().getManager();
        Player player = e.getPlayer();
        if (!e.getLine(0).toLowerCase().contains(manager.getSoupSignTitle())) return;
        int id = 0;
        for (String text : manager.getSoupSignFormat()) {
            e.setLine(id, Extra.removeBrackets(text));
            id++;
        }
        player.sendMessage(manager.getSoupSignCreation());

    }

    @EventHandler
    public void semFome(FoodLevelChangeEvent e) {
        EssentialsManager manager = EduEssentials.getInstance().getManager();
        if (!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        if (!manager.getNoChangeFoodLevel()) return;
        if (e.getFoodLevel() == 20) return;
        e.setFoodLevel(20);
        player.setExhaustion(0);
        player.setSaturation(20);

    }

    @EventHandler
    public void effect(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        EssentialsManager manager = EduEssentials.getInstance().getManager();

        if (!manager.getSoupSystem()) return;
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign) e.getClickedBlock().getState();
                if (!sign.getLine(0).equalsIgnoreCase(manager.getSoupSignFormat().get(0))) return;
                Inventory inv = Mine.newInventory(EduEssentials.getInstance().message("soup.menu-title"), 6 * 9);

                for (ItemStack item : inv) {
                    if (item == null) {
                        inv.addItem(EduEssentials.getInstance().getManager().getSoup());
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
        int value = manager.getSoupRecoverValue();
        if (player.getHealth() < player.getMaxHealth()) {

            double calc = player.getHealth() + value;
            player.setHealth(Math.min(calc, player.getMaxHealth()));
            remove = true;
        }
        if (!manager.getNoChangeFoodLevel()) {
            if (player.getFoodLevel() < 20) {
                int calc = value + player.getFoodLevel();
                player.setFoodLevel(Math.min(calc, 20));
                player.setSaturation(player.getSaturation() + 5);
                remove = true;
            }
        }
        if (remove) {
            e.setUseItemInHand(Result.DENY);

            player.setItemInHand(EduEssentials.getInstance().getManager().getSoupEmpty().clone());
            manager.getSoupSound().create(player);

        }


    }

}
