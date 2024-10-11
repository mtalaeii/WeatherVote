package me.mahdi.weatherVote.helpers

import me.mahdi.weatherVote.WeatherVote
import me.mahdi.weatherVote.interfaces.Weather
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

class WeatherHelper(private val plugin : WeatherVote, private var player: Player) : Weather
{

    override fun rain() {
        if(!player.world.hasStorm()){
            player.world.setStorm(true)
            player.world.isThundering = false
        }else{
            plugin.logger.warning("The weather already is rain or s.thing went wrong")
        }
    }

    override fun clear() {
        if(!player.world.isClearWeather){
            player.world.setStorm(false)
            player.world.isThundering = false
        }else{
            plugin.logger.warning("The weather already is clear or s.thing went wrong")
        }
    }

    override fun storm() {
        if(!player.world.hasStorm() && !player.world.isThundering){
            player.world.setStorm(true)
            player.world.isThundering = true
        }else{
            plugin.logger.warning("The weather already is storm or s.thing went wrong")
        }
    }

    override fun day() {
        player.world.time = 1000
    }

    override fun night() {
        player.world.time = 13000
    }

    override fun setWeather(weather: WeatherType) {
        when(weather) {
            WeatherType.RAIN -> rain()
            WeatherType.CLEAR -> clear()
            WeatherType.STORM -> storm()
            WeatherType.DAY -> day()
            else -> night()
        }
    }

}