package com.example.tic_tac_toe

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.view.touches
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    val viewSubscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gameGridView: InteractiveGridView = findViewById(R.id.gameGridView)

        val emptyGrid = GameGrid()
        gameGridView.setData(emptyGrid)

        val userTouchObservable = gameGridView.getTouchesOnGrid()
        val gameViewModel = GameViewModel(userTouchObservable)
        val resultView: TextView = findViewById(R.id.resultView)
        val playerView: TextView = findViewById(R.id.playerView)

        gameViewModel.subscribe()

        gameViewModel.getGameGrid()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                gameGridView.setData(it.getGrid())
            }
            .addTo(viewSubscriptions)

        gameViewModel.getPlayerName()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                playerView.text = "Player: $it"
            }
            .addTo(viewSubscriptions)

        gameViewModel.getResultVisibility()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it) resultView.visibility = View.VISIBLE
                else resultView.visibility = View.GONE
            }
            .addTo(viewSubscriptions)

        gameViewModel.getGameStatus()
            .map {
                "Winner: ${it.winner}"
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                resultView.text = it
            }
            .addTo(viewSubscriptions)

        resultView.clicks()
            .throttleFirst(1000,TimeUnit.MILLISECONDS)
            .subscribe {
                gameViewModel.restart()
            }
            .addTo(viewSubscriptions)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewSubscriptions.dispose()
    }
}