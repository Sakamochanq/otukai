package com.Sakamochanq.otukai.task.item

import com.Sakamochanq.otukai.task.Task
import org.bukkit.Material
import kotlin.time.Duration

data class ItemTask(
    val item: Material,
    val amount: Int,
    override val timeLimit: Duration
) : Task {

    override val description: String
        get() = "${item.name}を${amount}個手に入れろ！"

    override fun isCompleted(progress: Int): Boolean {
        return progress >= amount
    }
}