package com.example.tic_tac_toe

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.view.touches
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gameGridView: GameGridView = findViewById(R.id.gameGridView)

        val emptyGrid = GameGrid()
        gameGridView.setData(emptyGrid)

        val userTouchObservable = gameGridView.touches()
            .filter { it.action == MotionEvent.ACTION_UP }
            .map {
                getGridPosition(
                    it.x, it.y,
                    gameGridView.width, gameGridView.height,
                    GRID_WIDTH, GRID_HEIGHT
                )
            }

        // keeps the latest GameGrid
        val gameGridSubject: BehaviorSubject<GameGrid> = BehaviorSubject.createDefault(emptyGrid)
        userTouchObservable.withLatestFrom(gameGridSubject,
             { gridPosition, gameGrid -> gameGrid.setSymbolAt(gridPosition, GameSymbol.CIRCLE) })
            .subscribe(gameGridSubject::onNext)

        gameGridSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(gameGridView::setData)
    }
}