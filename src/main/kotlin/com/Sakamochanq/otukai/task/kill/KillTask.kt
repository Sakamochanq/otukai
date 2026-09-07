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

    val requiresNight: Boolean
        get() = entityType in NIGHT_ONLY_MOBS

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

    companion object {
        private val NIGHT_ONLY_MOBS = setOf(
            EntityType.BLAZE,
            EntityType.BOGGED,
            EntityType.BREEZE,
            EntityType.CAVE_SPIDER,
            EntityType.CREEPER,
            EntityType.DROWNED,
            EntityType.ELDER_GUARDIAN,
            EntityType.ENDER_DRAGON,
            EntityType.ENDERMAN,
            EntityType.ENDERMITE,
            EntityType.GHAST,
            EntityType.GUARDIAN,
            EntityType.HOGLIN,
            EntityType.HUSK,
            EntityType.MAGMA_CUBE,
            EntityType.PHANTOM,
            EntityType.PIGLIN,
            EntityType.PIGLIN_BRUTE,
            EntityType.PILLAGER,
            EntityType.RAVAGER,
            EntityType.SHULKER,
            EntityType.SILVERFISH,
            EntityType.SKELETON,
            EntityType.SKELETON_HORSE,
            EntityType.SLIME,
            EntityType.SPIDER,
            EntityType.STRAY,
            EntityType.VEX,
            EntityType.VINDICATOR,
            EntityType.WITCH,
            EntityType.WITHER,
            EntityType.WITHER_SKELETON,
            EntityType.ZOGLIN,
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.ZOMBIFIED_PIGLIN
        )
    }
}