package net.eduard.essentials.command.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.game.Title;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.api.lib.modules.VaultAPI;
import net.eduard.essentials.EduEssentials;

public class SetGroupCommand extends CommandManager {
    public List<String> messages = new ArrayList<>();
    public Title title = new Title("$player", "$group", 20, 20, 20);
    public String defaultGroup = "membro";

    public String getGroup(Player player) {
        UUID id = player.getUniqueId();
        EduEssentials.getInstance().getConfigs().add(id.toString(), defaultGroup);
        return EduEssentials.getInstance().getConfigs().getString(id.toString());
    }

    public SetGroupCommand() {
        super("setgroup");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {
        if (args.length <= 1) {
            sendUsage(sender);
            return true;
        }
        // /setgroup dono eduard
        // /setgroup eduard dono
        String name = args[0];
        String group = args[1];
        if (!Mine.existsPlayer(sender, name)) return true;
        Player target = Mine.getPlayer(name);
        Title t = (Title) title.copy();
        VaultAPI.getPermission().playerAddGroup(target, group);
        VaultAPI.getPermission().playerRemoveGroup(target, getGroup(target));
        EduEssentials.getInstance().getConfigs().set(target.getUniqueId().toString(), group);
        t.setSubTitle(getValues(t.getSubTitle(), name, group));
        t.setTitle(getValues(t.getTitle(), name, group));
        for (Player player : Mine.getPlayers()) {
            t.create(player);
            for (String message : messages) {
                player.sendMessage(getValues(message, name, group));
            }
        }


        return true;
    }

    public String getValues(String value, String name, String group) {
        return value.replace("$player", name).replace("$group", group);
    }

    @EventHandler
    public void event(PlayerJoinEvent e) {
        getGroup(e.getPlayer());
    }

}
