package com.example.tic_tac_toe

class GameStatus {
    var isEnded: Boolean = false
    var winner: GameSymbol? = null

    fun ended(winner: GameSymbol): GameStatus {
        this.isEnded = true
        this.winner = winner
        return GameStatus()
    }

    fun ongoing(): GameStatus {
        this.isEnded = false
        this.winner = null
        return GameStatus()
    }
}