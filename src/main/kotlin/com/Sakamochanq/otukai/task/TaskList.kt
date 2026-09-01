package com.Sakamochanq.otukai.task

import com.Sakamochanq.otukai.task.item.ItemTask
import org.bukkit.Material
import kotlin.time.Duration.Companion.minutes

object TaskList {

    val tasks: List<Task> = listOf(
        ItemTask(
            item = Material.APPLE,
            displayName = "りんご",
            amount = 1,
            timeLimit = 5.minutes
        ),
        ItemTask(
            item = Material.COBBLESTONE,
            displayName = "丸石",
            amount = 32,
            timeLimit = 3.minutes
        )
    )
}