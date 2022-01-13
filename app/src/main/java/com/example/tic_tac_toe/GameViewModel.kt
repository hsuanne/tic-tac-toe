package com.example.tic_tac_toe

import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.jakewharton.rxbinding4.view.touches
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class GameViewModel(
    private val userTouchObservable: Observable<GridPosition>
): ViewModel() {
    private val subscriptions = CompositeDisposable()
    private val gameStateSubject: BehaviorSubject<GameState> =
        BehaviorSubject.createDefault(GameState(GameGrid(), GameSymbol.EMPTY))
    private val playerInTurnSubject: BehaviorSubject<GameSymbol> =
        BehaviorSubject.create()
    private val gameStatusSubject: BehaviorSubject<GameStatus> =
        BehaviorSubject.create()

    fun getGameGrid(): Observable<GameState> {
        return gameStateSubject.hide()
    }

    fun getGameStatus(): Observable<GameStatus> {
        return gameStatusSubject.hide()
    }

    fun getResultVisibility(): Observable<Boolean> {
        return gameStatusSubject
            .map {
                it.isEnded
            }
    }

    fun getPlayerName(): Observable<String> {
        return playerInTurnSubject
            .map {
                if (it == GameSymbol.CIRCLE) "Circle"
                else "Cross"
            }
    }

    fun getPlayerInTurn(): Observable<GameSymbol> {
        return playerInTurnSubject.hide()
    }

    fun restartAtTouch(textView: TextView) {
        textView.touches()
            .filter { it.action == MotionEvent.ACTION_UP }
            .map { GameState(GameGrid(), GameSymbol.EMPTY) }
            .subscribe {
                gameStateSubject.onNext(it)
            }
    }

    fun restart() {
        val state = GameState(GameGrid(), GameSymbol.EMPTY)
        gameStateSubject.onNext(state)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    fun subscribe() {
        val gameInfoObservable =
            Observable.combineLatest(gameStateSubject, playerInTurnSubject, { gameState, symbol ->
                Pair(gameState, symbol)
            })

        val gameNotEndedTouches =
            userTouchObservable.withLatestFrom(gameStatusSubject, { gridPosition, gameStatus ->
                Pair(gridPosition, gameStatus)
            })
                .filter {
                    !it.second.isEnded
                }
                .map {
                    it.first
                }

        val filteredTouchesEventObservable =
            gameNotEndedTouches.withLatestFrom(gameStateSubject, { gridPosition, gameState ->
                Pair(gridPosition, gameState)
            })
                .filter {
                    val gridPosition = it.first
                    val gameState = it.second
                    gameState.isEmpty(gridPosition)
                }
                .map { it.first }

        filteredTouchesEventObservable.withLatestFrom(gameInfoObservable,
            { gridPosition, gameInfo ->
                val gameState = gameInfo.first
                gameState.getNextGameState(gridPosition, gameInfo.second)
            })
            .subscribe(gameStateSubject::onNext)
            .addTo(subscriptions)

        gameStateSubject
            .map(GameState::getLastSymbol)
            .map {
                when (it) {
                    GameSymbol.CIRCLE -> GameSymbol.CROSS
                    else -> GameSymbol.CIRCLE
                }
            }
            .subscribe {
                playerInTurnSubject.onNext(it)
            }
            .addTo(subscriptions)

        val gameStatus = GameStatus()
            gameStateSubject
                .map {
                    it.getGrid()
                }
                .map {
                    val winner = calculateWinnerForGrid(it)
                    if (winner != null || it.isGridFull()) {
                        if (winner != null) gameStatus.ended(winner)
                        else gameStatus.ended(GameSymbol.EMPTY)
                    }
                    else gameStatus.ongoing()
                }
                .subscribe {
                    gameStatusSubject.onNext(it)
                }
                .addTo(subscriptions)

    }
}


fun Disposable.addTo(compositeDisposable: CompositeDisposable): Disposable
        = apply { compositeDisposable.add(this) }