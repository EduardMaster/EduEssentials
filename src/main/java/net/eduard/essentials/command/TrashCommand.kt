package net.eduard.essentials.command

import net.eduard.api.lib.manager.CommandManager
import net.eduard.api.lib.modules.Mine
import net.eduard.essentials.EduEssentials
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class TrashCommand : CommandManager("trash", "lixo", "lixeira", "junk") {
    var title = "Lixeira"
    var message = "§aAbrindo lixeira..."
    @Transient
    var lixeira : Inventory? = null
    get(){
        if (field==null){
            field = Mine.newInventory(title, 6*9)
        }
        return field
    }
    init{
        val time = 20*5L
        EduEssentials.getInstance().syncTimer(time,time){
            lixeira?.clear()
        }
    }

    override fun playerCommand(player: Player, args: Array<String>) {
       player.sendMessage(message)
       player.openInventory(lixeira!!)
    }
}