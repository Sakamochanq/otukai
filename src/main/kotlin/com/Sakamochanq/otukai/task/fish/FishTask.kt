package com.Sakamochanq.otukai.task.fish

import com.Sakamochanq.otukai.task.Task
import kotlin.time.Duration

data class FishTask(
    val amount: Int,
    val displayName: String,
    override val timeLimit: Duration
) : Task {

    override val description: String
        get() = "${displayName}を${amount}匹釣れ！"

    fun fishMessage(
        playerName: String,
        fishAmount: Int
    ): String {
        return "${playerName}が${displayName}を${fishAmount}匹釣った！"
    }

    override fun isCompleted(progress: Int): Boolean {
        return progress >= amount
    }
}