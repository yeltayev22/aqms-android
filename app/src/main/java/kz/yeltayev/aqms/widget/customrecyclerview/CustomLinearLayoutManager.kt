package kz.yeltayev.aqms.widget.customrecyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import kz.yeltayev.aqms.utils.dpToPxF
import timber.log.Timber

/**
 * A linear layout manager that has the ability to control smooth scroll speed.
 * Smooth scrolling is activated using [RecyclerView.smoothScrollBy]
 * and [RecyclerView.smoothScrollToPosition].
 *
 * It can also accurately compute vertical scroll offset and total scroll range
 * due to child height caching.
 *
 * @param dpPerSecond Speed of smooth scrolling.
 */
@SuppressLint("WrongConstant")
class CustomLinearLayoutManager(
    private val context: Context,
    dpPerSecond: Int,
    orientation: Int = HORIZONTAL,
    reverseLayout: Boolean = false
) : LinearLayoutManager(context, orientation, reverseLayout) {

    private var msPerPixel = calculateMsPerPixel(dpPerSecond)
    private val childSizesMap = mutableMapOf<Int, Int>()

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State, position: Int) {

        val smoothScroller = object : LinearSmoothScroller(recyclerView.context) {

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return msPerPixel
            }
        }

        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    /**
     * Set the smooth scroll speed.
     *
     * @param dpPerSecond The speed of smooth scrolling.
     */
    fun setSmoothScrollSpeed(dpPerSecond: Int) {
        msPerPixel = calculateMsPerPixel(dpPerSecond)
    }

    private fun calculateMsPerPixel(dpPerSecond: Int): Float {
        return (1f / (dpPerSecond / 1000f)) / context.dpToPxF(1)
    }

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child != null) {
                childSizesMap[getPosition(child)] = child.height
            }
        }
    }

    fun computeAccurateVerticalScrollOffset(): Int {
        if (childCount == 0) {
            return 0
        }

        val firstChild = getChildAt(0)
        if (firstChild != null) {
            val firstChildPosition = getPosition(firstChild)

            var scrolledY: Int = -firstChild.y.toInt()
            Timber.d("firstChildPosition: $firstChildPosition")
            Timber.d("initData scrolledY: $scrolledY")
            for (i in 0 until firstChildPosition) {
                scrolledY += childSizesMap[i] ?: 0
            }
            Timber.d("after scrolledY: $scrolledY")

            return scrolledY
        } else {
            return 0
        }
    }

    fun computeAccurateVerticalScrollRange() {

    }
}