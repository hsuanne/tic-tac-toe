package com.example.tic_tac_toe

class GameUtils {
}

fun calculateWinnerForGrid(gameGrid: GameGrid): GameSymbol? {
    for (i in 0 until GRID_WIDTH) {
        for (n in 0 until GRID_HEIGHT) {
            val player = gameGrid.getSymbolAt(GridPosition(i, n))
            if (player == GameSymbol.EMPTY) continue

            if (n + 2 < GRID_WIDTH &&
                player == gameGrid.getSymbolAt(GridPosition(i, n + 1)) &&
                player == gameGrid.getSymbolAt(GridPosition(i, n + 2))
            ) return player

            if (i + 2 < GRID_HEIGHT) {
                if (player == gameGrid.getSymbolAt(GridPosition(i + 1, n)) &&
                    player == gameGrid.getSymbolAt(GridPosition(i + 2, n))
                ) return player

                if (n + 2 < GRID_WIDTH &&
                    player == gameGrid.getSymbolAt(GridPosition(i + 1, n + 1)) &&
                    player == gameGrid.getSymbolAt(GridPosition(i + 2, n + 2))
                ) return player

                if (n - 2 >= 0 &&
                    player == gameGrid.getSymbolAt(GridPosition(i + 1, n - 1)) &&
                    player == gameGrid.getSymbolAt(GridPosition(i + 2, n - 2))
                ) return player
            }
        }
    }
    return null
}