package com.Sakamochanq.otukai.game

import com.Sakamochanq.otukai.task.TaskList
import kotlin.time.Duration

class GameManager {

    private var game: Game? = null

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

        val newGame = Game(TaskList.tasks)

        newGame.start()

        game = newGame

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

        return true
    }


    // 経過時間の反映
    fun tick(elapsed: Duration) {
        game?.tick(elapsed)

        if (game?.state == GameState.FINISHED) {
            game = null
        }
    }
}