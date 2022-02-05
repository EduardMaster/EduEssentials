package net.eduard.essentials.task

import net.eduard.api.lib.manager.TimeManager
import net.eduard.api.lib.modules.Extra
import net.eduard.api.lib.modules.Mine
import org.bukkit.Bukkit
import java.util.*

class RestartTask : TimeManager(60) {

    enum class RestartFakeStage {
        WAITING, PRE_RESTART, AFTER_RESTART, COMPLETE_RESTART
    }

    @Transient
    var stage = RestartFakeStage.WAITING
    var selectedHour = 18
    var selectedMinute = 0
    var secondsIntoRestart = 30 * 60
    var secondsIntoFinishRestart = 60 * 2
    var messageWhenRestart = "§aO Servidor vai reiniciar aguarde alguns minutos ele irá reabrir."
    var messagePreRestart = "§cO Servidor irá reiniciar em %time , fique ligado para relogar no servidor em seguida."

    @Transient
    var usedTime = 60 * 30

    fun restart() {
        for (player in Mine.getPlayers()) {
            player.kickPlayer(
                messageWhenRestart.replace("/n", "\n")
                    .replace("\\n", "\n")
            )
        }
        Bukkit.setWhitelist(true)
        stage = RestartFakeStage.AFTER_RESTART
        usedTime = secondsIntoFinishRestart
        Mine.console("§cO Servidor aplicou o reiniciar FAKE.")
    }

    fun restartComplete() {
        stage = RestartFakeStage.COMPLETE_RESTART
        Bukkit.setWhitelist(false)
        Mine.console("§aO Servidor saiu do reiniciar FAKE.")

    }

    override fun run() {
       val day =  Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val minute =  Calendar.getInstance().get(Calendar.MINUTE)
        //Mine.console("§cHour of day: $day")
       // Mine.console("§cMinute of Day: $minute")
        if (stage == RestartFakeStage.PRE_RESTART) {
            usedTime -= 60
            if (usedTime <= 0) {
                restart()
            } else {
                for (player in Mine.getPlayers()) {
                    player.sendMessage(
                        messagePreRestart
                            .replace("/n", "\n")
                            .replace("\\n", "\n")
                            .replace("%time", Extra.formatTime(usedTime * 1000L))
                    )
                }
            }
        } else if (stage == RestartFakeStage.WAITING) {
            if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == selectedHour &&
                Calendar.getInstance().get(Calendar.MINUTE) == selectedMinute) {
                stage = RestartFakeStage.PRE_RESTART
                usedTime = secondsIntoRestart
                Mine.console("§cO Servidor foi colocado para reiniciar FAKE.")
            }
        } else if (stage == RestartFakeStage.AFTER_RESTART) {
            usedTime -= 60
            if (usedTime <= 0) {
                restartComplete()
            }
        }
    }

}