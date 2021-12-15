package com.example.tic_tac_toe

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.jakewharton.rxbinding4.view.touches
import io.reactivex.rxjava3.core.Observable

class InteractiveGridView: GameGridView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributes: AttributeSet) : super(context, attributes)
    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributes,
        defStyleAttr
    )

    fun getTouchesOnGrid(): Observable<GridPosition> {
        return this.touches()
            .filter { it.action == MotionEvent.ACTION_UP }
            .map {
                getGridPosition(
                    it.x, it.y,
                    this.width, this.height,
                    GRID_WIDTH, GRID_HEIGHT
                )
            }
    }

}