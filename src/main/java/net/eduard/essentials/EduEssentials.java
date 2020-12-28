
package net.eduard.essentials;

import java.util.*;

import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.config.ConfigSection;
import net.eduard.api.lib.game.Jump;
import net.eduard.api.lib.game.SoundEffect;
import net.eduard.essentials.listener.*;
import net.eduard.essentials.core.LaunchPadManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.eduard.api.lib.config.Config;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.api.lib.storage.StorageAPI;
import net.eduard.api.server.EduardPlugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class EduEssentials extends EduardPlugin {

    private static Set<Player> gods = new HashSet();
    private static EduEssentials instance;

    public static EduEssentials getInstance() {
        return instance;
    }

    private ItemStack soup;
    private ItemStack soupEmpty;
    private final Map<Player, Long> requestsDelay = new HashMap<>();
    private final Map<Player, Player> requests = new HashMap<>();
    private final ArrayList<Player> slimeChunkActive = new ArrayList<>();
    private Jump doubleJump;

    public static Set<Player> getGods() {
        return gods;
    }


    @Override
    public void onEnable() {
        instance = this;
        super.onEnable();
        setFree(true);
        //StorageAPI com bug não salva lista de string
        new AntiDupe().register(this);
        new AntiMacro().register(this);
        new DoubleJump().register(this);
        new LaunchPadManager().register(this);
        new SoupSystem().register(this);
        new ShowDamage().register(this);
        new ComboCounter().register(this);
        new ClickCounter().register(this);
        new SlimeChunkDetector().register(this);
        LaunchPadManager.NO_FALL.register(this);
        reload();
    }


    public void save() {
        getStorage().saveConfig();
    }

    @Override
    public void configDefault() {
        getConfigs().add("soup.enabled", true);
        getConfigs().add("soup.sign-tag", "soup");
        getConfigs().add("soup.item-full", Mine.newItem(Material.MUSHROOM_SOUP, "§eSopa Deliciosa", 1, 0, "§aRecupera vida ao ser ingerida"));
        getConfigs().add("soup.item-empty", Mine.newItem(Material.BOWL, "§aSopa tomada"));
        getConfigs().add("soup.create-sign", "&6Voce criou uma placa de sopas!");
        getConfigs().add("soup.menu-title", "&c&lSopas gratis!");
        getConfigs().add("soup.no-change-food-level", true);
        getConfigs().add("soup.recover-value", 6);
        soup = getConfigs().get("soup.item-full", ItemStack.class);
        soupEmpty = getConfigs().get("soup.item-empty", ItemStack.class);
        getConfigs().add("soup.sound", SoundEffect.create("BURP"));
        getConfigs().add("doublejump.enabled", true);
        getConfigs().add("doublejump.effect", new Jump(true, 0.5, 2.5, SoundEffect.create("ENDERMAN_TELEPORT")));
        doubleJump = getConfigs().get("doublejump.effect", Jump.class);
        getConfigs().add("pads.sponge", new LaunchPadManager(-1, 19, 0,
                new Jump(SoundEffect.create("EXPLODE"), new Vector(0, 2, 0))));
        for (World world : Bukkit.getWorlds()) {
            String path = "launchpad." + world.getName();
            getConfigs().add(path, true);
            LaunchPadManager.WORLDS.put(world, getConfigs().getBoolean(path));
        }
        getConfigs().saveConfig();
        doubleJump = getConfigs().get("doublejump.effect", Jump.class);

        for (String key : getConfigs().getSection("pads").getKeys()) {
            LaunchPadManager launchpad = getConfigs().get("pads." + key, (LaunchPadManager.class));
            launchpad.register(this);
        }

    }

    public void reload() {
        getConfigs().reloadConfig();
        getStorage().reloadConfig();
        configDefault();

        for (Class<?> claz : getClasses("net.eduard.essentials.command")) {
            try {
                if (CommandManager.class.isAssignableFrom(claz)) {
                    String name = claz.getSimpleName().toLowerCase().replace("command", "");
                    CommandManager cmd = (CommandManager) claz.newInstance();
                    String path = "commands." + name;
                    Config commands = new Config(this, "commands/" + name + ".yml");
                    getConfigs().add(path, false);
                    if (commands.getKeys().isEmpty())
                        commands.set(cmd);
                    cmd = (CommandManager) commands.get(claz);
                    if (getConfigs().getBoolean(path)) {
                        log("Comando " + cmd.getName() + " ativado com sucesso.");
                        cmd.registerCommand(this);
                    } else {
                        log("Comando " + cmd.getName() + " foi desativado com sucesso.");
                    }
                    commands.saveConfig();
                }
            } catch (Exception ex) {
                error("Error ao gerar comando " + claz.getSimpleName());
                ex.printStackTrace();
            }

        }
        StorageAPI.updateReferences();
        getConfigs().saveConfig();
    }

    public ItemStack getSoup() {
        return soup;
    }

    public void onDisable() {
        save();
        super.onDisable();
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
