package me.mahdi.weatherVote.helpers

import me.mahdi.weatherVote.WeatherVote
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class VoteHelper(private val plugin: WeatherVote) {
    private val weatherHelper: WeatherHelper by lazy { WeatherHelper(plugin) }

    @Volatile
    private var task: BukkitTask? = null
    private val totalVoteTime = if (plugin.config.getInt("wv.vote-time") > 0) plugin.config.getInt("wv.vote-time") else 60
    private var bossBar: BossBar = Bukkit.createBossBar("Voting Progress", BarColor.GREEN, BarStyle.SEGMENTED_20)

    private var playerVotedCount = 0
    private val playersVoted = mutableMapOf<String, Boolean>()
    private val playersStartVote = mutableMapOf<String, Instant>()

    fun getTask(): BukkitTask? {
        return task
    }

    @Synchronized
    private fun updatePlayerVote(playerId: String, vote: Boolean) {
        playersVoted[playerId] = vote
    }

    @Synchronized
    private fun getPlayerVoteCount(): Pair<Int, Int> {
        val yesVotes = playersVoted.values.count { it }
        val noVotes = playersVoted.values.count { !it }
        return Pair(yesVotes, noVotes)
    }

    fun startVoting(weather: WeatherType,player: Player) {
        if (!isValid(player)) return
        var remainingTime = totalVoteTime
        val players = plugin.server.onlinePlayers
        bossBar.style = BarStyle.SEGMENTED_10
        // Add players to the Boss Bar
        players.forEach {
            bossBar.addPlayer(it)
        }
        plugin.server.sendVotingMessage(weather,player)
        task = object : BukkitRunnable() {
            override fun run() {
                val progress = ((remainingTime.toDouble() / totalVoteTime) * 100).toInt()
                bossBar.progress = progress / 100.0
                bossBar.setTitle("${ChatColor.GREEN} Voting Progress ends in $remainingTime seconds.$playerVotedCount players voted so far.")
                if (remainingTime <= 10) {
                    bossBar.style = BarStyle.SOLID
                    bossBar.color = BarColor.RED
                    bossBar.setTitle("${ChatColor.RED} Voting Progress ends in $remainingTime seconds.$playerVotedCount players voted so far.")
                    plugin.server.broadcastColoredMessage("&cVoting ends in &r&c&l$remainingTime &r&cseconds!")
                }
                remainingTime--
                if (remainingTime < 0) {
                    this.cancel()
                    onCloseTask(weather,player)
                }
            }
        }.runTaskTimer(plugin, 0L, 20L) // 20 ticks = 1 second
        playerVote(player,true)

    }

    private fun onCloseTask(weather: WeatherType,player: Player) {
        cleanUp()
        plugin.server.broadcastColoredMessage("&4Voting has ended !")
        val (yesVotedCount, noVotedCount) = getPlayerVoteCount()
        if (!(yesVotedCount == 0 && noVotedCount == 0) && yesVotedCount >= noVotedCount) {
            plugin.server.broadcastColoredMessage("&aSuccessful round for weather &r&1&l${weather.name.lowercase()} &r&ain overworld!")
            weatherHelper.setWeather(weather,player)
            playersVoted.clear()
            playerVotedCount = 0
            return
        }
        plugin.server.broadcastColoredMessage("&cUnsuccessful round for weather &r&4&l${weather.name.lowercase()} &r&cin overworld!")
        playerVotedCount = 0
        playersVoted.clear()
    }

    private fun isValid(player: Player): Boolean {
        if (task !== null && !task!!.isCancelled) {
            player.sendColoredMessage("&eVote is already in progress !!")
            return false
        }
        if (player.world.name.equals("world_nether", ignoreCase = true) || player.world.name.equals("world_the_end", ignoreCase = true)) {
            player.sendColoredMessage("&4You can't vote in &r&4&nnether&r&4 or &r&4&nend&r&4!!")
            return false
        }
        else if(plugin.server.onlinePlayers.size <= 1){
            player.sendColoredMessage("&4There is no enough players in the server for start voting!!")
            return false
        }
        else if (playersStartVote.keys.contains(player.uniqueId.toString())) {
            val playerTime = playersStartVote[player.uniqueId.toString()]
            val duration = Duration.between(playerTime, Instant.now())
            val timeWhenAllowed = playerTime?.plus(Duration.ofMinutes(5))
            if (duration.toMinutes() >= 5) {
                playersStartVote[player.uniqueId.toString()] = Instant.now()
                return true
            } else {
                val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
                    .withZone(ZoneId.systemDefault())
                player.sendColoredMessage("&4you can't vote until ${formatter.format(timeWhenAllowed)}")
                return false
            }
        }
        playersStartVote[player.uniqueId.toString()] = Instant.now()
        return true
    }

    fun cleanUp() {
        bossBar.removeAll()
        task?.cancel()
    }

    fun playerVote(
        player: Player,
        vote: Boolean
    ): Boolean {
        if (task !== null && !task!!.isCancelled) {
            if (playersVoted.contains(player.uniqueId.toString())) {
                val playerVote = when (playersVoted[player.uniqueId.toString()]) {
                    true -> "yes"
                    else -> "no"
                }
                player.sendColoredMessage("&4you have been voted $playerVote before!!")
            } else {
                val playerVote = when (vote) {
                    true -> "yes"
                    else -> "no"
                }
                updatePlayerVote(player.uniqueId.toString(), vote)
                player.sendColoredMessage("&2you have been voted $playerVote successfully!")
                playerVotedCount++
            }
            return true
        }
        return false
    }
}
