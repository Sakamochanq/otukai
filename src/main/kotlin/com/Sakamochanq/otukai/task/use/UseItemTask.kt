package com.Sakamochanq.otukai.task.use

import com.Sakamochanq.otukai.task.Task
import org.bukkit.Material
import kotlin.time.Duration

enum class UseType {
    BUCKET_FILL,
    HOE_TILL,
    SHEARS_CUT
}

data class UseItemTask(
    val item: Material,
    val amount: Int,
    val displayName: String,
    val useType: UseType,
    override val timeLimit: Duration
) : Task {

    override val description: String
        get() = "${displayName}を${amount}回使え！"

    fun useMessage(
        playerName: String
    ): String {
        return "${playerName}が${displayName}を使った！"
    }

    override fun isCompleted(progress: Int): Boolean {
        return progress >= amount
    }
}