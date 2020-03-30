package kz.yeltayev.aqms

import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem

object Bindings {

    @BindingAdapter("toastMessage")
    @JvmStatic
    fun runMe(view: View, message: String?) {
        if (message != null) {
            Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show()
        }
    }

    @BindingAdapter("bottomNavigation_items")
    @JvmStatic
    fun addBottomNavigationItems(view: BottomNavigationBar, items: List<BottomNavigationItem>) {
        items.forEach { item ->
            view.addItem(item)
        }
        view.setFirstSelectedPosition(0).initialise()
    }
}