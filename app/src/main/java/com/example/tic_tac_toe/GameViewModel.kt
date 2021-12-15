package com.example.tic_tac_toe

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class GameViewModel(val userTouchObservable: Observable<GridPosition>) {
    private val subscriptions = CompositeDisposable()
    private val gameGridSubject: BehaviorSubject<GameGrid> = BehaviorSubject.createDefault(GameGrid())

    fun getGameGrid(): Observable<GameGrid> {
        return gameGridSubject.hide()
    }

    fun subscribe(){
        subscriptions.add(
            userTouchObservable.withLatestFrom(gameGridSubject,
                { gridPosition, gameGrid ->
                    gameGrid.setSymbolAt(gridPosition, GameSymbol.CIRCLE) })
                .subscribe(gameGridSubject::onNext)
        )
    }
}