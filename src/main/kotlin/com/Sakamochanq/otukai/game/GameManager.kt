package com.Sakamochanq.otukai.game

import com.Sakamochanq.otukai.player.RunnerTeam
import com.Sakamochanq.otukai.task.TaskList
import com.Sakamochanq.otukai.ui.GameBossBar
import com.Sakamochanq.otukai.task.item.ItemTask
import com.Sakamochanq.otukai.task.kill.KillTask
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class GameManager {

    private var game: Game? = null

    private val runnerTeam = RunnerTeam()
    private val bossBar = GameBossBar()

    private var lastIntermissionSecond: Int? = null

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

        bossBar.setPlayers(players)
        bossBar.update(newGame)
        bossBar.show()

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

    fun addProgress(
        player: Player,
        amount: Int
    ) {
        val currentGame = game
            ?: return

        if (currentGame.state != GameState.PLAYING) {
            return
        }

        val session = currentGame.currentTask
            ?: return

        val task = session.task

        val completed = currentGame.addProgress(amount)

        val message = when (task) {
            is ItemTask -> task.pickupMessage(player.name, amount)
            else -> "${player.name}がアイテムをゲットした！"
        }

    Bukkit.getOnlinePlayers().forEach {
        it.sendMessage("§b$message")
    }

        if (completed) {
            announceTaskCompleted()
        }
    }

    fun addKillProgress(player: Player) {
        val currentGame = game
            ?: return

        if (currentGame.state != GameState.PLAYING) {
            return
        }

        val session = currentGame.currentTask
            ?: return

        val task = session.task as? KillTask
            ?: return

        val completed = currentGame.addProgress(1)

        Bukkit.getOnlinePlayers().forEach {
            it.sendMessage(
                "§b${task.killMessage(player.name)}"
            )
        }

        if (completed) {
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
                if (
                    previousState == GameState.INTERMISSION ||
                    previousTaskIndex != currentGame.currentTaskIndex
                ) {
                    lastIntermissionSecond = null

                    bossBar.update(currentGame)
                    bossBar.show()

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

    // おつかい開始のアナウンスを全員に送信する
    private fun announceTaskStarted(game: Game) {
        val session = game.currentTask
            ?: return

        Bukkit.getOnlinePlayers().forEach { player ->
            player.sendTitle("§e§lおつかい！", "§f${session.task.description}", 10, 50, 10)

            player.sendMessage("§e§lおつかい！ §f${session.task.description}")

            player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f)
        }
    }

    // おつかい完了
    private fun announceTaskCompleted() {
        Bukkit.getOnlinePlayers().forEach { player ->
            player.sendTitle("§a§lタスク達成！", "§f次のタスクへ！", 5, 30, 10)
            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
        }

        announceIntermission(5)
        lastIntermissionSecond = 5
    }

    // 次へ
    private fun announceIntermission(seconds: Int) {
        Bukkit.getOnlinePlayers().forEach { player ->
            player.sendTitle("§f次のタスクまで", "§e§l$seconds", 0, 20, 0)

            if (seconds <= 3) {
                player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HAT, 1.0f, 1.0f)
            }
        }
    }

    // ゲーム終了！
    private fun finishGame() {

        Bukkit.getOnlinePlayers().forEach { player ->
            player.sendMessage( "§a§l[おつかい] §fゲーム終了！おつかれさまでした！" )
            player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f )
        }

        bossBar.hide()
        bossBar.removeAll()

        runnerTeam.clear()

        game = null
        lastIntermissionSecond = null
    }
}