package me.mahdi.weatherVote.helpers

import org.bukkit.ChatColor
import org.bukkit.Server
import org.bukkit.entity.Player

fun Player.sendColoredMessage(text: String) {
    val msg = "&2&lWeatherVote &r&b&l>> &r$text"
    this.sendMessage(ChatColor.translateAlternateColorCodes('&', msg))
}
fun Server.broadcastColoredMessage(text: String) {
    val msg = "&2&lWeatherVote &r&b&l>> &r$text"
    this.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg))
}