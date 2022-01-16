package net.eduard.essentials.command.admin

import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.game.Jump
import net.eduard.api.lib.game.SoundEffect
import net.eduard.api.lib.menu.Slot
import net.eduard.api.lib.modules.Mine
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.kotlin.format
import net.eduard.api.lib.modules.VaultAPI
import net.eduard.essentials.EduEssentials
import org.bukkit.*
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class AdminCommand : CommandManager("admin", "adm") {


    companion object {
        var prisioners = mutableSetOf<Player>()
        var admins = mutableSetOf<Player>()
        lateinit var instance: AdminCommand
    }

    init {
        instance = this
        registerListener(EduEssentials.getInstance())
    }

    fun removePrison(player: Player) {
        prisioners.remove(player)
        val loc = player.location
        player.sendMessage(messagePrisionFree)
        player.playSound(player.location, Sound.LEVEL_UP, 2f, 1f)
        loc.clone().add(0.0, -1.0, 0.0).block.type = Material.AIR
        loc.clone().add(0.0, 2.0, 0.0).block.type = Material.AIR
        loc.clone().add(0.0, 0.0, 1.0).block.type = Material.AIR
        loc.clone().add(1.0, 0.0, 0.0).block.type = Material.AIR
        loc.clone().add(0.0, 0.0, -1.0).block.type = Material.AIR
        loc.clone().add(-1.0, 0.0, 0.0).block.type = Material.AIR
    }

    fun prison(target: Player) {
        prisioners.add(target)
        var loc = target.location
        loc = loc.add(0.0, 10.0, 0.0)
        target.playSound(target.location, Sound.WITHER_SPAWN, 2f, 1f)
        loc.clone().add(0.0, 0.0, 0.0).block.type = Material.BEDROCK
        loc.clone().add(0.0, 3.0, 0.0).block.type = Material.BEDROCK
        loc.clone().add(0.0, 1.0, -1.0).block.type = Material.BEDROCK
        loc.clone().add(-1.0, 1.0, 0.0).block.type = Material.BEDROCK
        loc.clone().add(1.0, 1.0, 0.0).block.type = Material.BEDROCK
        loc.clone().add(0.0, 1.0, 1.0).block.type = Material.BEDROCK
        val targetLocation = loc.clone().add(0.0, 1.0, 0.0)
        targetLocation.x = targetLocation.blockX + 0.5
        targetLocation.z = targetLocation.blockZ + 0.5
        target.teleport(targetLocation)
        target.sendMessage(messagePrisioned)

    }

    fun setCageBlockType(blockType: Material, location: Location) {
        location.clone().add(0.0, 0.0, 0.0).block.type = blockType
        location.clone().add(0.0, 3.0, 0.0).block.type = blockType
        location.clone().add(0.0, 1.0, -1.0).block.type = blockType
        location.clone().add(-1.0, 1.0, 0.0).block.type = blockType
        location.clone().add(1.0, 1.0, 0.0).block.type = blockType
        location.clone().add(0.0, 1.0, 1.0).block.type = blockType
    }


    var jumpEffect = Jump(SoundEffect.create("BAT_LOOP"), Vector(0, 3, 0))
    var messagePrisionFree = "§aVoce foi liberto da Prisão!"
    var messagePrisioned = "§cVoce foi Aprisionado!"
    var messageOn = "§bVoce entrou no Modo Admin!"
    var messageOff = "§bVoce saiu do Modo Admin!"
    var messageChangeFastOn = "§6Troca rapida ativada!"
    var messageChangeFastOff = "§6Troca rapida desativada!"
    var messagePrisionPlayerFree = "§aVocê libertou o jogador %player."
    var messagePrisionPlayer = "§cVocê prendeu o jogador %player."
    var testAutoSoup = Slot(Mine.newItem(Material.ENDER_PEARL, "§eTestar Auto-SoupSystem"), 3)
    var testFreeFireChangeFast = Slot(Mine.newItem(Material.MAGMA_CREAM, "§eAtivar Troca Rapida"), 2)
    var testPrison = Slot(Mine.newItem(Material.MAGMA_CREAM, "§eAprisionar Jogador"), 1)
    var testNoFall = Slot(Mine.newItem(Material.FEATHER, "§eTestar No-Fall"), 4)
    var testInfo = Slot(Mine.newItem(Material.BLAZE_ROD, "§eVer Informações"), 5)
    var testAntKB =
        Slot(ItemBuilder(Material.STICK).name("§eTestar Knockback").addEnchant(Enchantment.KNOCKBACK, 10), 6)

    fun joinAdminMode(player: Player) {
        Mine.saveItems(player)
        Mine.hide(player)
        admins.add(player)
        val inventory = player.inventory
        Mine.clearInventory(player)
        testAutoSoup.give(inventory)
        testFreeFireChangeFast.give(inventory)
        testNoFall.give(inventory)
        testInfo.give(inventory)
        testAntKB.give(inventory)
        testPrison.give(inventory)
        player.gameMode = GameMode.CREATIVE
        // show player to another admins
        for (playerLoop in Mine.getPlayers()) {
            if (playerLoop.hasPermission(permission)) {
                playerLoop.showPlayer(player)
            }
        }
        player.sendMessage(messageOn)
    }

    fun leaveAdminMode(player: Player) {
        Mine.getItems(player)
        Mine.show(player)
        player.gameMode = GameMode.SURVIVAL
        admins.remove(player)
        player.sendMessage(messageOff)
    }

    @EventHandler
    fun testNoFall(e: PlayerInteractEntityEvent) {
        val player = e.player
        if (testNoFall.item != player.itemInHand) return
        if (e.rightClicked !is Player) return
        val target = e.rightClicked as Player
        if (!admins.contains(player)) return
        jumpEffect.create(target)


    }

    @EventHandler
    fun testFF_TrocaRapida(e: PlayerInteractEvent) {
        val player = e.player
        if (testFreeFireChangeFast.item != player.itemInHand) return
        if (!admins.contains(player)) return
        Mine.show(player)
        Mine.setInvunerable(player, 1)
        player.sendMessage(messageChangeFastOn)
        object : BukkitRunnable() {
            override fun run() {
                Mine.hide(player)
                for (playerLoop in Mine.getPlayers()) {
                    if (playerLoop.hasPermission(permission)) {
                        playerLoop.showPlayer(player)
                    }
                }
                player.sendMessage(messageChangeFastOff)
            }
        }.runTaskLater(plugin, 20)

    }

    @EventHandler
    fun testAutoSoup(e: PlayerInteractEntityEvent) {
        val player = e.player
        if (testAutoSoup.item != player.itemInHand) return
        if (e.rightClicked !is Player) return
        val target = e.rightClicked as Player
        if (admins.contains(player)) {
            player.openInventory(target.inventory)
        }

    }

    @EventHandler
    fun testInfo(e: PlayerInteractEntityEvent) {
        val player = e.player
        if (testInfo.item != player.itemInHand) return
        if (e.rightClicked !is Player) return
        val target = e.rightClicked as Player
        if (!admins.contains(player)) return
        player.sendMessage("")
        player.sendMessage("§bInformações do §3" + target.name)
        player.sendMessage("§fModo de Jogo: §a" + target.gameMode)
        player.sendMessage("§fAbates: §a" + target.getStatistic(Statistic.PLAYER_KILLS))
        player.sendMessage("§fMortes: §a" + target.getStatistic(Statistic.DEATHS))
        player.sendMessage("§fIP: §a" + Mine.getIp(player))
        if (VaultAPI.hasVault() && VaultAPI.hasEconomy()) {
            player.sendMessage("§fDinheiro: §a$§7" + VaultAPI.getEconomy().getBalance(player).format())
        }
        player.sendMessage("")

    }

    @EventHandler
    fun testPrison(e: PlayerInteractEntityEvent) {
        val player = e.player
        if (testPrison.item != player.itemInHand) return
        if (e.rightClicked !is Player) return
        val target = e.rightClicked as Player
        if (!admins.contains(player)) return
        if (!prisioners.contains(target)) {
            player.sendMessage(
                messagePrisionPlayer
                    .replace("%player", target.name)
            )
            prison(target)
        } else {
            player.sendMessage(
                messagePrisionPlayerFree
                    .replace("%player", target.name)
            )
            removePrison(target)
        }

    }

    override fun playerCommand(player: Player, args: Array<String>) {
        if (admins.contains(player)) {
            leaveAdminMode(player)
        } else {
            joinAdminMode(player)
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun adminsCanAttackAnyone(e: EntityDamageByEntityEvent) {
        if (e.damager !is Player) return
        val player = e.damager as Player
        if (admins.contains(player)) {
            e.isCancelled = false
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    fun adminsCantBeSeeByNormalPlayers(e: PlayerJoinEvent) {
        if (e.player.hasPermission(permission))return
        for (adm in admins){
            e.player.hidePlayer(adm)
        }
    }
    fun isPrisioner(target: Player): Boolean {
        return prisioners.contains(target)
    }

}