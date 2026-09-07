package com.Sakamochanq.otukai.task.location

import com.Sakamochanq.otukai.task.Task
import org.bukkit.block.Biome
import kotlin.time.Duration

data class LocationTask(
    val biome: Biome,
    val displayName: String,
    override val timeLimit: Duration
) : Task {

    override val description: String
        get() = "${displayName}へ行こう！"

    override fun isCompleted(progress: Int): Boolean {
        return progress >= 1
    }
}