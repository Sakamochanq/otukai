package com.Sakamochanq.otukai

import com.Sakamochanq.otukai.command.OtukaiCommand
import com.Sakamochanq.otukai.game.GameManager
import org.bukkit.plugin.java.JavaPlugin

class OtukaiPlugin : JavaPlugin() {

    lateinit var gameManager: GameManager
        private set

    override fun onEnable() {
        gameManager = GameManager()

        getCommand("otukai")?.setExecutor(
            OtukaiCommand(this)
        )

        logger.info("Otukai plugin enabled!")
    }

    override fun onDisable() {
        if (::gameManager.isInitialized) {
            gameManager.stop()
        }

        logger.info("Otukai plugin disabled!")
    }
}