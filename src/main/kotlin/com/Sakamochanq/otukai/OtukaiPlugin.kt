package com.sakamochanq.otukai

import org.bukkit.plugin.java.JavaPlugin

class OtukaiPlugin : JavaPlugin() {

    override fun onEnable() {
        logger.info("Otukai plugin enabled!")
    }

    override fun onDisable() {
        logger.info("Otukai plugin disabled!")
    }
}