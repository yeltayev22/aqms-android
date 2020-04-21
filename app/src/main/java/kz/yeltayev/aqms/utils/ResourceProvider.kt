package kz.yeltayev.aqms.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import kz.yeltayev.aqms.R

class ResourceProvider(val context: Context) {

    private val shortDayNames = mutableListOf<String>()
    private val shortMonthNames = mutableListOf<String>()

    init {
        fillShortDayNames()
        fillShortMonths()
    }

    fun getString(resId: Int): String {
        return context.getString(resId)
    }

    fun getDrawable(resId: Int): Drawable? {
        return AppCompatResources.getDrawable(context, resId)
    }

    fun getColor(resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }

    fun getShortDayName(dayOfWeek: Int): String {
        return shortDayNames[dayOfWeek - 1]
    }

    fun getShortMonthName(monthNumber: Int): String {
        return shortDayNames[monthNumber - 1]
    }

    @SuppressLint("ShowToast")
    fun showToast(text: String) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun fillShortDayNames() {
        shortDayNames.clear()
        shortDayNames.add(getString(R.string.day_short_1))
        shortDayNames.add(getString(R.string.day_short_2))
        shortDayNames.add(getString(R.string.day_short_3))
        shortDayNames.add(getString(R.string.day_short_4))
        shortDayNames.add(getString(R.string.day_short_5))
        shortDayNames.add(getString(R.string.day_short_6))
        shortDayNames.add(getString(R.string.day_short_7))
    }

    private fun fillShortMonths() {
        shortMonthNames.clear()
        shortMonthNames.add(getString(R.string.month_short_01))
        shortMonthNames.add(getString(R.string.month_short_02))
        shortMonthNames.add(getString(R.string.month_short_03))
        shortMonthNames.add(getString(R.string.month_short_04))
        shortMonthNames.add(getString(R.string.month_short_05))
        shortMonthNames.add(getString(R.string.month_short_06))
        shortMonthNames.add(getString(R.string.month_short_07))
        shortMonthNames.add(getString(R.string.month_short_08))
        shortMonthNames.add(getString(R.string.month_short_09))
        shortMonthNames.add(getString(R.string.month_short_10))
        shortMonthNames.add(getString(R.string.month_short_11))
        shortMonthNames.add(getString(R.string.month_short_12))
    }
}