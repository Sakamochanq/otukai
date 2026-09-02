package com.Sakamochanq.otukai.task

import org.bukkit.entity.Player
import kotlin.time.Duration

class TaskSession(
    val task: Task
) {
    // プレイヤーごとのゲーム開始時のアイテム所持数
    private val initialItemCounts: MutableMap<Player, Int> = mutableMapOf()

    // プレイヤーごとの現在の進捗
    private val playerProgress: MutableMap<Player, Int> = mutableMapOf()

    // 現在の合計進捗
    val progress: Int
        get() = playerProgress.values.sum()

    // 残り時間
    var remainingTime: Duration = task.timeLimit
        private set

    // タスクの完了判定
    val isCompleted: Boolean
        get() = task.isCompleted(progress)

    // 時間切れの判定
    val isTimedOut: Boolean
        get() = remainingTime == Duration.ZERO

    // ゲーム開始時の所持数を記録
    fun setInitialItemCount(
        player: Player,
        amount: Int
    ) {
        require(amount >= 0) {
            "Initial item count must not be negative."
        }

        initialItemCounts[player] = amount
    }

    // ゲーム開始時の所持数を取得
    fun getInitialItemCount(player: Player): Int {
        return initialItemCounts[player] ?: 0
    }

    // 現在のインベントリ数から進捗を更新
    fun updateItemProgress(
        player: Player,
        currentAmount: Int
    ) {
        require(currentAmount >= 0) {
            "Current item count must not be negative."
        }

        val initialAmount = getInitialItemCount(player)

        val progress = (currentAmount - initialAmount)
            .coerceAtLeast(0)

        playerProgress[player] = progress
    }

    // プレイヤーの進捗を追加
    // キル系タスクなどで使用
    fun addProgress(
        player: Player,
        amount: Int
    ) {
        require(amount >= 0) {
            "Progress amount must not be negative."
        }

        playerProgress[player] =
            (playerProgress[player] ?: 0) + amount
    }

    // プレイヤーの進捗を設定
    fun setProgress(
        player: Player,
        amount: Int
    ) {
        require(amount >= 0) {
            "Progress amount must not be negative."
        }

        playerProgress[player] = amount
    }

    // プレイヤーの進捗を取得
    fun getProgress(player: Player): Int {
        return playerProgress[player] ?: 0
    }

    // 全プレイヤーの進捗を取得
    fun getAllProgress(): Map<Player, Int> {
        return playerProgress.toMap()
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