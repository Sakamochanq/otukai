package com.Sakamochanq.otukai.task

import kotlin.time.Duration

class TaskSession(
    val task: Task
) {
    // 現在の進捗
    var progress: Int = 0
        private set

    // 残り時間
    var remainingTime: Duration = task.timeLimit
        private set

    // タスクの完了判定
    val isCompleted: Boolean
        get() = task.isCompleted(progress)

    // 時間切れの判定
    val isTimedOut: Boolean
        get() = remainingTime == Duration.ZERO

    // 進捗の追加
    fun addProgress(amount: Int) {
        require(amount >= 0) {
            "Progress amount must not be negative."
        }

        progress += amount
    }

    // 経過時間の反映
    fun tick(elapsed: Duration) {
        require(!elapsed.isNegative()) {
            "Elapsed time must not be negative."
        }

        remainingTime = (remainingTime - elapsed)
            .coerceAtLeast(Duration.ZERO)
    }
}