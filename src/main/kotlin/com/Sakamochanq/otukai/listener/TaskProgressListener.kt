package com.Sakamochanq.otukai.listener

import com.Sakamochanq.otukai.OtukaiPlugin
import com.Sakamochanq.otukai.game.GameState
import com.Sakamochanq.otukai.task.item.ItemTask
import com.Sakamochanq.otukai.task.kill.KillTask
import com.Sakamochanq.otukai.task.use.UseItemTask
import com.Sakamochanq.otukai.task.use.UseType
import org.bukkit.event.player.PlayerBucketFillEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.Sound
import org.bukkit.Material


class TaskProgressListener(
    private val plugin: OtukaiPlugin
) : Listener {

    // インベントリ内の対象アイテム数を数える
    private fun countItem(
        player: Player,
        itemTask: ItemTask
    ): Int {
        return player.inventory.contents
            .filterNotNull()
            .filter { it.type == itemTask.item }
            .sumOf { it.amount }
    }

    // アイテムタスクの進捗を現在のインベントリから更新
    private fun updateItemProgress(player: Player) {
        val game = plugin.gameManager.getGame() ?: return
        if (game.state != GameState.PLAYING) return
        if (!game.players.contains(player)) return
        
        val session = game.currentTask ?: return
        val task = session.task as? ItemTask ?: return
        
        val beforeProgress = session.getProgress(player)
        
        val currentAmount = countItem(player, task)
        session.updateItemProgress(
            player = player,
            currentAmount = currentAmount
        )
        
        val afterProgress = session.getProgress(player)
        
        // タスクの進捗として新しく記録された場合だけ効果音を鳴らす
        if (afterProgress > beforeProgress) {
            player.playSound(
                player.location,
                Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                0.6f,
                1.2f
            )
        }
    }

    // アイテム拾う
    @EventHandler
    fun onItemPickup(
        event: org.bukkit.event.entity.EntityPickupItemEvent
    ) {
        val player = event.entity as? Player
            ?: return

        // 実際に拾った後のインベントリ状態を取得するため、
        // イベント処理が終わった次のtickで確認する
        plugin.server.scheduler.runTask(
            plugin,
            Runnable {
                updateItemProgress(player)
            }
        )
    }

    // アイテム捨てる
    @EventHandler
    fun onItemDrop(
        event: PlayerDropItemEvent
    ) {
        val player = event.player

        plugin.server.scheduler.runTask(
            plugin,
            Runnable {
                updateItemProgress(player)
            }
        )
    }

    // インベントリクリック
    @EventHandler
    fun onInventoryClick(
        event: InventoryClickEvent
    ) {
        val player = event.whoClicked as? Player
            ?: return

        plugin.server.scheduler.runTask(
            plugin,
            Runnable {
                updateItemProgress(player)
            }
        )
    }

    // インベントリドラッグ
    @EventHandler
    fun onInventoryDrag(
        event: InventoryDragEvent
    ) {
        val player = event.whoClicked as? Player
            ?: return

        plugin.server.scheduler.runTask(
            plugin,
            Runnable {
                updateItemProgress(player)
            }
        )
    }

    // アイテム消費
    @EventHandler
    fun onItemConsume(
        event: PlayerItemConsumeEvent
    ) {
        val player = event.player

        plugin.server.scheduler.runTask(
            plugin,
            Runnable {
                updateItemProgress(player)
            }
        )
    }

    // エンティティキル
    @EventHandler
    fun onEntityDeath(
        event: EntityDeathEvent
    ) {
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


    @EventHandler
    fun onBucketFill(event: PlayerBucketFillEvent) {
        if (event.isCancelled) return

        val player = event.player

        val game = plugin.gameManager.getGame() ?: return
        if (game.state != GameState.PLAYING) return
        if (!game.players.contains(player)) return

        val session = game.currentTask ?: return
        val task = session.task as? UseItemTask ?: return

        if (task.useType != UseType.BUCKET_FILL) return

        // 実際に使用したバケツを確認する
        if (event.bucket != task.item) return

        plugin.gameManager.addUseItemProgress(player)
    }

    // 牛乳バケツ
    @EventHandler
    fun onMilkBucketUse(event: PlayerInteractEntityEvent) {
        val player = event.player

        val game = plugin.gameManager.getGame() ?: return
        if (game.state != GameState.PLAYING) return
        if (!game.players.contains(player)) return

        val session = game.currentTask ?: return
        val task = session.task as? UseItemTask ?: return

        if (task.useType != UseType.BUCKET_FILL) return

        val item = player.inventory.itemInMainHand ?: return
        if (item.type != Material.BUCKET) return

        if (event.rightClicked.type != EntityType.COW) return

        plugin.gameManager.addUseItemProgress(player)
    }
}