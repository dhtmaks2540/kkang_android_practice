package kr.co.lee.part4_13

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MyPlusView: View {
    var attrs: AttributeSet? = null

    // 증감 값
    var value: Int = 0

    // 화면 출력 이미지
    val plusBitmap: Bitmap
    val minusBitmap: Bitmap

    // 이미지가 화면에 출력되는 좌표 정보
    val plusRectDst: Rect
    val minusRectDst: Rect

    // value 출력 문자열 색상
    var textColor: Int = 0

    // Observer를 등록하기 위한 객체
    val listeners: ArrayList<OnMyChangeListener>

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        this.attrs = attrs
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
                this.attrs = attrs
            }

    init {
        // 이미지 획득
        plusBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.plus)
        minusBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.minus)

        // 이미지 출력 사각형 좌표 정보 설정
        plusRectDst = Rect(10, 10, 210, 210)
        minusRectDst = Rect(400, 10, 600, 210)

        // custom 속성값 획득
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.MyView)
            textColor = a.getColor(R.styleable.MyView_customTextColor, Color.RED)
        }
        listeners = ArrayList()
    }

    // Observer 등록을 위한 함수
    fun setOnMyChangeListener(listener: OnMyChangeListener) {
        listeners.add(listener)
    }

    // 크기 결정
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 매개변수로 넘어논 넓이, 높이의 정보를 가지고 모드(wrap_content, match_parent등)와 레이아웃의 크기를 얻어온다
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        var width = 0
        var height = 0

        if (widthMode == MeasureSpec.AT_MOST) {
            width = 700
        } else if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            height = 250
        } else if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize
        }

        setMeasuredDimension(width, height)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.x?.toInt()
        val y = event?.y?.toInt()
        // 플러스 아이콘이 터치된거라면
        if (plusRectDst.contains(x!!, y!!) && event.action == MotionEvent.ACTION_DOWN) {
            // 데이터 변경
            value++
            // 화면 갱신
            invalidate()
            for (listener in listeners) {
                // observer에 데이터 전달
                listener.onChange(value)
            }
            return true
        } else if (minusRectDst.contains(x, y) && event.action == MotionEvent.ACTION_DOWN) {
            value--
            invalidate()
            for (listener in listeners) {
                // observer에 데이터 전달
                listener.onChange(value)
            }
            return true
        }

        return false
    }

    override fun onDraw(canvas: Canvas?) {
        // 화면 지우기
        canvas?.drawColor(Color.alpha(Color.CYAN))

        // 이미지의 사각형 정보
        val plusRectSource = Rect(0, 0, plusBitmap.width, plusBitmap.height)
        val minusRectSource = Rect(0, 0, minusBitmap.width, minusBitmap.height)

        val paint = Paint()

        // plus 이미지 그리기
        canvas?.drawBitmap(plusBitmap, plusRectSource, plusRectDst, null)

        // value 문자열 그리기
        paint.textSize = 80f
//        paint.color = textColor
//        canvas?.drawText("${if(value.toString() != null) value.toString() else "null"}", 260.0f, 150.0f, paint)
        canvas?.drawText(value.toString()   , 260.0f, 150.0f, paint)

        // minus 이미지 그리기
        canvas?.drawBitmap(minusBitmap, minusRectSource, minusRectDst, null)

    }
}