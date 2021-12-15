package com.example.tic_tac_toe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gameGridView: InteractiveGridView = findViewById(R.id.gameGridView)

        val emptyGrid = GameGrid()
        gameGridView.setData(emptyGrid)

        val userTouchObservable = gameGridView.getTouchesOnGrid()
        val gameViewModel = GameViewModel(userTouchObservable)
        gameViewModel.subscribe()
        val gameStateSubject = gameViewModel.getGameGrid()
        gameStateSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                gameGridView.setData(it.getGrid())
            }
    }
}