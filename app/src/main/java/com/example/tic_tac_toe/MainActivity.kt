package com.example.tic_tac_toe

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.view.touches
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable


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

        gameViewModel.getPlayerInTurn()
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                if (it == GameSymbol.CIRCLE) "O"
                else "X"
            }
            .subscribe {
                playerView.text = "Player: $it"
            }
            .addTo(viewSubscriptions)

        gameViewModel.getGameStatus()
            .map {
                it.isEnded
            }
            .map {
                if (it) View.VISIBLE
                else View.GONE
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                resultView.visibility = it
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

        gameViewModel.restartAtTouch(resultView)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewSubscriptions.dispose()
    }
}