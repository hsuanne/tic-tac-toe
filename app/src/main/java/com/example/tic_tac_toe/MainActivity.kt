package com.example.tic_tac_toe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gameGridView: GameGridView = findViewById(R.id.gameGridView)
        gameGridView.setData(
            arrayOf(
            arrayOf(GameSymbol.CROSS, GameSymbol.CROSS, GameSymbol.CROSS),
            arrayOf(GameSymbol.CIRCLE, GameSymbol.CIRCLE, GameSymbol.CIRCLE),
            arrayOf(GameSymbol.CIRCLE, GameSymbol.CROSS, GameSymbol.CIRCLE)
        ))
    }
}