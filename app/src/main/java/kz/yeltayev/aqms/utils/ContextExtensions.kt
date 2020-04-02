package kz.yeltayev.aqms.utils

import android.content.Context
import android.util.TypedValue
import kotlin.math.roundToInt

fun Context.dpToPx(dp: Float): Int =
        dpToPxF(dp).roundToInt()

fun Context.dpToPx(dp: Int): Int =
        dpToPxF(dp).roundToInt()

fun Context.dpToPxF(dp: Int): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics)

fun Context.dpToPxF(dp: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)

fun Context.spToPxF(sp: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics)

fun Context.spToPx(sp: Float): Int =
        spToPxF(sp).toInt()