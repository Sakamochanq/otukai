package com.Sakamochanq.otukai.task.breakblock

import com.Sakamochanq.otukai.task.Task
import org.bukkit.Material
import kotlin.time.Duration

data class BreakBlockTask(
    val block: Material,
    val amount: Int,
    val displayName: String,
    override val timeLimit: Duration
) : Task {

    override val description: String
        get() = "${displayName}を${amount}個壊せ！"

    fun breakMessage(playerName: String): String {
        return "${playerName}が${displayName}を${amount}個壊した！"
    }

    override fun isCompleted(progress: Int): Boolean {
        return progress >= amount
    }
}