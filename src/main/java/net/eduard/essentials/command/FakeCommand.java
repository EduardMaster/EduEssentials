
package net.eduard.essentials.command;

import net.eduard.api.lib.modules.MineReflect;
import net.eduard.essentials.core.Fake;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.manager.CommandManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FakeCommand extends CommandManager {

    public FakeCommand() {
        super("fake", "nickfalso");
    }

    private static final Map<Player, Fake> fakes = new HashMap<>();


    public static boolean canFake(String name) {
        for (Fake fake : fakes.values()) {
            if (Objects.requireNonNull(fake.getFake()).equalsIgnoreCase(name) || fake.getOriginal().equalsIgnoreCase(name)) {
                return false;
            }
        }
        return true;
    }

    public static void removeFake(String name) {
        for (Fake fake : fakes.values()) {
            if (name.equalsIgnoreCase(fake.getFake())) {
                reset(fake.getPlayer());
            }
        }
    }

    public static Fake getFake(Player player) {
        Fake fake = fakes.get(player);
        if (fake == null) {
            fake = new Fake(player, player.getName(), null);
            fakes.put(player, fake);
        }
        return fake;
    }

    public static void fake(Player player, String name) {
        Fake fake = getFake(player);
        fake.setFake(name);
        player.setDisplayName(name);
        player.setPlayerListName(name);
        player.setCustomName(name);
        player.setCustomNameVisible(true);
//		for (Player p : Mine.getPlayers()) {
//			if (p == player)continue;
//			p.hidePlayer(player);
//		}
//		for (Player p : Mine.getPlayers()) {
//			if (p == player)continue;
//			p.showPlayer(player);
//		}
        MineReflect.changeName(player, name);
    }

    public static void reset(Player player) {
        Fake fake = getFake(player);
        String name = fake.getOriginal();
        fake.setFake(name);
        MineReflect.changeName(player, name);
        player.setDisplayName(name);
        player.setPlayerListName(name);
    }

    @EventHandler
    public void event(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        removeFake(player.getName());
    }

    @EventHandler
    public void event(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        String name = player.getName();
        removeFake(name);
        getFake(player);
    }

    @Override
    public void playerCommand(@NotNull Player player, @NotNull String[] args) {
        if (args.length == 0) {
            player.sendMessage("§c/fake <jogador> §7Ativar um fake de um jogador");
            player.sendMessage("§c/fake resetar §7Resetar o fake");
            return;
        }
        String cmd = args[0];
        if (cmd.equalsIgnoreCase("reset") ||
            cmd.equalsIgnoreCase("resetar")) {
            reset(player);
            player.sendMessage("§aFake resetado!");
        } else {
            String fakeName = cmd;
            fake(player, fakeName);
            player.sendMessage("§aFake ativado do jogador " + fakeName);
        }
    }


}
