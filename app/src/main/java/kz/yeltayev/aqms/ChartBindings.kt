package kz.yeltayev.aqms

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import androidx.databinding.BindingAdapter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.formatter.ValueFormatter
import kz.yeltayev.aqms.widget.chart.CustomMarkerView
import kz.yeltayev.aqms.widget.chart.RoundedBarRenderer
import kotlin.math.roundToInt

private const val ANIM_DURATION = 600

object ChartBindings {

    @BindingAdapter("barChart_data")
    @JvmStatic
    fun setChartValue(barChart: BarChart, data: BarData?) {
        barChart.renderer =
            RoundedBarRenderer(barChart, barChart.animator, barChart.viewPortHandler)

        val mPaint = barChart.renderer.paintRender

        mPaint.shader = LinearGradient(
            0f, 0f, 0f, barChart.height.toFloat(),
            intArrayOf(Color.parseColor("#103A58"), Color.parseColor("#103A58")),
            floatArrayOf(0f, 2f), Shader.TileMode.CLAMP
        )

        barChart.setScaleEnabled(false)

        barChart.legend.isEnabled = false
        barChart.animateY(ANIM_DURATION)

        barChart.xAxis.gridColor = android.R.color.transparent
        barChart.xAxis.textColor = R.color.dark
        barChart.axisLeft.isEnabled = true
        barChart.axisRight.isEnabled = false
        barChart.axisLeft.axisMinimum = 0f

        barChart.isDoubleTapToZoomEnabled = false
        barChart.setPinchZoom(false)
        barChart.description.isEnabled = false
        barChart.setDrawGridBackground(false)

        barChart.data = data

        barChart.scrollBarStyle = BarChart.SCROLLBARS_OUTSIDE_OVERLAY
        barChart.scrollBarSize = 2

        barChart.setVisibleXRangeMaximum(7f)
        barChart.isHorizontalScrollBarEnabled = true
        barChart.scrollBarDefaultDelayBeforeFade = 100000

        val marker = CustomMarkerView(barChart.context, R.layout.widget_marker_chart)

        barChart.marker = marker
        barChart.marker.offset.x = -(marker.width / 2).toFloat()
        barChart.marker.offset.y = -marker.height.toFloat()
        barChart.setDrawMarkers(true)

        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        if (data != null) {
            barChart.moveViewToX(data.xMax)
        }
    }

    @BindingAdapter("barChart_xAxis")
    @JvmStatic
    fun setXAxis(barChart: BarChart, xAxisValues: List<String>?) {
        if (xAxisValues != null) {
            barChart.xAxis.valueFormatter = MyXAxisValueFormatter(xAxisValues)
            barChart.invalidate()
        }
    }

    @BindingAdapter("barChart_yAxis")
    @JvmStatic
    fun setYAxis(barChart: BarChart, xAxisValues: List<Float>?) {
        if (xAxisValues != null) {
            barChart.axisLeft.valueFormatter = MyYAxisValueFormatter(xAxisValues)
            barChart.invalidate()
        }
    }

    @BindingAdapter("barChart_noDataText")
    @JvmStatic
    fun setBarChartNoDataText(barChart: BarChart, text: String?) {
        barChart.setNoDataText(text)
    }

    @BindingAdapter("barChart_maxValue")
    @JvmStatic
    fun setMaxValue(barChart: BarChart, maxValue: Int) {
        barChart.setMaxVisibleValueCount(maxValue)
    }
}


private class MyXAxisValueFormatter(private val values: List<String>) : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return values[value.toInt() - 1]
    }
}

private class MyYAxisValueFormatter(private val values: List<Float>) : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return value.roundToInt().toString()
    }
}