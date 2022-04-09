package net.eduard.essentials.listener

import net.eduard.api.lib.abstraction.Minecraft
import net.eduard.api.lib.bungee.BungeeAPI
import net.eduard.api.lib.event.PlayerTargetPlayerEvent
import net.eduard.api.lib.manager.EventsManager
import net.eduard.api.lib.modules.Mine
import net.eduard.api.lib.modules.MineReflect
import net.eduard.essentials.EduEssentials
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.*
import org.bukkit.event.server.ServerListPingEvent


class EssentialsListener : EventsManager() {

    val main get() = EduEssentials.getInstance()

    companion object {
        private val lastCommand: MutableMap<Player, Long> = HashMap()
    }

    @EventHandler
    fun onPingServer(e: ServerListPingEvent) {
        val amount = main.getInt("motd.custom-amount")
        if (amount > -1) {
            e.maxPlayers = amount
        }
        if (!main.getBoolean("motd.custom-text")) return
        val builder = StringBuilder()
        for (line in EduEssentials.getInstance().configs.getMessages("motd.text")) {
            builder.append(line + "\n")
        }
        e.motd = builder.toString()
    }


    @EventHandler
    fun cancelDrops(e: PlayerDropItemEvent) {
        if (EduEssentials.getInstance().configs.getBoolean("cancel.drops")) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun cancelDamageTypes(e: EntityDamageEvent) {
        if (e.entity is Player) {
            if (EduEssentials.getInstance().configs.getBoolean("cancel.damage." + e.cause)) {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onDeathFeatures(e: PlayerDeathEvent) {
        if (main.getBoolean("death.no-message")) {
            e.deathMessage = null
        }
    }

    @EventHandler
    fun foodDontChange(e: FoodLevelChangeEvent) {
        if (e.entity is Player) {
            val player = e.entity as Player
            if (EduEssentials.getInstance().configs.getBoolean("food-not-change")) {
                e.foodLevel = 20
                player.saturation = 20f
                player.exhaustion = 0f
            }
        }
    }


    @EventHandler
    fun onJoinFeatures(e: PlayerJoinEvent) {
        val player = e.player
        val header = StringBuilder()
        for (linha in EduEssentials.getInstance().configs.getMessages("tab-header")) {
            header.append(Mine.getReplacers(linha, e.player))
            header.append("\n")
        }
        val footer = StringBuilder()
        for (linha in EduEssentials.getInstance().configs.getMessages("tab-footer")) {
            footer.append(Mine.getReplacers(linha, e.player))
            footer.append("\n")
        }
        MineReflect.setTabList(player, header.toString(), footer.toString())

        EduEssentials.getInstance().syncDelay(20) {
            if (EduEssentials.getInstance().configs.getBoolean("force-gamemode.enabled")) {
                player.gameMode = GameMode.valueOf(EduEssentials.getInstance().configs.getString("force-gamemode.used"))
            }
        }


        if (main.getBoolean("join.first-join-message")) {
            if (!player.hasPlayedBefore()) {
                e.joinMessage = main.message("join.first-join-text")
                    .replace("%player", player.name)
            }
        }
        if (main.getBoolean("join.message")) {
            e.joinMessage = main.message("join.text")
                .replace("%player", player.name)
        }
        if (main.getBoolean("join.no-message")) {
            e.joinMessage = null
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    fun onTarget(e: PlayerTargetPlayerEvent) {
        if (!main.getBoolean("on-target.show-text")) return
        Minecraft.instance.sendActionBar(e.player, Mine.getReplacers(main.getString("on-target.text"), e.target))
    }

    @EventHandler
    fun onQuitFeatures(e: PlayerQuitEvent) {
        val player = e.player
        if (main.getBoolean("quit.custom-message"))
            e.quitMessage = main.configs.message("quit.custom-text")
                .replace("%player", player.name, false)
        if (main.getBoolean("quit.no-message")) {
            e.quitMessage = null
        }
    }



    @EventHandler
    fun signWithColors(e: SignChangeEvent) {
        val player = e.player
        if (player.hasPermission("sign.color")) {
            for (lineNumber in e.lines.indices) {
                e.setLine(lineNumber, ChatColor.translateAlternateColorCodes('&', e.lines[lineNumber]))
            }
        }
    }

    @EventHandler
    fun chatWithColors(e: AsyncPlayerChatEvent) {
        val player = e.player
        if (!player.hasPermission("chat.color")) return
        e.message = ChatColor.translateAlternateColorCodes('&', e.message)

    }


    @EventHandler
    fun commandDelay(e: PlayerCommandPreprocessEvent) {
        val player = e.player
        if (!EduEssentials.getInstance().configs.getBoolean("command-delay.enabled")) return
        if (player.hasPermission(EduEssentials.getInstance().configs.getString("command-delay.bypass-permission"))) return
        if (lastCommand.containsKey(player)) {
            val momentoQueUsouComandoAntes = lastCommand[player]
            val agora = System.currentTimeMillis()
            val delay = EduEssentials.getInstance().configs.getInt("command-delay.ticks").toLong()
            val test = agora > momentoQueUsouComandoAntes!! + delay
            if (!test) {
                player.sendMessage(EduEssentials.getInstance().message("command-cooldown"))
                e.isCancelled = true
            } else {
                lastCommand[player] = System.currentTimeMillis()
            }
        } else {
            lastCommand[player] = System.currentTimeMillis()
        }
    }

    /**
     *  Este evento não para Tab feitos em comandos apenas Chat<br>
     *  Feature quebrada na v1.8<br>
     *  Existe o evento mais na v1.9 TabCompleteEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    fun blockTabCommands(e: PlayerChatTabCompleteEvent) {
        val player = e.player as Player
        if (player.hasPermission(EduEssentials.getInstance().getString("blocked.bypass-permission"))) return
        for (blockedCmd in EduEssentials.getInstance().configs.getStringList("blocked.tab-commands")) {
            if (e.chatMessage.startsWith(blockedCmd, true)) {
                e.tabCompletions.clear()
                break
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    fun blockCommands(e: PlayerCommandPreprocessEvent) {
        val player = e.player
        if (player.hasPermission(EduEssentials.getInstance().getString("blocked.bypass-permission"))) return
        val text = e.message
        for (blockedCmd in EduEssentials.getInstance().configs.getStringList("blocked.run-commands")) {
            val commandName = if (" " in text) e.message.split(" ")[0] else e.message
            if (commandName.equals(blockedCmd, true)) {
                e.isCancelled = true
                player.sendMessage(Mine.MSG_NO_PERMISSION)
                break
            }
        }
    }


    init {
        for (serverName in EduEssentials.getInstance().configs.getStringList("servers")){
            val placehodler = "" + serverName.toLowerCase() + "_players_amount"
            Mine.addReplacer(placehodler) {
                val server = BungeeAPI.getServer(serverName)
                server.count
            }
            EduEssentials.getInstance().log("Registrando placeholder {$placehodler}")
        }
    }

}