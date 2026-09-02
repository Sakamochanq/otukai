package com.Sakamochanq.otukai.listener

import com.Sakamochanq.otukai.OtukaiPlugin
import com.Sakamochanq.otukai.game.GameState
import com.Sakamochanq.otukai.task.item.ItemTask
import com.Sakamochanq.otukai.task.kill.KillTask
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPickupItemEvent

class TaskProgressListener(
    private val plugin: OtukaiPlugin
) : Listener {

    @EventHandler
    fun onItemPickup(event: EntityPickupItemEvent) {
        val player = event.entity as? org.bukkit.entity.Player
            ?: return

        val game = plugin.gameManager.getGame()
            ?: return

        if (game.state != GameState.PLAYING) {
            return
        }

        if (!game.players.contains(player)) {
            return
        }

        val session = game.currentTask
            ?: return

        val task = session.task as? ItemTask
            ?: return

        val itemStack = event.item.itemStack

        if (itemStack.type != task.item) {
            return
        }

        plugin.gameManager.addProgress(
            player = player,
            amount = itemStack.amount
        )
    }
    
    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        val player = event.entity.killer
            ?: return

        val game = plugin.gameManager.getGame()
            ?: return

        if (game.state != GameState.PLAYING) {
            return
        }

        if (!game.players.contains(player)) {
            return
        }

        val session = game.currentTask
            ?: return

        val task = session.task as? KillTask
            ?: return

        if (event.entity.type != task.entityType) {
            return
        }

        plugin.gameManager.addKillProgress(player)
}
}