package kz.yeltayev.aqms

import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager

object Bindings {

    @BindingAdapter("toastMessage")
    @JvmStatic
    fun runMe(view: View, message: String?) {
        if (message != null) {
            Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show()
        }
    }

    @BindingAdapter("viewPager_adapter")
    @JvmStatic
    fun setAdapter(viewPager: ViewPager, adapter: MyAdapter) {
        viewPager.adapter = adapter
        viewPager.currentItem = 0
    }
}