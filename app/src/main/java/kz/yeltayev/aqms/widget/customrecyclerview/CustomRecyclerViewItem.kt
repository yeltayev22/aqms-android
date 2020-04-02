package kz.yeltayev.aqms.widget.customrecyclerview

import androidx.annotation.LayoutRes

interface CustomRecyclerViewItem {
    @LayoutRes
    fun getLayoutId(): Int
}
