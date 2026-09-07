package com.Sakamochanq.otukai.game

class GameScore {

    var currentScore: Int = 0
        private set

    var bestScore: Int = 0
        private set

    fun addScore() {
        currentScore++

        if (currentScore > bestScore) {
            bestScore = currentScore
        }
    }

    fun resetCurrentScore() {
        currentScore = 0
    }

    fun resetAll() {
        currentScore = 0
        bestScore = 0
    }
}