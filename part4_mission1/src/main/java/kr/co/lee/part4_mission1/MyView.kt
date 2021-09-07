package kr.co.lee.part4_mission1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MyView: View {
    var value: Int = 0

    var attrs: AttributeSet? = null
    val listeners: ArrayList<OnMyChangeListener>

    var widthSize: Int = 0
    var heightSize: Int = 0

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        this.attrs = attrs
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        this.attrs = attrs
    }

    init {
        listeners = ArrayList()
    }
    
    // Observer 등록을 위한 함수
    fun setOnChangeListener(listener: OnMyChangeListener) {
        listeners.add(listener)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        widthSize = MeasureSpec.getSize(widthMeasureSpec)
        heightSize = MeasureSpec.getSize(heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        val donutSize = context.resources.getDimensionPixelSize(R.dimen.donut_size)
        val donutStrokeSize = context.resources.getDimensionPixelSize(R.dimen.donut_stroke_size)
        val donutTextSize = context.resources.getDimensionPixelSize(R.dimen.donut_textSize)

        

        super.onDraw(canvas)
    }
}