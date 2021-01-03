package net.eduard.essentials.task

import net.eduard.api.lib.manager.TimeManager
import net.eduard.essentials.EduEssentials

class AutoMessager : TimeManager(
    20*EduEssentials.getInstance()
    .configs.getLong("auto-message-per-seconds")) {

    var currentMessage = 0
    override fun run() {
        val msgs = EduEssentials.getInstance().manager.autoMessages

        val msg = msgs[currentMessage]
        msg.sendAll()
        currentMessage++
        if (currentMessage>= msgs.size){
            currentMessage=0
        }
    }


}