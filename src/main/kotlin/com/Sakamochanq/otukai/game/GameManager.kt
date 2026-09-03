package com.Sakamochanq.otukai.game

import com.Sakamochanq.otukai.player.RunnerTeam
import com.Sakamochanq.otukai.task.TaskList
import com.Sakamochanq.otukai.task.item.ItemTask
import com.Sakamochanq.otukai.task.kill.KillTask
import com.Sakamochanq.otukai.task.use.UseItemTask
import com.Sakamochanq.otukai.task.breakblock.BreakBlockTask
import com.Sakamochanq.otukai.task.fish.FishTask
import com.Sakamochanq.otukai.task.craft.CraftTask
import com.Sakamochanq.otukai.ui.GameBossBar
import com.Sakamochanq.otukai.ui.GameScoreboard
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import kotlin.time.Duration

class GameManager {

    private var game: Game? = null

    private val runnerTeam = RunnerTeam()
    private val bossBar = GameBossBar()
    private val scoreboard = GameScoreboard()

    private var lastIntermissionSecond: Int? = null
    
    // クラフト前の対象アイテム数
    private val craftInitialCounts: MutableMap<Player, Int> = mutableMapOf()

    val isRunning: Boolean
        get() = game?.state == GameState.PLAYING ||
                game?.state == GameState.INTERMISSION

    fun getGame(): Game? {
        return game
    }

    fun start(): Boolean {
        if (isRunning) {
            return false
        }

        runnerTeam.clear()

        val players = Bukkit.getOnlinePlayers().toSet()

        if (players.isEmpty()) {
            return false
        }

        players.forEach {
            runnerTeam.addPlayer(it)
        }

        val newGame = Game(
            players = players,
            tasks = TaskList.tasks
        )

        newGame.start()

        game = newGame
        lastIntermissionSecond = null

        // 最初のタスクの初期インベントリを記録
        initializeItemTaskProgress(newGame)

        bossBar.setPlayers(players)
        bossBar.update(newGame)
        bossBar.show()

        scoreboard.show(newGame)

        announceTaskStarted(newGame)

        return true
    }

    fun stop(): Boolean {
        if (!isRunning) {
            return false
        }

        game?.stop()

        finishGame()

        return true
    }

    // アイテムタスク開始時の基準値を記録する
    private fun initializeItemTaskProgress(game: Game) {
        val session = game.currentTask
            ?: return

        val task = session.task as? ItemTask
            ?: return

        game.players.forEach { player ->
            val currentAmount = countItem(
                player = player,
                task = task
            )

            session.setInitialItemCount(
                player = player,
                amount = currentAmount
            )

            session.updateItemProgress(
                player = player,
                currentAmount = currentAmount
            )
        }
    }

    // インベントリ内のクラフト対象アイテム数を数える
    fun countCraftItem(
        player: Player,
        task: CraftTask
    ): Int {
        return player.inventory.contents
            .filterNotNull()
            .filter { it.type == task.item }
            .sumOf { it.amount }
    }

    // クラフト前のアイテム数を記録する
    fun setCraftInitialCount(
        player: Player,
        task: CraftTask
    ) {
        craftInitialCounts[player] = countCraftItem(
            player = player,
            task = task
        )
    }

    // クラフトによって増えたアイテム数を取得する
    // fun getCraftedAmount(
    //     player: Player,
    //     task: CraftTask
    // ): Int {
    //     val initialAmount = craftInitialCounts[player]
    //         ?: return 0
    // 
    //     val currentAmount = countCraftItem(
    //         player = player,
    //         task = task
    //     )
    // 
    //     return (currentAmount - initialAmount).coerceAtLeast(0)
    // }

    // インベントリ内の対象アイテム数を数える
    private fun countItem(
        player: Player,
        task: ItemTask
    ): Int {
        return player.inventory.contents
            .filterNotNull()
            .filter { it.type == task.item }
            .sumOf { it.amount }
    }

    // アイテムタスクの現在の進捗を更新する
    private fun updateItemTaskProgress(game: Game) {
        if (game.state != GameState.PLAYING) {
            return
        }

        val session = game.currentTask
            ?: return

        val task = session.task as? ItemTask
            ?: return

        val completedBefore = session.isCompleted

        game.players.forEach { player ->
            val currentAmount = countItem(
                player = player,
                task = task
            )

            // session.updateItemProgress(
            //     player = player,
            //     currentAmount = currentAmount
            // )
        }

        // Scoreboardを更新
        scoreboard.show(game)

        // タスク達成
        if (session.isCompleted && game.checkTaskCompleted()) {
            announceTaskCompleted()
        }
    }

    // キル系タスクの進捗を追加
    fun addKillProgress(player: Player) {
        val currentGame = game
            ?: return

        if (currentGame.state != GameState.PLAYING) {
            return
        }

        val session = currentGame.currentTask
            ?: return

        session.task as? KillTask
            ?: return

        val completedBefore = session.isCompleted

        session.addProgress(
            player = player,
            amount = 1
        )

        // Scoreboardを更新
        scoreboard.show(currentGame)

        // タスク達成
        if (session.isCompleted && currentGame.checkTaskCompleted()) {
            announceTaskCompleted()
        }
    }

    fun tick(elapsed: Duration) {
        val currentGame = game
            ?: return

        val previousState = currentGame.state
        val previousTaskIndex = currentGame.currentTaskIndex

        currentGame.tick(elapsed)

        when (currentGame.state) {
            GameState.PLAYING -> {

                // アイテムタスクの進捗を更新
                updateItemTaskProgress(currentGame)

                // インターミッション終了後、
                // 新しいタスクが開始された
                if (
                    previousState == GameState.INTERMISSION ||
                    previousTaskIndex != currentGame.currentTaskIndex
                ) {
                    lastIntermissionSecond = null

                    // 新しいタスクの初期インベントリを記録
                    initializeItemTaskProgress(currentGame)

                    bossBar.update(currentGame)
                    bossBar.show()

                    scoreboard.show(currentGame)

                    announceTaskStarted(currentGame)
                } else {
                    bossBar.update(currentGame)
                }
            }

            GameState.INTERMISSION -> {
                bossBar.hide()

                val seconds = currentGame.intermissionRemaining
                    .inWholeSeconds
                    .toInt()

                if (
                    seconds != lastIntermissionSecond &&
                    seconds in 1..5
                ) {
                    announceIntermission(seconds)
                    lastIntermissionSecond = seconds
                }
            }

            GameState.FINISHED -> {
                finishGame()
            }

            GameState.IDLE -> Unit
        }
    }

    // アイテム使用系タスクの進捗を追加
    fun addUseItemProgress(player: Player) {
        val currentGame = game ?: return
        if (currentGame.state != GameState.PLAYING) return
        if (!currentGame.players.contains(player)) return
        
        val session = currentGame.currentTask ?: return
        session.task as? UseItemTask ?: return
        
        session.addProgress(
            player = player,
            amount = 1
        )
        
        // 進捗が実際に増えたときだけ効果音を鳴らす
        player.playSound(
            player.location,
            Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
            0.6f,
            1.2f
        )
        
        scoreboard.show(currentGame)
        
        if (session.isCompleted && currentGame.checkTaskCompleted()) {
            announceTaskCompleted()
        }
    }

    // おつかい開始のアナウンス
    private fun announceTaskStarted(game: Game) {
        val session = game.currentTask
            ?: return

        Bukkit.getOnlinePlayers().forEach { player ->
            player.sendTitle(
                "§e§lおつかい！",
                "§f${session.task.description}",
                10,
                50,
                10
            )

            player.sendMessage(
                "§e§lおつかい！ §f${session.task.description}"
            )

            player.playSound(
                player.location,
                Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                1.0f,
                1.0f
            )
        }
    }

    // おつかい完了
    private fun announceTaskCompleted() {
        Bukkit.getOnlinePlayers().forEach { player ->
            player.sendTitle(
                "§a§lおつかい達成！",
                "§f次のおつかいへ！",
                5,
                30,
                10
            )

            player.playSound(
                player.location,
                Sound.ENTITY_PLAYER_LEVELUP,
                1.0f,
                1.0f
            )
        }

        announceIntermission(5)
        lastIntermissionSecond = 5
    }

    // 次のタスクまでのカウントダウン
    private fun announceIntermission(seconds: Int) {
        Bukkit.getOnlinePlayers().forEach { player ->
            player.sendTitle(
                "§f次のおつかいまで",
                "§e§l$seconds",
                0,
                20,
                0
            )

            if (seconds <= 3) {
                player.playSound(
                    player.location,
                    Sound.BLOCK_NOTE_BLOCK_HAT,
                    1.0f,
                    1.0f
                )
            }
        }
    }

    // ゲーム終了
    private fun finishGame() {
        Bukkit.getOnlinePlayers().forEach { player ->
            player.sendMessage(
                "§a§l[おつかい] §fゲーム終了！"
            )

            player.playSound(
                player.location,
                Sound.BLOCK_ANVIL_LAND,
                1.0f,
                1.0f
            )
        }

        bossBar.hide()
        bossBar.removeAll()

        scoreboard.hide()
        scoreboard.remove()

        runnerTeam.clear()

        game = null
        lastIntermissionSecond = null
    }

    // ブロック破壊系タスクの進捗を追加
    fun addBreakBlockProgress(player: Player) {
        val currentGame = game ?: return
        if (currentGame.state != GameState.PLAYING) return
        if (!currentGame.players.contains(player)) return

        val session = currentGame.currentTask ?: return
        session.task as? BreakBlockTask ?: return

        session.addProgress(
            player = player,
            amount = 1
        )

        player.playSound(
            player.location,
            Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
            0.6f,
            1.2f
        )

        scoreboard.show(currentGame)

        if (session.isCompleted && currentGame.checkTaskCompleted()) {
            announceTaskCompleted()
        }
    }

    // クラフト系タスクの進捗を追加
    fun addCraftProgress(
        player: Player,
        amount: Int
    ) {
        val currentGame = game ?: return
        if (currentGame.state != GameState.PLAYING) return
        if (!currentGame.players.contains(player)) return

        val session = currentGame.currentTask ?: return
        session.task as? CraftTask ?: return

        session.addProgress(
            player = player,
            amount = amount
        )

        player.playSound(
            player.location,
            Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
            0.6f,
            1.2f
        )

        scoreboard.show(currentGame)

        if (session.isCompleted && currentGame.checkTaskCompleted()) {
            announceTaskCompleted()
        }
    }

    // 釣り系タスクの進捗を追加
    fun addFishProgress(player: Player) {
        val currentGame = game ?: return

        if (currentGame.state != GameState.PLAYING) return
        if (!currentGame.players.contains(player)) return

        val session = currentGame.currentTask ?: return
        session.task as? FishTask ?: return

        session.addProgress(
            player = player,
            amount = 1
        )

        player.playSound(
            player.location,
            Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
            0.6f,
            1.2f
        )

        scoreboard.show(currentGame)

        if (session.isCompleted && currentGame.checkTaskCompleted()) {
            announceTaskCompleted()
        }
    }
}