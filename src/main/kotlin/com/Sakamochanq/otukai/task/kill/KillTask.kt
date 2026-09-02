package com.Sakamochanq.otukai.task.kill

import com.Sakamochanq.otukai.task.Task
import org.bukkit.entity.EntityType
import kotlin.time.Duration

data class KillTask(
    val entityType: EntityType,
    val amount: Int,
    val displayName: String,
    override val timeLimit: Duration
) : Task {

    override val description: String
        get() = "${displayName}を${amount}体倒せ！"

    fun killMessage(
        playerName: String
    ): String {
        return "${playerName}が${displayName}を倒した！"
    }

    override fun isCompleted(progress: Int): Boolean {
        return progress >= amount
    }
}