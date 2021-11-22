package net.eduard.essentials.objects

import net.eduard.api.lib.modules.Mine
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent

class AutoMessage(
    var text: String = "§aText",
    var hover: MutableList<String> = mutableListOf("§bText"),
    var clickAction: ClickEvent.Action = ClickEvent.Action.OPEN_URL,
    var clickArgument: String = "https://eduard.com.br/"
) {

    fun create(): TextComponent {
        val builder = ComponentBuilder("")
        for (line in hover) {
            builder.append(line, ComponentBuilder.FormatRetention.FORMATTING)
        }
        val textComponent = TextComponent(text)
        val clickEvent = ClickEvent(clickAction, clickArgument)
        textComponent.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, builder.create())
        textComponent.clickEvent = clickEvent
        return textComponent

    }
    fun sendAll(){
        val textComponent = create()
        for (player in Mine.getPlayers()){
            player.spigot().sendMessage(textComponent)
        }
    }

}