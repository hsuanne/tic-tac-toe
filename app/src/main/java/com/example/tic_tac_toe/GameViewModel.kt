package com.example.tic_tac_toe

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class GameViewModel(
    private val userTouchObservable: Observable<GridPosition>
) {
    private val subscriptions = CompositeDisposable()
    private val gameStateSubject: BehaviorSubject<GameState> =
        BehaviorSubject.createDefault(GameState(GameGrid(), GameSymbol.EMPTY))
    private val playerInTurnSubject: BehaviorSubject<GameSymbol> =
        BehaviorSubject.create()

    fun getGameGrid(): Observable<GameState> {
        return gameStateSubject.hide()
    }

    fun subscribe() {
        val gameInfoObservable =
            Observable.combineLatest(gameStateSubject, playerInTurnSubject, { gameState, symbol ->
                Pair(gameState, symbol)
            })

        val filteredTouchesEventObservable =
            userTouchObservable.withLatestFrom(gameStateSubject, {gridPosition, gameState ->
                Pair(gridPosition, gameState)
            })
                .filter {
                    val gridPosition = it.first
                    val gameState = it.second
                    gameState.isEmpty(gridPosition)
                }
                .map { it.first }

        subscriptions.add(
            filteredTouchesEventObservable.withLatestFrom(gameInfoObservable,
                { gridPosition, gameInfo ->
                    val gameState = gameInfo.first
                    gameState.getNextGameState(gridPosition, gameInfo.second)
                })
                .subscribe(gameStateSubject::onNext)
        )

        subscriptions.add(
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
        )

    }
}