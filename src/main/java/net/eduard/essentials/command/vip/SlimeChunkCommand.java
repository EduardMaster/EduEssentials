package net.eduard.essentials.command.vip;

import java.util.List;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.EduEssentials;

public class SlimeChunkCommand extends CommandManager {


    public SlimeChunkCommand() {
        super("slime", "gosma");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;
        List<Player> lista = EduEssentials.getInstance().getManager().getSlimeChunkActive();
        if (lista.contains(player)) {
            lista.remove(player);

            player.sendMessage(" ");
            player.sendMessage("§cVocê desligou o localizador de Slime Chunk, Você não receber\u00E1");
            player.sendMessage("§cmas estes barulhos, caso deseje ativar use §nslime§c");
            player.sendMessage(" ");
            player.playSound(player.getLocation(), Sound.SLIME_WALK, 5.0F, 5.0F);

        } else {

            player.sendMessage(" ");
            player.sendMessage("§aVocê ativou o localizador de §nSlime Chunks§a. Sempre que você");
            player.sendMessage("§apassar em uma Slime Chunk você escutará um §nbarulho de slime§a");
            player.sendMessage("§afique atento e ative os sons do seu Jogo");
            player.sendMessage(" ");
            player.playSound(player.getLocation(), Sound.SLIME_WALK, 5.0F, 5.0F);
            lista.add(player);
        }


        return true;

    }
}
