package me.mahdi.weatherVote.commands

import me.mahdi.weatherVote.WeatherVote
import me.mahdi.weatherVote.helpers.VoteHelper
import me.mahdi.weatherVote.helpers.WeatherType
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class UserCommands constructor(private val plugin : WeatherVote): CommandExecutor , TabCompleter{
    private var helper: VoteHelper? = null
    private val weathers = listOf("clear","storm","rain","day","night")

    override fun onCommand(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String?>
    ): Boolean {

        if(p0 is Player) {
            helper = helper ?: VoteHelper(plugin, p0)
            plugin.helper = helper
            try{
                val weather = WeatherType.valueOf(p3[0]!!.uppercase())
                helper!!.startVoting(weather)
                return true
            }catch (e : IllegalArgumentException){
                if(p3[0] == "yes"){
                    return helper!!.playerVote(p0,true)
                }else if(p3[0] == "no"){
                    return helper!!.playerVote(p0,false)
                }else{
                    plugin.logger.warning("Unknown key key type for weather ${p3[0]}")
                }
            }
        }
        return false
    }

    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String?>
    ): List<String?>? {
        // Tab completion logic
        if (p3.isEmpty()) {
            return weathers // If no arguments are provided, return all weather options
        }
        val input = p3[0]?.lowercase() ?: return null
        val commandList = if(helper?.getTask() !== null && !helper?.getTask()!!.isCancelled) listOf("yes","no") else weathers
        return commandList.filter { it.startsWith(input) }
    }

}