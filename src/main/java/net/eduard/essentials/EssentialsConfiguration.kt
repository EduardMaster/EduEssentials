package net.eduard.essentials

import net.eduard.api.lib.config.Config
import net.eduard.api.lib.config.ConfigSection

fun Config.clearDrops() {
    section("clear-drops") {
        add("enabled", true)
        add("timer-in-seconds", 120)
        add("broadcast-at", listOf(30, 10, 5, 4, 3, 2, 1))
        add("broadcast-text", listOf(
                "",
                "&cOs drops serão limpos em %time segundos.",
                ""))
        add("text", listOf(
                "",
                "&cOs drops foram Limpos com Sucesso.",
                ""))
    }
    section("disable") {
        add("hopper", true)
        add("enchanment-table", false)
        add("restone", false)
        add("anvil", false)
        add("slime-split", false)
        add("boat", true)
        add("cart", true)
        add("vehicle-inventory", false)
    }
}

fun Config.section(name: String, setup: ConfigSection.() -> Unit): ConfigSection {
    val section = getSection(name)
    section.setup()
    return section
}