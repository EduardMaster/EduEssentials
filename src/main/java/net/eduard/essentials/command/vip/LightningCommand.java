
package net.eduard.essentials.command.vip;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.lib.manager.CommandManager;

public class LightningCommand extends CommandManager {
	public String messageOn = "§6Luz ativada!";
	public String messageOff = "§6Luz desativada!";

	public LightningCommand() {
		super("lightning");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
				player.removePotionEffect(PotionEffectType.NIGHT_VISION);
				player.sendMessage(messageOff);
			} else {
				player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20*60*60*24, 0));
				player.sendMessage(messageOn);
			}

		} 

		return true;
	}

}
