package net.eduard.essentials.listener;

import java.util.Random;

import net.eduard.api.lib.modules.Mine;
import org.bukkit.Chunk;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.lib.manager.EventsManager;
import net.eduard.essentials.EduEssentials;

public class SlimeChunkDetector extends EventsManager {

	@EventHandler
	public void split(SlimeSplitEvent e) {
		e.setCancelled(EduEssentials.getInstance().getConfigs().getBoolean("disable.slime-split"));
	}

	@EventHandler
	public void onSlimeChunk(PlayerMoveEvent e) {

		Player player = e.getPlayer();
		if (EduEssentials.getInstance().getSlimeChunkActive().contains(player)) {
			if (!Mine.equals(e.getFrom(), e.getTo())) {
				long worldSeed = player.getWorld().getSeed();
				Chunk jogadorChunk = player.getWorld().getChunkAt(player.getLocation());
				int x = jogadorChunk.getX();
				int z = jogadorChunk.getZ();

				Random random = new Random(
						worldSeed + x * x * 4987142 + x * 5947611 + z * z * 4392871L + z * 389711 ^ 0x3AD8025F);

				if (random.nextInt(10) == 0)
					player.playSound(player.getLocation(), Sound.SLIME_WALK, 2.0F, 2.0F);
			}
		}
	}
}