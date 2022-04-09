
package net.eduard.essentials.listener;

import java.util.HashMap;
import java.util.Map;


import net.eduard.api.lib.modules.MineReflect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.EventsManager;

public class ComboCounter extends EventsManager {

    private static final Map<Player, Integer> combos = new HashMap<>();


    private static final Map<Player, Long> lastHit = new HashMap<>();
    public static Map<Player, Integer> getCombos() {
        return combos;
    }

    @EventHandler
    public void event(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player entity = (Player) e.getEntity();
            if (getCombos().containsKey(entity)) {
                entity.sendMessage("§aSeu combo final foi este: §f"
                        + getCombos().get(entity));
                getCombos().put(entity, 0);
            }

        }
        if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            if (getCombos().containsKey(player)) {
                if (lastHit.containsKey(player)) {
                    Long tempo = lastHit.get(player);
                    long agora = Mine.getNow();
                    if (tempo + 50 > agora) {
                        return;
                    }
                }
                int combo = 1;
                getCombos().put(player, combo = (getCombos().get(player) + 1));
                MineReflect.sendActionBar(player, "§aVocê acertou mais um combo! §cCombo atual: §f" + combo);
                lastHit.put(player, System.currentTimeMillis());
            }

        }
    }

}
