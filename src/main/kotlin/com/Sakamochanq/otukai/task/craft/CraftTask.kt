package com.Sakamochanq.otukai.task.craft

import com.Sakamochanq.otukai.task.Task
import org.bukkit.Material
import kotlin.time.Duration

data class CraftTask(
    val item: Material,
    val amount: Int,
    val displayName: String,
    override val timeLimit: Duration
) : Task {

    override val description: String
        get() = "${displayName}を${amount}個クラフトしろ！"

    fun craftMessage(
        playerName: String,
        craftAmount: Int
    ): String {
        return "${playerName}が${displayName}を${craftAmount}個クラフトした！"
    }

    override fun isCompleted(progress: Int): Boolean {
        return progress >= amount
    }
}