package com.example.tic_tac_toe

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

const val GRID_WIDTH = 3
const val GRID_HEIGHT = 3

class GameGridView : View {
    private val linePaint = Paint()
    private lateinit var circle: Bitmap
    private lateinit var cross: Bitmap
    private lateinit var bitmapSrcRect: Rect
    private lateinit var bitmapPaint: Paint

    constructor(context: Context) : super(context)
    constructor(context: Context, attributes: AttributeSet) : super(context, attributes)
    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributes,
        defStyleAttr
    ) {
        linePaint.color = Color.BLACK
        linePaint.strokeWidth = 8f

        circle = BitmapFactory.decodeResource(resources, R.drawable.circle)
        cross = BitmapFactory.decodeResource(resources, R.drawable.cross)
        bitmapSrcRect = Rect(0, 0, cross.width, cross.height)
        bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }


    private lateinit var gameState: GameGrid

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // clear the old drawings
        canvas?.drawColor(Color.WHITE)

        drawGridLines(canvas, GRID_WIDTH.toFloat(), GRID_HEIGHT.toFloat(), width / 3f, height / 3f)
        drawSymbols(canvas, GRID_WIDTH.toFloat(), GRID_HEIGHT.toFloat(), width / 3f, height / 3f)
    }

    fun setData(gameState: GameGrid) {
        // save data for drawing
        this.gameState = gameState

        // schedule redraw
        this.invalidate()
    }

    private fun drawGridLines(
        canvas: Canvas?,
        gridWidth: Float, gridHeight: Float,
        tileWidth: Float, tileHeight: Float
    ) {
        linePaint.color = Color.BLACK
        linePaint.strokeWidth = 8f
        for (i in 0..gridWidth.toInt()) {
            canvas?.drawLine(i * tileWidth, 0f, i * tileWidth, height.toFloat(), linePaint)
        }
        for (n in 0..gridHeight.toInt()) {
            canvas?.drawLine(0f, n * tileHeight, width.toFloat(), n * tileHeight, linePaint)
        }
    }

    private fun drawSymbols(
        canvas: Canvas?,
        gridWidth: Float, gridHeight: Float,
        tileWidth: Float, tileHeight: Float
    ) {
        circle = BitmapFactory.decodeResource(resources, R.drawable.circle)
        cross = BitmapFactory.decodeResource(resources, R.drawable.cross)
        bitmapSrcRect = Rect(0, 0, cross.width, cross.height)
        bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        for (i in 0 .. 2) {
            for (n in 0 .. 2) {
                val symbol = gameState.getSymbolAt(GridPosition(i,n))
                val dstCross = RectF(i*tileWidth, n*tileHeight, (i+1)*tileWidth, (n+1)*tileHeight)
                val dstCircle = RectF(i*tileWidth+90, n*tileHeight+90, (i+1)*tileWidth, (n+1)*tileHeight)
                if (symbol == GameSymbol.CIRCLE){
                    canvas?.drawBitmap(circle, bitmapSrcRect, dstCircle, bitmapPaint)
                } else if (symbol == GameSymbol.CROSS) {
                    canvas?.drawBitmap(cross, bitmapSrcRect, dstCross, bitmapPaint)
                }
            }
        }
    }
}