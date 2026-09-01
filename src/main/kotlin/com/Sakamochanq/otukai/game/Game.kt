package com.Sakamochanq.otukai.game

import com.Sakamochanq.otukai.task.Task
import com.Sakamochanq.otukai.task.TaskSession
import org.bukkit.entity.Player
import kotlin.time.Duration

class Game(
    val players: Set<Player>,
    val tasks: List<Task>
) {

    var state: GameState = GameState.IDLE
        private set

    var currentTaskIndex: Int = 0
        private set

    var currentTask: TaskSession? = null
        private set

    fun start() {
        check(state == GameState.IDLE) {
            "Game is already started."
        }

        check(players.isNotEmpty()) {
            "Cannot start a game without players."
        }

        check(tasks.isNotEmpty()) {
            "Cannot start a game without tasks."
        }

        state = GameState.PLAYING
        currentTaskIndex = 0

        startCurrentTask()
    }

    fun addProgress(amount: Int) {
        check(state == GameState.PLAYING) {
            "Game is not playing."
        }

        val session = currentTask
            ?: error("Current task session does not exist.")

        session.addProgress(amount)

        if (session.isCompleted) {
            nextTask()
        }
    }

    fun tick(elapsed: Duration) {
        if (state != GameState.PLAYING) {
            return
        }

        val session = currentTask ?: return

        session.tick(elapsed)

        if (session.isTimedOut) {
            finish()
        }
    }

    fun stop() {
        if (state != GameState.PLAYING) {
            return
        }

        finish()
    }

    private fun startCurrentTask() {
        val task = tasks.getOrNull(currentTaskIndex)

        if (task == null) {
            finish()
            return
        }

        currentTask = TaskSession(task)
    }

    private fun nextTask() {
        currentTaskIndex++
        startCurrentTask()
    }

    private fun finish() {
        state = GameState.FINISHED
        currentTask = null
    }
}