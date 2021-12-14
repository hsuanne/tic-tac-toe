package com.example.tic_tac_toe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.view.touches
import io.reactivex.rxjava3.core.Observable


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
            )
        )

        val userTouchObservable = gameGridView.touches()
            .filter { it.action == MotionEvent.ACTION_UP }
            .map {
                getGridPosition(
                    it.x, it.y,
                    gameGridView.width, gameGridView.height,
                    GRID_WIDTH, GRID_HEIGHT
                )
            }
        userTouchObservable.subscribe {
            println("GridPos (${it.i}, ${it.n})")
        }
    }
}