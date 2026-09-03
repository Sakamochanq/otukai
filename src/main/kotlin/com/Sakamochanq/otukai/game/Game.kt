package com.Sakamochanq.otukai.game

import com.Sakamochanq.otukai.task.Task
import com.Sakamochanq.otukai.task.TaskSession
import com.Sakamochanq.otukai.task.fish.FishTask
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class Game(
    val players: Set<org.bukkit.entity.Player>,
    tasks: List<Task>
) {
    // ゲーム開始時にタスクをランダムな順番にする
    private val shuffledTasks = tasks.shuffled()

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

    // 釣りタスクが解禁されているか
    var fishingUnlocked: Boolean = false
        private set

    fun start() {
        check(state == GameState.IDLE) {
            "Game is already started."
        }

        check(players.isNotEmpty()) {
            "Cannot start a game without players."
        }

        check(shuffledTasks.isNotEmpty()) {
            "Cannot start a game without tasks."
        }

        state = GameState.PLAYING
        currentTaskIndex = 0
        fishingUnlocked = false

        startCurrentTask()
    }

    fun unlockFishing() {
        fishingUnlocked = true
    }

    fun checkTaskCompleted(): Boolean {
        if (state != GameState.PLAYING) {
            return false
        }

        val session = currentTask ?: return false

        if (!session.isCompleted) {
            return false
        }

        startIntermission()
        return true
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

            if (currentTaskIndex >= shuffledTasks.size) {
                finish()
            } else {
                startCurrentTask()
                state = GameState.PLAYING
            }
        }
    }

    private fun startCurrentTask() {
        while (currentTaskIndex < shuffledTasks.size) {
            val task = shuffledTasks[currentTaskIndex]

            // 釣りタスクが未解禁ならスキップ
            if (task is FishTask && !fishingUnlocked) {
                currentTaskIndex++
                continue
            }

            currentTask = TaskSession(task)
            return
        }

        finish()
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