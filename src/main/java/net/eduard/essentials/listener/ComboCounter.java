
package net.eduard.essentials.listener;

import java.util.HashMap;
import java.util.Map;


import net.eduard.api.lib.modules.MineReflect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.EventsManager;

public class ComboCounter extends EventsManager {

    @EventHandler
    public void event(PlayerMoveEvent e) {

    }


    private static final Map<Player, Integer> combos = new HashMap<>();

    public static Map<Player, Integer> getCombos() {
        return combos;
    }

    private static final Map<Player, Long> ultimoTapa = new HashMap<>();

    @EventHandler
    public void event(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player entity = (Player) e.getEntity();
            if (getCombos().containsKey(entity)) {
                entity.sendMessage("§aSeu combo final foi este: §2"
                        + getCombos().get(entity));
                getCombos().put(entity, 0);
            }

        }
        if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();

            if (getCombos().containsKey(player)) {
                if (ultimoTapa.containsKey(player)) {
                    Long tempo = ultimoTapa.get(player);
                    long agora = Mine.getNow();
                    if (tempo + 50 > agora) {
                        return;
                    }
                }
                int combo = 1;
                getCombos().put(player,
                        combo = (getCombos().get(player) + 1));
                MineReflect.sendActionBar(player, "§aVoce acertou mais um combo! §cCombo atual: " + combo);
                ultimoTapa.put(player, System.currentTimeMillis());
            }

        }
    }

}
