package com.Sakamochanq.otukai.ui

import com.Sakamochanq.otukai.game.Game
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import kotlin.math.max
import kotlin.math.min

class GameBossBar {

    private val bossBar: BossBar = Bukkit.createBossBar(
        "",
        BarColor.GREEN,
        BarStyle.SOLID
    )

    fun addPlayer(player: Player) {
        bossBar.addPlayer(player)
    }

    fun removePlayer(player: Player) {
        bossBar.removePlayer(player)
    }

    fun update(game: Game) {
        val session = game.currentTask
            ?: return

        val task = session.task

        val remaining = session.remainingTime
        val total = task.timeLimit

        val progress = if (total.isPositive()) {
            (remaining / total).toDouble()
        } else {
            0.0
        }

        bossBar.setProgress(
            min(1.0, max(0.0, progress))
        )

        bossBar.setTitle(
            buildString {
                append(task.description)
                append("  ")
                append(formatTime(remaining))
            }
        )
    }

    fun show() {
        bossBar.setVisible(true)
    }

    fun hide() {
        bossBar.setVisible(false)
    }

    fun removeAll() {
        bossBar.removeAll()
    }

    private fun formatTime(duration: kotlin.time.Duration): String {
        val totalSeconds = duration.inWholeSeconds

        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60

        return "%02d:%02d".format(minutes, seconds)
    }
}