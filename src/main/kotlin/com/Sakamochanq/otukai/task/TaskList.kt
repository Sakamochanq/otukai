package com.Sakamochanq.otukai.task

import com.Sakamochanq.otukai.task.item.ItemTask
import com.Sakamochanq.otukai.task.kill.KillTask
import com.Sakamochanq.otukai.task.use.UseItemTask
import com.Sakamochanq.otukai.task.breakblock.BreakBlockTask
import com.Sakamochanq.otukai.task.craft.CraftTask
import com.Sakamochanq.otukai.task.fish.FishTask
import com.Sakamochanq.otukai.task.use.UseType
import org.bukkit.Material
import kotlin.time.Duration.Companion.minutes
import org.bukkit.entity.EntityType

object TaskList {

    val tasks: List<Task> = listOf(
        ItemTask(
            item = Material.WHEAT_SEEDS,
            displayName = "種",
            amount = 10,
            timeLimit = 5.minutes
        ),
        UseItemTask(
            item = Material.BUCKET,
            amount = 1,
            displayName = "バケツ",
            useType = UseType.BUCKET_FILL,
            timeLimit = 10.minutes,
        ),
        BreakBlockTask(
            block = Material.STONE,
            amount = 20,
            displayName = "石",
            timeLimit = 5.minutes
        ),
        CraftTask(
            item = Material.STICK,
            amount = 8,
            displayName = "棒",
            timeLimit = 5.minutes
        ),
        FishTask(
            amount = 3,
            displayName = "魚",
            timeLimit = 7.minutes
        ),
    )
}