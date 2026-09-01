package com.Sakamochanq.otukai.game

import com.Sakamochanq.otukai.task.Task
import com.Sakamochanq.otukai.task.TaskSession
import org.bukkit.entity.Player
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class Game(
    val players: Set<Player>,
    val tasks: List<Task>
) {

    companion object {
        private val INTERMISSION_DURATION = 5.seconds
    }

    var state: GameState = GameState.IDLE
        private set

    var currentTaskIndex: Int = 0
        private set

    var currentTask: TaskSession? = null
        private set

    var intermissionRemaining: Duration = Duration.ZERO
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

    fun addProgress(amount: Int): Boolean {
        if (state != GameState.PLAYING) {
            return false
        }

        val session = currentTask
            ?: return false

        session.addProgress(amount)

        if (session.isCompleted) {
            startIntermission()
            return true
        }

        return false
    }

    fun tick(elapsed: Duration) {
        when (state) {
            GameState.PLAYING -> tickPlaying(elapsed)
            GameState.INTERMISSION -> tickIntermission(elapsed)
            else -> Unit
        }
    }

    fun stop() {
        if (
            state != GameState.PLAYING &&
            state != GameState.INTERMISSION
        ) {
            return
        }

        finish()
    }

    private fun tickPlaying(elapsed: Duration) {
        val session = currentTask ?: return

        session.tick(elapsed)

        if (session.isTimedOut) {
            finish()
        }
    }

    private fun tickIntermission(elapsed: Duration) {
        intermissionRemaining =
            (intermissionRemaining - elapsed)
                .coerceAtLeast(Duration.ZERO)

        if (intermissionRemaining == Duration.ZERO) {
            currentTaskIndex++

            if (currentTaskIndex >= tasks.size) {
                finish()
            } else {
                startCurrentTask()
                state = GameState.PLAYING
            }
        }
    }

    private fun startCurrentTask() {
        val task = tasks.getOrNull(currentTaskIndex)

        if (task == null) {
            finish()
            return
        }

        currentTask = TaskSession(task)
    }

    private fun startIntermission() {
        state = GameState.INTERMISSION
        intermissionRemaining = INTERMISSION_DURATION
    }

    private fun finish() {
        state = GameState.FINISHED
        currentTask = null
        intermissionRemaining = Duration.ZERO
    }
}