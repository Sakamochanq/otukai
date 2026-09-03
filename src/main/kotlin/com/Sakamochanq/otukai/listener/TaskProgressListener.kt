package com.Sakamochanq.otukai.listener

import com.Sakamochanq.otukai.OtukaiPlugin
import com.Sakamochanq.otukai.game.GameState
import com.Sakamochanq.otukai.task.item.ItemTask
import com.Sakamochanq.otukai.task.kill.KillTask
import com.Sakamochanq.otukai.task.use.UseItemTask
import com.Sakamochanq.otukai.task.breakblock.BreakBlockTask
import com.Sakamochanq.otukai.task.craft.CraftTask
import com.Sakamochanq.otukai.task.fish.FishTask
import com.Sakamochanq.otukai.task.use.UseType
import org.bukkit.event.player.PlayerBucketFillEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerShearEntityEvent
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerFishEvent
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.block.Block
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

    // 釣りタスクの解禁条件を確認
    private fun checkFishingUnlock(player: Player) {
        val game = plugin.gameManager.getGame() ?: return

        if (game.state != GameState.PLAYING) return
        if (!game.players.contains(player)) return

        if (game.fishingUnlocked) return

        val hasFishingRod = player.inventory.contents
            .filterNotNull()
            .any { it.type == Material.FISHING_ROD }

        val hasString = player.inventory.contents
            .filterNotNull()
            .any { it.type == Material.STRING }

        if (hasFishingRod || hasString) {
            game.unlockFishing()
        }
    }

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
                checkFishingUnlock(player)
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
                checkFishingUnlock(player)
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

    private fun isHoe(material: Material): Boolean {
        return material == Material.WOODEN_HOE ||
               material == Material.STONE_HOE ||
               material == Material.IRON_HOE ||
               material == Material.GOLDEN_HOE ||
               material == Material.DIAMOND_HOE ||
               material == Material.NETHERITE_HOE
    }

    @EventHandler
    fun onHoeTill(event: PlayerInteractEvent) {
        if (event.action != org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) {
            return
        }

        val player = event.player

        val game = plugin.gameManager.getGame() ?: return
        if (game.state != GameState.PLAYING) return
        if (!game.players.contains(player)) return

        val session = game.currentTask ?: return
        val task = session.task as? UseItemTask ?: return

        if (task.useType != UseType.HOE_TILL) return

        val item = player.inventory.itemInMainHand
        if (!isHoe(item.type)) return

        val block = event.clickedBlock ?: return

        if (!isTillableBlock(block)) return

        // 実際に耕地へ変化した後に判定する
        plugin.server.scheduler.runTask(
            plugin,
            Runnable {
                if (block.type == Material.FARMLAND) {
                    plugin.gameManager.addUseItemProgress(player)
                }
            }
        )
    }

    private fun isTillableBlock(block: Block): Boolean {
        return when (block.type) {
            Material.DIRT,
            Material.GRASS_BLOCK,
            Material.DIRT_PATH -> true

            else -> false
        }
    }

    // 羊の毛を刈る
    @EventHandler
    fun onShearEntity(event: PlayerShearEntityEvent) {
        if (event.isCancelled) return
    
        val player = event.player
    
        val game = plugin.gameManager.getGame() ?: return
        if (game.state != GameState.PLAYING) return
        if (!game.players.contains(player)) return
    
        val session = game.currentTask ?: return
        val task = session.task as? UseItemTask ?: return
    
        if (task.useType != UseType.SHEARS_CUT) return
    
        // 羊以外は対象外
        if (event.entity.type != EntityType.SHEEP) return
    
        // 実際に羊の毛刈りが成功した場合だけカウント
        plugin.gameManager.addUseItemProgress(player)
    }

    // ブロック破壊
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.isCancelled) return

        val player = event.player

        val game = plugin.gameManager.getGame() ?: return
        if (game.state != GameState.PLAYING) return
        if (!game.players.contains(player)) return

        val session = game.currentTask ?: return
        val task = session.task as? BreakBlockTask ?: return

        if (event.block.type != task.block) return

        plugin.gameManager.addBreakBlockProgress(player)
    }

    // 魚を釣る
    @EventHandler
    fun onFish(event: PlayerFishEvent) {
        // 実際に魚が釣れた場合だけ処理する
        if (event.state != PlayerFishEvent.State.CAUGHT_FISH) {
            return
        }
    
        val player = event.player
    
        val game = plugin.gameManager.getGame()
            ?: return
    
        if (game.state != GameState.PLAYING) return
        if (!game.players.contains(player)) return
    
        val session = game.currentTask
            ?: return
    
        val task = session.task as? FishTask
            ?: return
    
        plugin.gameManager.addFishProgress(player)
    }

    // アイテムクラフト
    @EventHandler
    fun onCraftItem(event: CraftItemEvent) {
        val player = event.whoClicked as? Player ?: return

        val game = plugin.gameManager.getGame() ?: return
        if (game.state != GameState.PLAYING) return
        if (!game.players.contains(player)) return

        val session = game.currentTask ?: return
        val task = session.task as? CraftTask ?: return

        val result = event.currentItem ?: return
        if (result.type != task.item) return

        plugin.gameManager.addCraftProgress(
            player = player,
            amount = result.amount
        )

        checkFishingUnlock(player)
    }
}