package com.example.tic_tac_toe

class GameGrid {
    private val width = 3
    private val height = 3
    private var grid:Array<Array<GameSymbol>> = Array(width) { Array(height) { GameSymbol.EMPTY } }

    constructor()

    constructor(grid:Array<Array<GameSymbol>>){
        this.grid = grid
    }

    fun getSymbolAt(gridPosition: GridPosition): GameSymbol {
        return grid[gridPosition.i][gridPosition.n]
    }

    fun setSymbolAt(gridPosition: GridPosition, gameSymbol: GameSymbol): GameGrid {
        val copy = this.copy()
        copy.grid[gridPosition.i][gridPosition.n] = gameSymbol
        return copy
    }

    private fun copy():GameGrid{
        val newGrid = GameGrid().grid
        for (i in 0 .. 2) {
            System.arraycopy(this.grid[i], 0, newGrid[i], 0, 3)
        }
        return GameGrid(newGrid)
    }

    fun isGridFull(): Boolean {
        for (i in 0..2) {
            for (n in 0..2) {
                if(grid[i][n] == GameSymbol.EMPTY) return false
            }
        }
        return true
    }
}

enum class GameSymbol {
    CIRCLE, CROSS, TRIANGLE, EMPTY
}