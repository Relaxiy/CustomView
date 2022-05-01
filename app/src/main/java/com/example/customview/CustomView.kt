package com.example.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Paint.Style
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CustomView(
    context: Context,
    attrs: AttributeSet
) : View(context, attrs)  {

    companion object {
        private const val LINE_WIDTH = 10f
        private const val LETTERS_LINE_WIDTH = 100f
        private const val START_COORDINATE = 0f
        private const val START_ANGLE = 113f
        private const val SWEEP_ANGLE = 133f
    }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomView, 0, 0)
            .apply {
                customText = getString(R.styleable.CustomView_CustomText).toString()
            }
    }

    private var customText: String

    private val textPaint = Paint(ANTI_ALIAS_FLAG)
        .apply {
            color = context.getColor(R.color.white)
            strokeWidth = LETTERS_LINE_WIDTH
        }


    private val backgroundPaint = Paint(ANTI_ALIAS_FLAG)
        .apply {
            strokeWidth = LINE_WIDTH
            style = Style.STROKE
            color = context.getColor(R.color.black)
        }

    private val rightLinePaint = Paint(ANTI_ALIAS_FLAG)
        .apply {
            strokeWidth = 5f
            style = Style.STROKE
            color = context.getColor(R.color.black)
        }

    private val rectangleBackgroundPaint = Paint(ANTI_ALIAS_FLAG)
        .apply {
            color = context.getColor(R.color.black)
        }

    private val centerOfX: Float get() = (width / 2).toFloat()

    private var defaultStartX = 0f
    private var stopX = 0f
    private var textCoordinateX = 0f
    private var textCoordinateY = 0f

    private val arcOval by lazy {
        RectF(
            width - textCoordinateX - 50f,
            5f,
            width - textCoordinateX + 123f,
            height.toFloat() - 5f
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        textPaint.textSize = height/3.5f
        defaultStartX = width/1.15f
        stopX = width.toFloat()
        textCoordinateX = centerOfX - textPaint.measureText(customText) / 1.8f
        textCoordinateY = height/1.7f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(width - textCoordinateX + 15f, height.toFloat(), width - textCoordinateX + 55f, height/2f, rightLinePaint)
        canvas.drawLine(width - textCoordinateX + 15f, START_COORDINATE, width - textCoordinateX + 55f, height/2f, rightLinePaint)
        canvas.drawLine(textCoordinateX - 50f, height.toFloat(), width - textCoordinateX + 15f, height.toFloat(), backgroundPaint)
        canvas.drawLine(textCoordinateX - 50f, START_COORDINATE, width - textCoordinateX + 15f, START_COORDINATE, backgroundPaint)
        canvas.drawLine(textCoordinateX - 50f, START_COORDINATE, textCoordinateX - 50f, height.toFloat(), rightLinePaint)
        canvas.drawArc(arcOval, START_ANGLE, SWEEP_ANGLE, true, rectangleBackgroundPaint)
        canvas.drawRect(textCoordinateX - 50f,15f, width - textCoordinateX, height - 15f, rectangleBackgroundPaint)
        canvas.drawText(
            customText,
            textCoordinateX,
            textCoordinateY,
            textPaint
        )
    }
}