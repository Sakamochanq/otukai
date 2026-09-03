package com.Sakamochanq.otukai.task

import org.bukkit.entity.Player
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class TaskSession(
    val task: Task,
    playerCount: Int
) {
    init {
        require(playerCount >= 1) {
            "Player count must be at least 1."
        }
    }

    // 参加人数
    val playerCount: Int = playerCount

    // 人数に応じて調整された目標数
    val targetAmount: Int
        get() {
            val baseAmount = getBaseAmount()
            return baseAmount * playerCount
        }

    // 人数に応じて調整された制限時間
    val timeLimit: Duration
        get() {
            val baseTime = task.timeLimit
            val additionalTime = 15.seconds * (playerCount - 1)
            val maxTime = baseTime * 2

            return (baseTime + additionalTime)
                .coerceAtMost(maxTime)
        }

    // プレイヤーごとのゲーム開始時のアイテム所持数
    private val initialItemCounts: MutableMap<Player, Int> = mutableMapOf()

    // プレイヤーごとの現在の進捗
    private val playerProgress: MutableMap<Player, Int> = mutableMapOf()

    // 現在の合計進捗
    val progress: Int
        get() = playerProgress.values.sum()

    // 残り時間
    var remainingTime: Duration = timeLimit
        private set

    // タスクの完了判定
    val isCompleted: Boolean
        get() = progress >= targetAmount

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

        // 新しいタスク開始時なので、進捗を0にする
        playerProgress[player] = 0
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

    // Taskの基本目標数を取得
    private fun getBaseAmount(): Int {
        return when (task) {
            is com.Sakamochanq.otukai.task.item.ItemTask ->
                task.amount

            is com.Sakamochanq.otukai.task.kill.KillTask ->
                task.amount

            is com.Sakamochanq.otukai.task.use.UseItemTask ->
                task.amount

            is com.Sakamochanq.otukai.task.breakblock.BreakBlockTask ->
                task.amount

            is com.Sakamochanq.otukai.task.craft.CraftTask ->
                task.amount

            is com.Sakamochanq.otukai.task.fish.FishTask ->
                task.amount

            else -> 0
        }
    }
}