package me.mahdi.weatherVote.interfaces

import me.mahdi.weatherVote.helpers.WeatherType
import org.bukkit.entity.Player

interface Weather {
    fun rain(player: Player)
    fun clear(player: Player)
    fun storm(player: Player)
    fun day(player: Player)
    fun night(player: Player)
    fun setWeather(weather: WeatherType,player: Player)
}