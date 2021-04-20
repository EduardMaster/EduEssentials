
package net.eduard.essentials;

import java.util.*;

import net.eduard.api.lib.game.Title;
import net.eduard.api.lib.modules.Mine;
import net.eduard.api.lib.game.Jump;
import net.eduard.api.lib.game.SoundEffect;
import net.eduard.essentials.core.AutoMessage;
import net.eduard.essentials.core.EssentialsManager;
import net.eduard.essentials.core.LaunchPadManager;
import net.eduard.essentials.listener.*;
import net.eduard.essentials.task.AutoMessagerTask;
import net.eduard.essentials.listener.SpawnListener;
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


    private static EduEssentials instance;

    private EssentialsManager manager;

    public EssentialsManager getManager() {
        return manager;
    }

    public static EduEssentials getInstance() {
        return instance;
    }




    @Override
    public void onEnable() {
        instance = this;
        setFree(true);
        super.onEnable();
        new AntiDupe().register(this);
        new AntiMacro().register(this);
        new EssentialsListener().register(this);
        new DoubleJump().register(this);
        new SpawnListener().registerListener(this);
        new SoupSystem().register(this);
        new ShowDamage().register(this);
        new ComboCounter().register(this);
        new ClickCounter().register(this);
        new SlimeChunkDetector().register(this);
        new AutoMessagerTask().asyncTimer();
        LaunchPadManager.NO_FALL.register(this);

        reload();

    }

    public void autoMessages() {
        String key = "essentials";
        if (getStorage().contains(key)) {
            manager = getStorage().get(key, EssentialsManager.class);
        } else {
            manager = new EssentialsManager();
            AutoMessage message = new AutoMessage();
            manager.getAutoMessages().add(message);
            getStorage().set(key, manager);
            getStorage().saveConfig();
        }
    }

    public void spawn() {
        messages.add("Spawn teleport", "&6Voce foi teleportado para spawn!");
        messages.add("Spawn delay teleport", "&bVoce vai ser teleportando em %time segundos!");
        messages.add("Spawn not setted", "&cO spawn ainda nao foi setado!");
        messages.add("Spawn setted", "&bVoce setou o spawn!");
        messages.saveConfig();
        for (World world : Bukkit.getWorlds()) {
            String name = world.getName().toLowerCase();
            configs.add("teleport.respawn in world.$name", false,
                    "Teleportar quando voce resnacer no mundo $name");
        }
        configs.add("teleport.on respawn", true, "Teleportar quando renascer");
        configs.add("teleport.on join", true, "Teleportar quando entrar");
        configs.add("teleport.only on first join", false, "Teleportar apenas quando entrar pela primeira vez");
        configs.add("teleport.delay.enabled", false, "Atraso ao teleportar");
        configs.add("teleport.delay.seconds", 4, "Tempo de atraso ao teleportar");

        configs.add("teleport.title enabled", true, "Enviar um titulo ao ir pro spawn");
        configs.add("teleport.title", new Title("§6Inicio", "§eVoce foi para o Spawn!", 20, 20, 20),
                "Configure como vai ser o titulo ao ir para o spawn");
        configs.add("teleport.sound", SoundEffect.create("ENDERMAN_TELEPORT"), "Som ao teleportar");
        configs.add("teleport.sound on join", SoundEffect.create("ENDERMAN_TELEPORT"), "Som ao entrar ");
        configs.add("teleport.sound on respawn", SoundEffect.create("ENDERMAN_TELEPORT"), "Som ao renascer");

    }


    public void save() {
        getStorage().saveConfig();
    }

    @Override
    public void configDefault() {
        getConfigs().add("auto-message-per-seconds", 60);
        getConfigs().add("tab-header", Arrays.asList("", "" +
                        " §6Seja bem vindo a rede"
                , "  §b A rede que contem varios minigames"
                , ""));
        getConfigs().add("tab-footer", Arrays.asList("", "" +
                " §6Acesse §ewww.rededemine.com"));




        getConfigs().add("pads.sponge", new LaunchPadManager(-1, 19, 0,
                new Jump(SoundEffect.create("EXPLODE"), new Vector(0.0, 2.0, 0.0))));
        for (World world : Bukkit.getWorlds()) {
            String path = "launchpad." + world.getName();
            getConfigs().add(path, true);
            LaunchPadManager.WORLDS.put(world, getConfigs().getBoolean(path));
        }


        spawn();

        getConfigs().saveConfig();

        for (String key : getConfigs().getSection("pads").getKeys()) {
            LaunchPadManager launchpad = getConfigs().get("pads." + key, (LaunchPadManager.class));
            launchpad.register(this);
        }

    }

    public void reload() {
        getMessages().reloadConfig();
        getConfigs().reloadConfig();
        getStorage().reloadConfig();
        configDefault();
        autoMessages();
        int quantidadeDeComandosDesativados = 0;
        for (Class<?> claz : getClasses("net.eduard.essentials.command")) {
            try {
                if (!CommandManager.class.isAssignableFrom(claz)) continue;
                String name = claz.getSimpleName().toLowerCase().replace("command", "");
                CommandManager cmd = (CommandManager) claz.newInstance();
                for (CommandManager sub : cmd.getSubCommands().values()) {
                    StorageAPI.autoRegisterClass(sub.getClass());
                }
                String path = "commands." + name;
                Config commands = new Config(this, "commands/" + name + ".yml");
                getConfigs().add(path, false);
                if (commands.getKeys().isEmpty())
                    commands.set(cmd);
                cmd = (CommandManager) commands.get(claz);
                if (getConfigs().getBoolean(path)) {
                    log("Comando ativado §a" + cmd.getName());
                    cmd.registerCommand(this);
                } else {
                    quantidadeDeComandosDesativados++;

                }
                commands.saveConfig();

            } catch (Exception ex) {
                error("Error ao gerar comando " + claz.getSimpleName());
                ex.printStackTrace();
            }

        }
        log("Comandos desativados: §c" + quantidadeDeComandosDesativados );
        StorageAPI.updateReferences();
        getConfigs().saveConfig();
    }



    public void onDisable() {
        save();
        super.onDisable();
    }

}
