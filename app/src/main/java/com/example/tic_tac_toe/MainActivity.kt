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

        val gameGridView: InteractiveGridView = findViewById(R.id.gameGridView)

        val emptyGrid = GameGrid()
        gameGridView.setData(emptyGrid)

        val userTouchObservable = gameGridView.getTouchesOnGrid()
        val gameViewModel = GameViewModel(userTouchObservable)
        gameViewModel.subscribe()
        val gameGridSubject = gameViewModel.getGameGrid()
        gameGridSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(gameGridView::setData)
    }
}