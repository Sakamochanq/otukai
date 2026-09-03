package com.Sakamochanq.otukai.ui

import com.Sakamochanq.otukai.game.Game
import com.Sakamochanq.otukai.task.item.ItemTask
import com.Sakamochanq.otukai.task.kill.KillTask
import com.Sakamochanq.otukai.task.use.UseItemTask
import com.Sakamochanq.otukai.task.breakblock.BreakBlockTask
import com.Sakamochanq.otukai.task.craft.CraftTask
import com.Sakamochanq.otukai.task.fish.FishTask
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
        private const val OBJECTIVE_TITLE = "§eタスク進捗"
    }

    private var objective: Objective? = null

    // 現在表示しているエントリー
    private val entries = mutableSetOf<String>()

    fun show(game: Game) {
        val currentObjective = getOrCreateObjective()

        currentObjective.displaySlot = DisplaySlot.SIDEBAR

        clear()

        val session = game.currentTask
            ?: return

        val task = session.task

        // タスクのノルマ
        val targetAmount = when (task) {
            is ItemTask -> task.amount
            is KillTask -> task.amount
            is UseItemTask -> task.amount
            else -> 0
        }

        // タスクの単位
        val unit = when (task) {
            is ItemTask -> "個"
            is KillTask -> "匹"
            is UseItemTask -> "回"
            is BreakBlockTask -> "個"
            is CraftTask -> "個"
            is FishTask -> "匹"
            else -> ""
        }

        val displayEntries = mutableListOf<String>()

        // 空白
        displayEntries.add("§0 ")

        displayEntries.add("§e現在のタスク:")

        // タスク内容
        displayEntries.add(
            "§f${task.description}"
        )

        // 空白
        displayEntries.add("§0  ")

        // プレイヤーごとの進捗
        game.players
            .sortedBy { it.name.lowercase() }
            .forEach { player ->

                displayEntries.add(
                    createPlayerEntry(
                        playerName = player.name,
                        progress = session.getProgress(player),
                        unit = unit
                    )
                )
            }

        // 空白
        displayEntries.add("§0   ")

        // 合計
        displayEntries.add(
            createTotalEntry(
                progress = session.progress,
                target = targetAmount,
                unit = unit
            )
        )

        /*
         * スコアは表示順を決めるためだけに使用する。
         *
         * 実際の進捗は文字列の中に表示する。
         */
        val maxScore = displayEntries.size

        displayEntries.forEachIndexed { index, entry ->

            val score = maxScore - index

            currentObjective
                .getScore(entry)
                .score = score

            entries.add(entry)
        }
    }

    fun hide() {
        objective?.displaySlot = null
    }

    fun remove() {
        clear()

        val existing = scoreboard.getObjective(
            OBJECTIVE_NAME
        )

        if (existing != null) {
            existing.unregister()
        }

        objective = null
    }

    private fun getOrCreateObjective(): Objective {
        val existing = scoreboard.getObjective(
            OBJECTIVE_NAME
        )

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

    private fun createPlayerEntry(
        playerName: String,
        progress: Int,
        unit: String
    ): String {
        return "§f$playerName §7| §a${progress}${unit}"
    }

    private fun createTotalEntry(
        progress: Int,
        target: Int,
        unit: String
    ): String {
        return "§e合計 §7| §a${progress}/${target}${unit}"
    }

    private fun clear() {
        entries
            .toList()
            .forEach { entry ->
                scoreboard.resetScores(entry)
            }

        entries.clear()
    }
}