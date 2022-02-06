package net.eduard.essentials.task

import net.eduard.api.lib.manager.TimeManager
import net.eduard.api.lib.modules.Extra
import net.eduard.api.lib.modules.Mine
import java.util.*

class RestartTask : TimeManager(60) {

    enum class RestartStage {
        WAITING, RESTARTING, RESTARTED
    }

    @Transient
    var stage = RestartStage.WAITING
    var selectedHour = 18
    var selectedMinute = 0
    var secondsIntoRestart = 30 * 60
    var messagePreRestart = "§cO Servidor irá reiniciar em %time , fique ligado para relogar no servidor em seguida."
    var messageRestart = "§cO Servidor foi reiniciado."

    @Transient
    var usedTime = 60 * 30

    fun restart() {
        stage = RestartStage.RESTARTED
        Mine.console("§cO Servidor Reiniciou.")
        for (player in Mine.getPlayers()) {
            player.sendMessage(messageRestart)
        }
    }


    override fun run() {
        if (stage == RestartStage.WAITING) {
            val day = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val minute = Calendar.getInstance().get(Calendar.MINUTE)
            if (day == selectedHour &&
                minute == selectedMinute) {
                stage = RestartStage.RESTARTING
                usedTime = secondsIntoRestart
                Mine.console("§cO Servidor foi colocado para Reiniciar.")
            }
        } else if (stage == RestartStage.RESTARTING) {
            usedTime -= 60
            if (usedTime <= 0) {
                restart()
            } else {
                for (player in Mine.getPlayers()) {
                    player.sendMessage(messagePreRestart
                        .replace("%time", Extra.formatTime(usedTime * 1000L))
                    )
                }
            }
        }
    }

}