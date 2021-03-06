package kz.yeltayev.aqms

import android.animation.LayoutTransition
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ramijemli.percentagechartview.PercentageChartView
import kz.yeltayev.aqms.utils.PRECISION
import java.math.BigDecimal
import java.math.RoundingMode

object Bindings {

    @BindingAdapter("toastMessage")
    @JvmStatic
    fun runMe(view: View, message: String?) {
        if (message != null) {
            Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show()
        }
    }

    @BindingAdapter("textView_textRes")
    @JvmStatic
    fun setTextRes(textView: TextView, textRes: Int) {
        if (textRes != 0) {
            textView.setText(textRes)
        } else {
            textView.text = null
        }
    }

    @BindingAdapter("textView_toString")
    @JvmStatic
    fun setStringText(textView: TextView, `object`: Any) {
        if (`object` is BigDecimal) {
            val value = `object`.setScale(PRECISION, RoundingMode.HALF_UP)
            textView.text = value.toString()
        } else {
            textView.text = `object`.toString()
        }
    }

    @BindingAdapter("imageView_src")
    @JvmStatic
    fun loadDrawableResource(imageView: ImageView, drawableRes: Int) {
        if (drawableRes != 0) {
            imageView.setImageResource(drawableRes)
        } else {
            imageView.setImageDrawable(null)
        }
    }

    @BindingAdapter("view_backgroundColor")
    @JvmStatic
    fun setBackgroundColor(view: View, @ColorRes colorRes: Int) {
        if (colorRes != 0) {
            view.setBackgroundColor(ContextCompat.getColor(view.context, colorRes))
        } else {
            view.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    @BindingAdapter("view_backgroundRes")
    @JvmStatic
    fun setBackgroundDrawable(view: View, drawableRes: Int) {
        if (drawableRes != 0) {
            view.setBackgroundResource(drawableRes)
        }
    }

    @BindingAdapter("pcv_progress")
    @JvmStatic
    fun setProgress(view: PercentageChartView, percentage: BigDecimal) {
        view.setProgress(percentage.toFloat(), true)
    }

    @BindingAdapter("srl_onRefresh")
    @JvmStatic
    fun setOnRefreshListener(view: SwipeRefreshLayout, callback: () -> Unit) {
        view.setOnRefreshListener {
            callback.invoke()
        }
    }

    @BindingAdapter("srl_isRefreshing")
    @JvmStatic
    fun setIsRefreshing(view: SwipeRefreshLayout, isRefreshing: Boolean) {
        view.isRefreshing = isRefreshing
    }

    @BindingAdapter("show")
    @JvmStatic
    fun setVisibility(view: View, isVisible: Boolean) {
        if (isVisible) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    @BindingAdapter("viewGroup_animateLayoutChanges")
    @JvmStatic
    fun animateLayoutChanges(viewGroup: ViewGroup, duration: Long) {
        val layoutTransition = LayoutTransition()
        if (duration > 0L) {
            layoutTransition.setDuration(duration)
        }
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    }
}