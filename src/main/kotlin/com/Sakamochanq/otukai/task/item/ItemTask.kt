package com.Sakamochanq.otukai.task.item

import com.Sakamochanq.otukai.task.Task
import org.bukkit.Material
import kotlin.time.Duration

data class ItemTask(
    val item: Material,
    val amount: Int,
    val displayName: String,
    override val timeLimit: Duration
) : Task {

    override val description: String
        get() = "${displayName}を${amount}個手に入れろ！"

    fun pickupMessage(
        playerName: String,
        pickupAmount: Int
    ): String {
        return "${playerName}が${displayName}を${pickupAmount}個ゲットした！"
    }

    override fun isCompleted(progress: Int): Boolean {
        return progress >= amount
    }
}