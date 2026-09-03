package com.Sakamochanq.otukai.task

import com.Sakamochanq.otukai.task.item.ItemTask
import com.Sakamochanq.otukai.task.kill.KillTask
import com.Sakamochanq.otukai.task.use.UseItemTask
import com.Sakamochanq.otukai.task.breakblock.BreakBlockTask
import com.Sakamochanq.otukai.task.craft.CraftTask
import com.Sakamochanq.otukai.task.use.UseType
import org.bukkit.Material
import kotlin.time.Duration.Companion.minutes
import org.bukkit.entity.EntityType

object TaskList {

    val tasks: List<Task> = listOf(
        ItemTask(
            item = Material.APPLE,
            displayName = "りんご",
            amount = 1,
            timeLimit = 5.minutes
        ),
        ItemTask(
            item = Material.WHEAT_SEEDS,
            displayName = "種",
            amount = 10,
            timeLimit = 5.minutes
        ),
        ItemTask(
            item = Material.COBBLESTONE,
            displayName = "丸石",
            amount = 32,
            timeLimit = 5.minutes
        ),
        ItemTask(
            item = Material.OAK_LOG,
            displayName = "オークの原木",
            amount = 10,
            timeLimit = 5.minutes
        ),
        ItemTask(
            item = Material.DIRT,
            displayName = "土",
            amount = 30,
            timeLimit = 5.minutes
        ),
        ItemTask(
            item = Material.COAL,
            displayName = "石炭",
            amount = 10,
            timeLimit = 10.minutes
        ),
        ItemTask(
            item = Material.COPPER_INGOT,
            displayName = "銅のインゴット",
            amount = 5,
            timeLimit = 10.minutes
        ),
        ItemTask(
            item = Material.IRON_INGOT,
            displayName = "鉄のインゴット",
            amount = 5,
            timeLimit = 20.minutes
        ),
        KillTask(
            entityType = EntityType.SHEEP,
            amount = 1,
            displayName = "羊",
            timeLimit = 3.minutes
        ),
        KillTask(
            entityType = EntityType.PIG,
            amount = 1,
            displayName = "豚",
            timeLimit = 3.minutes
        ),
        KillTask(
            entityType = EntityType.COW,
            amount = 1,
            displayName = "牛",
            timeLimit = 3.minutes
        ),
        UseItemTask(
            item = Material.BUCKET,
            amount = 1,
            displayName = "バケツ",
            useType = UseType.BUCKET_FILL,
            timeLimit = 10.minutes,
        ),
        UseItemTask(
            item = Material.IRON_HOE,
            amount = 20,
            displayName = "クワ",
            useType = UseType.HOE_TILL,
            timeLimit = 5.minutes,
        ),
        UseItemTask(
            item = Material.SHEARS,
            amount = 3,
            displayName = "ハサミ",
            useType = UseType.SHEARS_CUT,
            timeLimit = 10.minutes
        ),
        BreakBlockTask(
            block = Material.STONE,
            amount = 20,
            displayName = "石",
            timeLimit = 5.minutes
        ),
        CraftTask(
            item = Material.CRAFTING_TABLE,
            amount = 1,
            displayName = "作業台",
            timeLimit = 5.minutes
        )
    )
}