package net.eduard.essentials.command;

import net.eduard.api.lib.manager.CommandManager;
import net.eduard.api.lib.modules.Mine;
import net.eduard.essentials.EduEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class WarpsCommand extends CommandManager {
    public WarpsCommand() {
        super("warps", "warplist", "locais");

    }

    public String message = "§6Suas warps: §e$warps";

    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {

        Set<String> list = EduEssentials.getInstance().getStorage().getKeys("warps");

        StringBuilder builder = new StringBuilder();
        int id = 0;
        for (String sec : list) {
            if (id != 0) {
                builder.append(", ");
            }
            id++;
            builder.append(sec);
        }
        sender.sendMessage(message.replace("$warps",
                builder.toString()));


        return true;
    }
}
