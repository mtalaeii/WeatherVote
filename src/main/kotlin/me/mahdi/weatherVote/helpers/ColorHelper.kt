package me.mahdi.weatherVote.helpers

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.Server
import org.bukkit.entity.Player

const val mainMsg = "&2&lWeatherVote &r&b&l>> &r%s"

fun Player.sendColoredMessage(text: String) {
    val msg = String.format(mainMsg,text)
    this.sendMessage(ChatColor.translateAlternateColorCodes('&', msg))
}
fun Server.broadcastColoredMessage(text: String) {
    val msg = String.format(mainMsg,text)
    this.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg))
}

fun Server.sendVotingMessage(weatherType: WeatherType,player: Player) {
    // Create the main message
    val message = TextComponent(ChatColor.translateAlternateColorCodes('&', String.format(
        "$mainMsg &r&8%s &r&9by player &r&l&4%s&r&9 ... [",
        "&9Voting started for weather",
        weatherType.name.lowercase(),
        player.name
    )))
    // for weather %s
    // Create the "Yes" part
    val yes = TextComponent(ChatColor.translateAlternateColorCodes('&', "&a&lYes"))
    yes.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/voting yes")

    // Create the "No" part
    val no = TextComponent(ChatColor.translateAlternateColorCodes('&', "&c&lNo"))
    no.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/voting no")

    // Append "Yes" and "No" to the main message
    message.addExtra(yes)
    message.addExtra(ChatColor.translateAlternateColorCodes('&', "&r | "))
    message.addExtra(no)
    message.addExtra(ChatColor.translateAlternateColorCodes('&', "&r&9 ]"))

    // Send the message to the player
    this.spigot().broadcast(message)
}