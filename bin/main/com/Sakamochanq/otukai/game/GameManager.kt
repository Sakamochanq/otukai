package com.Sakamochanq.otukai.game

import com.Sakamochanq.otukai.task.TaskList
import com.Sakamochanq.otukai.player.RunnerTeam
import com.Sakamochanq.otukai.ui.GameBossBar
import kotlin.time.Duration
import org.bukkit.Bukkit

class GameManager {

    private var game: Game? = null
    private val runnerTeam = RunnerTeam()
    private val bossBar = GameBossBar()

    // ゲームが進行中かどうか
    val isRunning: Boolean
        get() = game?.state == GameState.PLAYING


    // ゲームを取得する
    fun getGame(): Game? {
        return game
    }


    // ゲーム開始
    fun start(): Boolean {
        if (isRunning) {
            return false
        }

        runnerTeam.clear()

        val players = Bukkit.getOnlinePlayers().toSet()

        players.forEach {
            runnerTeam.addPlayer(it)
        }

        val newGame = Game(
            players = players,
            tasks = TaskList.tasks
        )

        newGame.start()

        game = newGame

        bossBar.setPlayers(players)
        bossBar.update(newGame)
        bossBar.show()

        return true
    }

    // ゲーム終了
    fun stop(): Boolean {
        val currentGame = game
            ?: return false

        if (currentGame.state != GameState.PLAYING) {
            return false
        }

        currentGame.stop()

        bossBar.hide()
        bossBar.removeAll()

        runnerTeam.clear()

        return true
    }


    // 経過時間の反映
    fun tick(elapsed: Duration) {
        val currentGame = game
            ?: return
        
        currentGame.tick(elapsed)
        
        if (currentGame.state == GameState.FINISHED) {
            bossBar.hide()
            bossBar.removeAll()
        
            runnerTeam.clear()
        
            game = null
        
            return
        }
    
        bossBar.update(currentGame)
    }
}