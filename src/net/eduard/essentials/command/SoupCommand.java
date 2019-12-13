package net.eduard.essentials.command;

import java.util.ArrayList;
import java.util.List;

import net.eduard.essentials.Main;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.game.SoundEffect;
import net.eduard.api.lib.manager.CommandManager;

public class SoupCommand extends CommandManager {


	public SoupCommand() {
		super("soup", "sopas");
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (Mine.onlyPlayer(sender)) {
			Player p = (Player) sender;
			Mine.fill(p.getInventory(), Main.getInstance().getSoup());
			p.sendMessage(Main.getInstance().message("soup"));
		}
		return true;
	}

}
