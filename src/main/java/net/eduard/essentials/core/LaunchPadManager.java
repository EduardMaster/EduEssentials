package net.eduard.essentials.core;

import java.util.HashMap;
import java.util.Map;

import net.eduard.api.lib.manager.EventsManager;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.game.Jump;

@SuppressWarnings("unused")
public class LaunchPadManager extends EventsManager {

    public static final Map<World, Boolean> WORLDS = new HashMap<>();

    public static final FallManager NO_FALL = new FallManager();

    private int blockHigh;
    private int blockId = 20;
    private int blockData = -1;
    private Jump jump;

    public LaunchPadManager() {
    }

    public LaunchPadManager(int blockId, Jump jump) {
        this.blockId = blockId;
        this.jump = jump;
    }

    public LaunchPadManager(int blockHigh, int blockId, int blockData, Jump jump) {
        super();
        this.blockHigh = blockHigh;
        this.blockId = blockId;
        this.blockData = blockData;
        this.jump = jump;
    }

    public LaunchPadManager(int blockHigh, int blockId, Jump jump) {
        this.blockHigh = blockHigh;
        this.blockId = blockId;
        this.jump = jump;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void event(PlayerMoveEvent e) {
        if (Mine.equals2(e.getFrom(), e.getTo())) return;
        Player player = e.getPlayer();
        Block block = e.getTo().getBlock().getRelative(0,
                blockHigh, 0);
        if (blockData != -1 && blockData != block.getData())
            return;
        if (block.getTypeId() != blockId)
            return;
        WORLDS.putIfAbsent(player.getWorld(), true);
        if (!WORLDS.get(player.getWorld())) return;
        jump.create(player);
        if (!NO_FALL.getPlayers().contains(player))
            NO_FALL.getPlayers().add(player);


    }

    public int getBlockHigh() {
        return blockHigh;
    }

    public void setBlockHigh(int blockHigh) {
        this.blockHigh = blockHigh;
    }

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public int getBlockData() {
        return blockData;
    }

    public void setBlockData(int blockData) {
        this.blockData = blockData;
    }

    public Jump getJump() {
        return jump;
    }

    public void setJump(Jump jump) {
        this.jump = jump;
    }


}
