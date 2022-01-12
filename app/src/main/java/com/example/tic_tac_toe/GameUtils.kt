package com.example.tic_tac_toe

class GameUtils {
}

fun calculateWinnerForGrid(gameGrid: GameGrid): GameSymbol? {
    for (i in 0 until GRID_WIDTH) {
        for (n in 0 until GRID_HEIGHT) {
            val symbol = gameGrid.getSymbolAt(GridPosition(i, n))
            if (symbol == GameSymbol.EMPTY) continue

            if (n + 2 < GRID_WIDTH &&
                symbol == gameGrid.getSymbolAt(GridPosition(i, n + 1)) &&
                symbol == gameGrid.getSymbolAt(GridPosition(i, n + 2))
            ) return symbol

            if (i + 2 < GRID_HEIGHT) {
                if (symbol == gameGrid.getSymbolAt(GridPosition(i + 1, n)) &&
                    symbol == gameGrid.getSymbolAt(GridPosition(i + 2, n))
                ) return symbol

                if (n + 2 < GRID_WIDTH &&
                    symbol == gameGrid.getSymbolAt(GridPosition(i + 1, n + 1)) &&
                    symbol == gameGrid.getSymbolAt(GridPosition(i + 2, n + 2))
                ) return symbol

                if (n - 2 >= 0 &&
                    symbol == gameGrid.getSymbolAt(GridPosition(i + 1, n - 1)) &&
                    symbol == gameGrid.getSymbolAt(GridPosition(i + 2, n - 2))
                ) return symbol
            }
        }
    }
    return null
}