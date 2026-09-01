package com.Sakamochanq.otukai.game

import com.Sakamochanq.otukai.task.Task
import com.Sakamochanq.otukai.task.TaskSession
import kotlin.time.Duration

class Game(
    val tasks: List<Task>
) {

    var state: GameState = GameState.IDLE
        private set

    var currentTaskIndex: Int = 0
        private set

    var currentTask: TaskSession? = null
        private set

    // ゲーム開始
    fun start() {
        check(state == GameState.IDLE) {
            "Game is already started."
        }

        check(tasks.isNotEmpty()) {
            "Cannot start a game without tasks."
        }

        state = GameState.PLAYING
        currentTaskIndex = 0

        startCurrentTask()
    }

    // 進捗の追加
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

    // 経過時間の反映
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

    // 強制終了
    fun stop() {
        if (state != GameState.PLAYING) {
            return
        }

        finish()
    }

    // 現在のタスクを開始する
    private fun startCurrentTask() {
        val task = tasks.getOrNull(currentTaskIndex)

        if (task == null) {
            finish()
            return
        }

        currentTask = TaskSession(task)
    }

    // 次のタスクへ
    private fun nextTask() {
        currentTaskIndex++
        startCurrentTask()
    }

    // ゲーム終了
    private fun finish() {
        state = GameState.FINISHED
        currentTask = null
    }
}