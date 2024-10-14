package me.mahdi.weatherVote.helpers

import me.mahdi.weatherVote.WeatherVote
import me.mahdi.weatherVote.interfaces.Weather
import org.bukkit.entity.Player

class WeatherHelper(private val plugin : WeatherVote) : Weather
{

    override fun rain(player: Player) {
        if(!player.world.hasStorm()){
            player.world.setStorm(true)
            player.world.isThundering = false
        }else{
            plugin.logger.warning("The weather already is rain or s.thing went wrong")
        }
    }

    override fun clear(player: Player) {
        if(!player.world.isClearWeather){
            player.world.setStorm(false)
            player.world.isThundering = false
        }else{
            plugin.logger.warning("The weather already is clear or s.thing went wrong")
        }
    }

    override fun storm(player: Player) {
        if(!player.world.hasStorm() && !player.world.isThundering){
            player.world.setStorm(true)
            player.world.isThundering = true
        }else{
            plugin.logger.warning("The weather already is storm or s.thing went wrong")
        }
    }

    override fun day(player: Player) {
        if(player.world.hasStorm()) clear(player)
        player.world.time = 1000
    }

    override fun night(player: Player) {
        player.world.time = 13000
    }

    override fun setWeather(weather: WeatherType,player: Player) {
        when(weather) {
            WeatherType.RAIN -> rain(player)
            WeatherType.CLEAR -> clear(player)
            WeatherType.STORM -> storm(player)
            WeatherType.DAY -> day(player)
            else -> night(player)
        }
    }

}