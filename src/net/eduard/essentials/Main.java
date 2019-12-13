
package net.eduard.essentials;

import java.util.*;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.config.ConfigSection;
import net.eduard.api.lib.game.Jump;
import net.eduard.api.lib.game.SoundEffect;
import net.eduard.api.lib.storage.Storable;
import net.eduard.essentials.events.AntiDupe;
import net.eduard.essentials.events.AntiMacro;
import net.eduard.essentials.events.DoubleJump;
import net.eduard.essentials.events.SoupSystem;
import net.eduard.essentials.manager.LaunchPadManager;
import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import net.eduard.api.lib.config.Config;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.api.lib.storage.StorageAPI;
import net.eduard.api.server.EduardPlugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;
import org.bukkit.util.Vector;

public class Main extends EduardPlugin {
    private static Main plugin;
    public static Main getInstance() {
        return plugin;
    }
    private Config commands;
    private ItemStack soup;
    private ItemStack soupEmpty;
    private Map<Player, Long> requestsDelay = new HashMap<>();
    private  Map<Player, Player> requests = new HashMap<>();
    private ArrayList<Player> slimeChunkActive = new ArrayList<>();
    private Jump doubleJump;







    @Override
    public void onEnable() {
        plugin = this;
        setFree(true);
        commands = new Config(this, "commands.yml");

        new AntiDupe().register(this);
        new AntiMacro().register(this);
        new DoubleJump().register(this);
        new LaunchPadManager().register(this);
        new SoupSystem().register(this);
        reload();

    }





    public void save() {



    }

    public void reload() {
        commands.reloadConfig();
        config.reloadConfig();
        storage.reloadConfig();
        StorageAPI.register(LaunchPadManager.class);


        config.add("Soup.enabled", true);
        config.add("Soup.sign-tag", "soup");
        config.add("Soup.item-full", Mine.newItem(Material.MUSHROOM_SOUP, "§eSopa Deliciosa",1,0,"§aRecupera vida ao ser ingerida"));
        config.add("Soup.item-empty", Mine.newItem(Material.BOWL, "§aSopa tomada"));
        config.add("Soup.create-sign", "&6Voce criou uma placa de sopas!");
        config.add("Soup.menu-title", "&c&lSopas gratis!");
        config.add("Soup.sign-format", Arrays.asList("&f=======", "&aSopas!"), "&2Clique!", "&f======");
        config.add("Soup.no-change-food-level", true);
        config.add("Soup.recover-value", 6);
        soup = (ItemStack) config.get("Soup.item-full");
        soupEmpty = (ItemStack) config.get("Soup.item-empty");
        config.add("Soup.sound",  SoundEffect.create("BURP"));
         config.add("DoubleJump.enabled",true);
        config.add("DoubleJump.effect",new Jump(true, 0.5, 2.5, SoundEffect.create("ENDERMAN_TELEPORT")));
        doubleJump = (Jump) config.get("DoubleJump.effect");
        config.add("Pads.sponge", new LaunchPadManager(-1,19,0,
		new Jump(SoundEffect.create("EXPLODE"), new Vector(0, 2, 0))));
		for (World world:Bukkit.getWorlds()){
			String path = "LaunchPad."+world.getName();
			config.add(path, true);
			LaunchPadManager.WORLDS.put(world, config.getBoolean(path));
		}
		config.saveConfig();

		LaunchPadManager.NO_FALL.register(this);
		doubleJump = (Jump) config.get("Double Jump.effect");
		for (ConfigSection sec : config.getSection("Pads").getValues()) {
			((LaunchPadManager) sec.getValue()).register(this);
		}



		/*
		for (CommandManager cmd : CommandManager.getCommandsRegistred().values()) {
			if (cmd.getPlugin().equals(this)) {
				cmd.unregisterCommand();
				cmd.unregisterListener();
			}
		}
		*/
        try {
            for (Class<?> claz : getClasses("net.eduard.essentials.command")) {
                StorageAPI.register((Class<? extends Storable>) claz);
                if (CommandManager.class.isAssignableFrom(claz)) {

                    CommandManager cmd = (CommandManager) claz.newInstance();
                    String path = "commands." + cmd.getClass().getSimpleName().toLowerCase();
//					if (commands.contains(path)) {
//						cmd = (CommandManager) commands.get(path);
//					} else {
                    config.add(path, false);
                    commands.set(path, cmd);
                    if (config.getBoolean(path)){
                        cmd.registerCommand(this);
                    }
//					}
//					config.add(path, true);
//					if (config.getBoolean(path)) {
//						cmd.registerCommand(this);
//						cmd.register(this);
//					}
                }

            }
//			StorageAPI.updateReferences();

            commands.saveConfig();
            config.saveConfig();
        } catch (Exception ex) {


            ex.printStackTrace();
        }



    }
    public ItemStack getSoup(){
        return soup;
    }

    public void onDisable() {
        save();
    }

    public Config getCommands() {
        return commands;
    }


    public ArrayList<Player> getSlimeChunkActive() {
        return slimeChunkActive;
    }


    public Jump getDoubleJump() {
        return doubleJump;
    }

    public ItemStack getSoupEmpty() {
        return soupEmpty;
    }

    public Map<Player, Long> getRequestsDelay() {
        return requestsDelay;
    }

    public Map<Player, Player> getRequests() {
        return requests;
    }
}
