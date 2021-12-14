package com.example.tic_tac_toe

class GridPosition(val i: Int, val n: Int) {
}

fun getGridPosition(
    touchX: Float, touchY: Float,
    viewWidthPixels: Int, viewHeightPixels: Int,
    gridWidth: Int, gridHeight: Int
): GridPosition {
    // horizontal grid position
    val rx = touchX / (viewWidthPixels)
    val i = (rx * gridWidth).toInt()

    // vertical grid position
    val ry = touchY / viewHeightPixels
    val n = (ry * gridHeight).toInt()

    return GridPosition(i, n)
}