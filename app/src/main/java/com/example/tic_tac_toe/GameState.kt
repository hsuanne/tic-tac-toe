package com.example.tic_tac_toe

class GameState(private val gameGrid: GameGrid, private val lastPlayedSymbol: GameSymbol) {

    fun getGrid(): GameGrid {
        return gameGrid
    }

    fun getLastSymbol(): GameSymbol{
        return lastPlayedSymbol
    }

    fun getNextGameState(gridPosition: GridPosition, gameSymbol: GameSymbol): GameState {
        return GameState(gameGrid.setSymbolAt(gridPosition, gameSymbol), gameSymbol)
    }

    fun isEmpty(gridPosition: GridPosition): Boolean {
        return gameGrid.getSymbolAt(gridPosition) == GameSymbol.EMPTY
    }
}