package kz.yeltayev.aqms.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

class ResourceProvider (val context: Context) {

    fun getString(resId: Int): String {
        return context.getString(resId)
    }

    fun getDrawable(resId: Int): Drawable? {
        return AppCompatResources.getDrawable(context, resId)
    }

    fun getColor(resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }
}