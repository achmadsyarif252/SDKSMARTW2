package com.example.blesdktest.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.blesdktest.R
import com.goodix.ble.libcomx.annotation.NonNull
import com.goodix.ble.libcomx.annotation.Nullable
import com.orhanobut.logger.Logger


/**
 * ECG report measurement page
 * Created by Administrator on 2018/11/5.
 */
class EcgHeartRealthViewKotlin(context: Context, @Nullable attrs: AttributeSet?) :
    View(context, attrs) {
    private var mWidth = 0f
    private var mHeight = 0f
    private var mLinePiant: Paint? = null
    private var mWhiteRectPiant: Paint? = null
    private var mGridPiant: Paint? = null
    private var mBordGridPiant: Paint? = null
    private lateinit var heartPositionY: FloatArray
    private lateinit var mPoints: Array<PointF?>
    private var ecgLineColor = 0
    private var color_line = 0
    private var color_black = 0
    private var linePositionX = 0
    private fun initPaint() {
        mLinePiant = Paint()
        mLinePiant!!.color = ecgLineColor
        mLinePiant!!.strokeCap = Paint.Cap.SQUARE
        mLinePiant!!.style = Paint.Style.STROKE
        mLinePiant!!.strokeWidth = 4f
        mLinePiant!!.isAntiAlias = true
        mWhiteRectPiant = Paint()
        mWhiteRectPiant!!.color = Color.WHITE
        mWhiteRectPiant!!.strokeCap = Paint.Cap.SQUARE
        mWhiteRectPiant!!.style = Paint.Style.FILL
        mWhiteRectPiant!!.strokeWidth = 4f
        mWhiteRectPiant!!.isAntiAlias = true
        mGridPiant = Paint()
        mGridPiant!!.color = color_line
        mGridPiant!!.strokeWidth = 2f
        mGridPiant!!.strokeCap = Paint.Cap.ROUND
        mGridPiant!!.isAntiAlias = true
        mBordGridPiant = Paint()
        mBordGridPiant!!.color = color_black
        mBordGridPiant!!.strokeWidth = 2f
        mBordGridPiant!!.strokeCap = Paint.Cap.ROUND
        mBordGridPiant!!.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Logger.t(TAG).i("onMeasure")
        mWidth = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        mHeight = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        onPagerSetting()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //        Logger.t(TAG).i("onDraw");
        val stopX = mWidth
        for (i in 0..coumlnQutoCount) {
//            Logger.t(TAG).i("onDraw: " + stopX);
            if (i % 5 == 0) {
                canvas.drawLine(0f, rowQutoWidth * i, stopX, rowQutoWidth * i, mBordGridPiant!!)
            } else {
                canvas.drawLine(0f, rowQutoWidth * i, stopX, rowQutoWidth * i, mGridPiant!!)
            }
        }
        run {
            var i = 0
            while (i <= rowQutoCount) {
                if (i % 5 == 0) {
                    canvas.drawLine(
                        coumlnQutoWidth * i, 0f, coumlnQutoWidth * i, mHeight,
                        mBordGridPiant!!
                    )
                } else {
                    canvas.drawLine(
                        coumlnQutoWidth * i, 0f, coumlnQutoWidth * i, mHeight,
                        mGridPiant!!
                    )
                }
                i++
            }
        }
        for (i in mPoints.indices) {
            val point = PointF(rowEcgWidth * i, heartPositionY[i])
            mPoints[i] = point
        }
        drawPath(canvas, mPoints, linePositionX, item_contents)
    }

    private fun getRowY(v: Float): Float {
        return mHeight / 5 * 3 - v / 175 * coumlnQutoWidth
    }

    private fun drawPath(canvas: Canvas, mPoints: Array<PointF?>, pointX: Int, item_contents: Int) {
        val arrayLeft: Array<PointF?>?
        val arrayMiddle: Array<PointF?>?
        val arrayRight: Array<PointF?>?
        val length = mPoints.size
        val leftLength: Int
        val middleLength: Int
        val rightLength: Int
        if (pointX <= 0) {
            leftLength = 0
            middleLength = item_contents
            rightLength = length - item_contents
            arrayLeft = arrayOfNulls(leftLength)
            arrayMiddle = arrayOfNulls(middleLength)
            arrayRight = arrayOfNulls(rightLength)
            Log.i(
                TAG,
                "drawPath: left arr$length,pointX=$pointX,rightLength=$rightLength"
            )
            for (i in mPoints.indices) {
                if (i < item_contents) {
                    arrayMiddle[i] = mPoints[i]
                } else {
                    arrayRight[i - item_contents] = mPoints[i]
                }
            }
        } else if (pointX + item_contents >= length) {
            leftLength = pointX
            middleLength = pointX + item_contents - length
            rightLength = 0
            Log.i(
                TAG,
                "drawPath: right arr$length,pointX=$pointX,rightLength 0"
            )
            arrayLeft = arrayOfNulls(leftLength)
            arrayMiddle = arrayOfNulls(middleLength)
            arrayRight = arrayOfNulls(rightLength)
            for (i in mPoints.indices) {
                if (i < pointX) {
                    arrayLeft[i] = mPoints[i]
                } else if (i < pointX + middleLength) {
                    //   Log.i(TAG, "drawPath: right arr,[i - pointX]=" + (i - pointX) + ",i=" + i + ",middleLength=" + middleLength);
                    arrayMiddle[i - pointX] = mPoints[i]
                }
            }
        } else {
            leftLength = pointX
            middleLength = item_contents
            rightLength = length - pointX - item_contents
            arrayLeft = arrayOfNulls(leftLength)
            arrayMiddle = arrayOfNulls(middleLength)
            arrayRight = arrayOfNulls(rightLength)
            Log.i(
                TAG,
                "drawPath: middle arr$length,pointX=$pointX,leftLength=$leftLength,middleLength=$middleLength,right=$rightLength"
            )
            for (i in mPoints.indices) {
                if (i < pointX) {
                    arrayLeft[i] = mPoints[i]
                } else if (i < pointX + item_contents) {
                    arrayMiddle[i - pointX] = mPoints[i]
                } else {
                    arrayRight[rightLength - (length - i)] = mPoints[i]
                }
            }
        }
        drawCubicLine(canvas, arrayLeft, ecgLineColor)
        drawCubicLine(canvas, arrayMiddle, Color.TRANSPARENT)
        drawCubicLine(canvas, arrayRight, ecgLineColor)
    }

    private fun drawCubicLine(canvas: Canvas, mPoints: Array<PointF?>?, color: Int) {
        if (mPoints == null || mPoints.size <= 2) {
            return
        }
        mLinePiant!!.color = color
        val mPath = getCubicLinePath(mPoints)
        canvas.drawPath(mPath, mLinePiant!!)
    }

    @NonNull
    private fun getCubicLinePath(mPoints: Array<PointF?>): Path {
        val mPath = Path()
        for (j in mPoints.indices) {
            val startp = mPoints[j]
            var endp: PointF?
            if (j != mPoints.size - 1) {
                endp = mPoints[j + 1]
                if (endp == null) {
                    continue
                }
                val wt = (startp!!.x + endp.x) / 2
                val p3 = PointF()
                val p4 = PointF()
                p3.x = wt
                p3.y = startp.y
                p4.x = wt
                p4.y = endp.y
                if (j == 0) {
                    mPath.moveTo(startp.x, startp.y)
                }
                mPath.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y)
            }
        }
        return mPath
    }

    private var item_contents = 0

    @Synchronized
    fun changeData(data: IntArray, item_content: Int) {
        item_contents = item_content
        linePositionX = linePositionX % rowEcgCount
        for (i in linePositionX until linePositionX + item_contents) {
            if (i >= rowEcgCount) {
                linePositionX = -item_content
                break
            }
            val rowY = getRowY(data[i - linePositionX].toFloat())
            heartPositionY[i] = rowY
        }
        Logger.t(TAG).i("changeData: columnCount=$rowEcgCount,linePositionX=$linePositionX")
        invalidate()
        linePositionX = linePositionX + item_content
    }

    /**
     * Clear and initialize the data (600 points of the calculation list, data playback points)，）
     */
    fun clearData() {
        linePositionX = 0
        onPagerSetting()
        invalidate()
    }

    var arraylist: List<Int> = ArrayList()

    //Sampling rate & travel speed
    var HZ = 250
    var SPEED = 25

    //一How many dots in a grid
    var count = HZ / SPEED

    //How many grids are placed vertically in total
    var coumlnQutoCount = 16 * 5

    //一Height of the grid
    var coumlnQutoWidth = 1f

    //How many cells are placed horizontally in total
    var rowQutoCount = 1f

    //一The width of a grid
    var rowQutoWidth = 1f

    //How many points are placed horizontally in total
    var rowEcgCount = 1

    //一Width of the points
    var rowEcgWidth = 1f

    init {
        ecgLineColor = context.resources.getColor(R.color.ecg_line)
        color_line = resources.getColor(R.color.ecg_line_bg_normal)
        color_black = resources.getColor(R.color.ecg_line_bg_bold)
        initPaint()
    }

    private fun onPagerSetting() {
        coumlnQutoWidth = mHeight / coumlnQutoCount
        rowQutoWidth = coumlnQutoWidth
        rowQutoCount = mWidth / rowQutoWidth
        rowEcgCount = (rowQutoCount * count).toInt()
        rowEcgWidth = mWidth / rowEcgCount
        mPoints = arrayOfNulls(rowEcgCount)
        initHeartPosition()
    }

    private fun initHeartPosition() {
        heartPositionY = FloatArray(rowEcgCount)
        for (i in 0 until rowEcgCount) {
            heartPositionY[i] = mHeight / 5 * 3
        }
    }

    companion object {
        private val TAG = EcgHeartRealthView::class.java.simpleName
    }
}