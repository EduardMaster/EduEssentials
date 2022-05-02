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

    val folder = File(EduEssentials.getInstance().dataFolder , "players/")
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

    /*
    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        e.player.actions.saveIfPossible()
        cache.remove(e.player)
    }

    class PlayerActions(val playerName: String) {
        var content = mutableListOf<String>()
        fun add(action: String, cancelled: Boolean) {
            content.add("[${System.currentTimeMillis().formatTime()}] Cancelled:$cancelled -> $action")
        }

        fun deleteFile() {
            val file = File("playerlogs/${playerName.toLowerCase()}.txt")

            if (file.exists())
                file.delete()
        }

        fun canSave() = content.isNotEmpty()

        fun save() {
            val file = File("playerlogs/${playerName.toLowerCase()}.txt")
            file.parentFile.mkdirs()
            Extra.writeLines(file, content)
        }

        fun saveIfPossible() {
            if (canSave())save()
        }
    }

    companion object {
        val cache = mutableMapOf<Player, PlayerActions>()
        fun save() {
            cache.forEach {
                if (it.value.canSave())
                    it.value.save()
                it.value.deleteFile()
            }
        }
        fun reload(){
            val pasta = File("playerlogs/")
            pasta.mkdirs()
            for (file in pasta.listFiles()!!){
                if (!file.name.endsWith(".txt"))continue
                val lines = Extra.readLines(file);
                if (lines.isEmpty()){
                    file.delete()
                }
            }
        }

        val Player.actions
            @Synchronized
            get() = cache.getOrPut(this) {
                val name = name.toLowerCase()
                val file = File("playerlogs/$name.txt")
                val actions = PlayerActions(this.name)
                if (file.exists()) {
                    actions.content.addAll(Extra.readLines(file))
                }
                actions
            }
    }


     */
}