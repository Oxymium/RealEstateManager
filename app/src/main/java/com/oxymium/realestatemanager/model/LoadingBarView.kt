package com.oxymium.realestatemanager.model

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

const val LOADING_COLOR = "#2196F3"
class LoadingBarView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var progress: Float = 0f
    private val paint: Paint = Paint()

    init {
        paint.color = Color.parseColor(LOADING_COLOR)
        paint.style = Paint.Style.FILL
    }

    fun setProgress(percentage: Float) {
        progress = percentage
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width * progress
        canvas.drawRect(0f, 0f, width, height.toFloat(), paint)
    }
}
