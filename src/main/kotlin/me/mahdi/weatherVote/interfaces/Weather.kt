package me.mahdi.weatherVote.interfaces

import me.mahdi.weatherVote.helpers.WeatherType

interface Weather {
    fun rain()
    fun clear()
    fun storm()
    fun day()
    fun night()
    fun setWeather(weather: WeatherType)
}