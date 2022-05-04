package net.eduard.essentials.listener

import com.google.common.util.concurrent.Monitor
import net.eduard.api.lib.manager.EventsManager
import net.eduard.essentials.EduEssentials
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import java.io.File
import java.text.SimpleDateFormat
import java.util.concurrent.CompletableFuture

class PlayerActionsListener : EventsManager() {

    val folder = File( "playerslogs/")
    val FORMAT_DATETIME = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    private val mutex: Monitor = Monitor()
    init{
        folder.mkdirs()
    }
    fun newAction(player : Player , cancelled : Boolean , action : String){
        CompletableFuture.runAsync {
            try {
                mutex.enter()
                val file = File(folder, "${player.name.toLowerCase()}.txt")
                file.appendText("\n${FORMAT_DATETIME.format(System.currentTimeMillis())} Cancelled:$cancelled ->$action")
            }catch (ex : Exception){
                ex.printStackTrace()
            }
            finally {
                mutex.leave()
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onChat(e: AsyncPlayerChatEvent) {
        newAction(e.player, e.isCancelled , e.message)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onCommand(e: PlayerCommandPreprocessEvent) {
        newAction(e.player, e.isCancelled , e.message)
    }

}