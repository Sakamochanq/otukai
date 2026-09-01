package com.Sakamochanq.otukai.player

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Team

class RunnerTeam {

    companion object {
        private const val TEAM_NAME = "otukai_runner"
        private const val TEAM_PREFIX = "[runner] "
    }

    private val team: Team

    init {
        val scoreboard = Bukkit.getScoreboardManager()?.mainScoreboard
            ?: error("Main scoreboard is not available.")

        team = scoreboard.getTeam(TEAM_NAME)
            ?: scoreboard.registerNewTeam(TEAM_NAME)

        team.prefix = TEAM_PREFIX
        team.color = ChatColor.AQUA
    }

    // [runner]チームにプレイヤーを追加する
    fun addPlayer(player: Player) {
        team.addPlayer(player)
    }

    // [runner]チームからプレイヤーを削除する
    fun removePlayer(player: Player) {
        team.removePlayer(player)
    }

    // [runner]チームのクリア
    fun clear() {
        team.entries.toList().forEach {
            team.removeEntry(it)
        }
    }

    // [runner]チームに所属しているプレイヤーを取得する
    fun getPlayers(): Set<Player> {
        return team.entries
            .mapNotNull { Bukkit.getPlayerExact(it) }
            .toSet()
    }
}