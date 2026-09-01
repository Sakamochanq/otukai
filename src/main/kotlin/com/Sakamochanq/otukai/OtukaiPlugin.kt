package com.Sakamochanq.otukai

import com.Sakamochanq.otukai.command.OtukaiCommand
import com.Sakamochanq.otukai.listener.TaskProgressListener
import com.Sakamochanq.otukai.game.GameManager
import com.Sakamochanq.otukai.game.GameScheduler
import org.bukkit.plugin.java.JavaPlugin

class OtukaiPlugin : JavaPlugin() {

    lateinit var gameManager: GameManager
        private set

    lateinit var gameScheduler: GameScheduler
        private set

    override fun onEnable() {
        gameManager = GameManager()

        gameScheduler = GameScheduler(
            this,
            gameManager
        )

        val otukaiCommand = OtukaiCommand(this)

        getCommand("otukai")?.setExecutor(otukaiCommand)
        getCommand("otukai")?.tabCompleter = otukaiCommand
        
        server.pluginManager.registerEvents(
            TaskProgressListener(this),
            this
        )
        gameScheduler.start()

        logger.info("Otukai plugin enabled!")
    }

    override fun onDisable() {
        if (::gameScheduler.isInitialized) {
            gameScheduler.stop()
        }

        if (::gameManager.isInitialized) {
            gameManager.stop()
        }

        logger.info("Otukai plugin disabled!")
    }
}