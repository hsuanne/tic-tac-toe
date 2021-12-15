package com.example.tic_tac_toe

import android.content.Context

class PlayerView: androidx.appcompat.widget.AppCompatImageView {
    constructor(context: Context) : super(context)

    fun setData(gameSymbol: GameSymbol) {
        when(gameSymbol) {
            GameSymbol.CIRCLE -> setImageResource(R.drawable.circle)
            GameSymbol.CROSS -> setImageResource(R.drawable.cross)
            else -> setImageResource(0)
        }
    }
}