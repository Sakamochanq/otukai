package com.Sakamochanq.otukai.task

import com.Sakamochanq.otukai.task.item.ItemTask
import com.Sakamochanq.otukai.task.kill.KillTask
import com.Sakamochanq.otukai.task.use.UseItemTask
import com.Sakamochanq.otukai.task.use.UseType
import org.bukkit.Material
import org.bukkit.entity.EntityType
import kotlin.time.Duration.Companion.minutes

object TaskList {

    val tasks: List<Task> = listOf(

        // ========================================
        // アイテム入手
        // ========================================

        ItemTask(
            item = Material.APPLE,
            amount = 1,
            displayName = "りんご",
            timeLimit = 5.minutes
        ),

        ItemTask(
            item = Material.WHEAT_SEEDS,
            amount = 10,
            displayName = "小麦の種",
            timeLimit = 5.minutes
        ),

        ItemTask(
            item = Material.COBBLESTONE,
            amount = 32,
            displayName = "丸石",
            timeLimit = 3.minutes
        ),

        ItemTask(
            item = Material.OAK_LOG,
            amount = 16,
            displayName = "オークの原木",
            timeLimit = 3.minutes
        ),

        ItemTask(
            item = Material.COAL,
            amount = 16,
            displayName = "石炭",
            timeLimit = 4.minutes
        ),

        ItemTask(
            item = Material.IRON_INGOT,
            amount = 5,
            displayName = "鉄インゴット",
            timeLimit = 5.minutes
        ),

        ItemTask(
            item = Material.GOLD_INGOT,
            amount = 3,
            displayName = "金インゴット",
            timeLimit = 5.minutes
        ),

        ItemTask(
            item = Material.DIRT,
            amount = 32,
            displayName = "土",
            timeLimit = 3.minutes
        ),

        ItemTask(
            item = Material.SAND,
            amount = 16,
            displayName = "砂",
            timeLimit = 3.minutes
        ),

        ItemTask(
            item = Material.GRAVEL,
            amount = 16,
            displayName = "砂利",
            timeLimit = 3.minutes
        ),

        ItemTask(
            item = Material.WHEAT,
            amount = 10,
            displayName = "小麦",
            timeLimit = 5.minutes
        ),

        ItemTask(
            item = Material.CARROT,
            amount = 10,
            displayName = "ニンジン",
            timeLimit = 5.minutes
        ),

        ItemTask(
            item = Material.POTATO,
            amount = 10,
            displayName = "ジャガイモ",
            timeLimit = 5.minutes
        ),

        ItemTask(
            item = Material.BREAD,
            amount = 3,
            displayName = "パン",
            timeLimit = 5.minutes
        ),

        ItemTask(
            item = Material.STRING,
            amount = 5,
            displayName = "糸",
            timeLimit = 5.minutes
        ),

        ItemTask(
            item = Material.FEATHER,
            amount = 5,
            displayName = "羽根",
            timeLimit = 5.minutes
        ),

        ItemTask(
            item = Material.LEATHER,
            amount = 3,
            displayName = "革",
            timeLimit = 5.minutes
        ),


        // ========================================
        // モブ討伐
        // ========================================

        KillTask(
            entityType = EntityType.SHEEP,
            amount = 3,
            displayName = "羊",
            timeLimit = 3.minutes
        ),

        KillTask(
            entityType = EntityType.PIG,
            amount = 2,
            displayName = "豚",
            timeLimit = 3.minutes
        ),

        KillTask(
            entityType = EntityType.COW,
            amount = 2,
            displayName = "牛",
            timeLimit = 3.minutes
        ),

        KillTask(
            entityType = EntityType.CHICKEN,
            amount = 3,
            displayName = "ニワトリ",
            timeLimit = 3.minutes
        ),

        KillTask(
            entityType = EntityType.ZOMBIE,
            amount = 3,
            displayName = "ゾンビ",
            timeLimit = 3.minutes
        ),

        KillTask(
            entityType = EntityType.SKELETON,
            amount = 3,
            displayName = "スケルトン",
            timeLimit = 3.minutes
        ),

        KillTask(
            entityType = EntityType.SPIDER,
            amount = 2,
            displayName = "クモ",
            timeLimit = 3.minutes
        ),

        KillTask(
            entityType = EntityType.CREEPER,
            amount = 1,
            displayName = "クリーパー",
            timeLimit = 3.minutes
        ),


        // ========================================
        // アイテム使用
        // ========================================

        // バケツ
        // 水・マグマ・牛乳を入れる
        UseItemTask(
            item = Material.BUCKET,
            amount = 1,
            displayName = "バケツ",
            useType = UseType.BUCKET_FILL,
            timeLimit = 5.minutes
        ),

        // クワ
        // どの種類のクワでもOK
        UseItemTask(
            item = Material.IRON_HOE,
            amount = 20,
            displayName = "クワ",
            useType = UseType.HOE_TILL,
            timeLimit = 3.minutes
        ),

        // ハサミ
        // 羊の毛刈りのみ
        UseItemTask(
            item = Material.SHEARS,
            amount = 3,
            displayName = "ハサミ",
            useType = UseType.SHEARS_CUT,
            timeLimit = 2.minutes
        )
    )
}