
package net.eduard.essentials.listener;

import java.util.HashMap;

import net.eduard.api.lib.modules.MineReflect;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.EventsManager;
import net.eduard.essentials.EduEssentials;

public class EssentialsEvents extends EventsManager {
    private static final HashMap<Player, Long> lastCommand = new HashMap<>();

    @EventHandler
    public void join(PlayerJoinEvent e){
        StringBuilder header = new StringBuilder();
        for (String linha : EduEssentials.getInstance().getConfigs().getMessages("tab-header")){
            header.append(Mine.getReplacers(linha,e.getPlayer()));
            header.append("\n");
        }
        StringBuilder footer = new StringBuilder();
        for (String linha : EduEssentials.getInstance().getConfigs().getMessages("tab-footer")){
            footer.append(Mine.getReplacers(linha,e.getPlayer()));
            footer.append("\n");
        }
        MineReflect.setTabList(e.getPlayer(),header.toString(),footer.toString() );
    }


    @EventHandler
    public void onSignChangeEvent(SignChangeEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("sign.color")) {
            for (int i = 0; i < e.getLines().length; i++) {
                e.setLine(i, ChatColor.translateAlternateColorCodes('&', e.getLines()[i])) ;
            }
        }
    }


    @EventHandler
    public void event(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPermission("command.delay.bypass")) {
            if (lastCommand.containsKey(player)) {
                Long teste = lastCommand.get(player);
                long agora = System.currentTimeMillis();
                boolean test = agora > (teste + 1000 * 3);
                if (!test) {
                    player.sendMessage(EduEssentials.getInstance().message("command-cooldown"));
                    e.setCancelled(true);

                } else {
                    lastCommand.put(player, System.currentTimeMillis());
                }
            } else {
                lastCommand.put(player, System.currentTimeMillis());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChatTabCompleteEvent(PlayerChatTabCompleteEvent e) {
        for (String msg : EduEssentials.getInstance().getMessages("blocked-tab-commads")) {
            if (e.getChatMessage().toLowerCase().startsWith(msg.toLowerCase())) {
                e.getTabCompletions().clear();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        for (String msg : EduEssentials.getInstance().getMessages("blocked-commads")) {
            if (e.getMessage().toLowerCase().startsWith(msg.toLowerCase())) {
                e.setCancelled(true);
                player.sendMessage(Mine.MSG_NO_PERMISSION);
            }
        }
    }

    @EventHandler
    public void event(PlayerMoveEvent e) {

    }

    @EventHandler
    public void event(PlayerJoinEvent e) {
    }

}
