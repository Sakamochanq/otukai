package com.Sakamochanq.otukai.ui

import com.Sakamochanq.otukai.game.Game
import org.bukkit.Bukkit
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard

class GameScoreboard {

    private val scoreboard: Scoreboard =
        Bukkit.getScoreboardManager()?.mainScoreboard
            ?: error("Main scoreboard is not available.")

    companion object {
        private const val OBJECTIVE_NAME = "otukai_task"
        private const val OBJECTIVE_TITLE = "§e§lおつかい！"
    }

    private var objective: Objective? = null

    fun show(game: Game) {
        val currentObjective = getOrCreateObjective()

        currentObjective.displaySlot = DisplaySlot.SIDEBAR

        clear(currentObjective)

        val session = game.currentTask
            ?: return

        // プレイヤーごとの進捗
        game.players
            .sortedBy { it.name.lowercase() }
            .forEach { player ->
                currentObjective
                    .getScore(player.name)
                    .score = session.getProgress(player)
            }

        // 合計
        currentObjective
            .getScore("§e合計")
            .score = session.progress
    }

    fun hide() {
        objective?.displaySlot = null
    }

    fun remove() {
        objective?.let {
            scoreboard.resetScores(it.name)
        }

        val existing = scoreboard.getObjective(OBJECTIVE_NAME)

        if (existing != null) {
            existing.unregister()
        }

        objective = null
    }

    private fun getOrCreateObjective(): Objective {
        val existing = scoreboard.getObjective(OBJECTIVE_NAME)

        if (existing != null) {
            objective = existing
            return existing
        }

        val newObjective = scoreboard.registerNewObjective(
            OBJECTIVE_NAME,
            "dummy",
            OBJECTIVE_TITLE
        )

        objective = newObjective

        return newObjective
    }

    private fun clear(objective: Objective) {
        scoreboard.entries
            .toList()
            .forEach { entry ->
                scoreboard.resetScores(entry)
            }
    }
}