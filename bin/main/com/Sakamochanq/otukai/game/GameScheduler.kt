package com.Sakamochanq.otukai.game

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import kotlin.time.Duration.Companion.seconds

class GameScheduler(
    private val plugin: JavaPlugin,
    private val gameManager: GameManager
) {

    private var task: BukkitTask? = null

    fun start() {
        if (task != null) {
            return
        }

        task = plugin.server.scheduler.runTaskTimer(
            plugin,
            Runnable {
                gameManager.tick(1.seconds)
            },
            20L,
            20L
        )
    }

    fun stop() {
        task?.cancel()
        task = null
    }
}