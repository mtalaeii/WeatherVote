package me.mahdi.weatherVote

import me.mahdi.weatherVote.commands.UserCommands
import me.mahdi.weatherVote.helpers.VoteHelper
import org.bukkit.plugin.java.JavaPlugin

class WeatherVote : JavaPlugin() {
    var helper: VoteHelper ?= null

    override fun onEnable() {
        logger.info("trying to enable WeatherVote plugin ...")
        saveDefaultConfig()
        getCommand("voting")!!.setExecutor(UserCommands(this))
    }

    override fun onDisable() {
        logger.info("shutting down WeatherVote plugin ...")
        helper?.cleanUp()
    }
}
