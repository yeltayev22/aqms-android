package kz.yeltayev.aqms.widget.customrecyclerview;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomRecyclerViewBindings {
    @BindingAdapter("rv_items")
    public static void setItems(CustomRecyclerView customRecyclerView, List items) {
        customRecyclerView.setItems(items);
    }

    @BindingAdapter("rv_viewModel")
    public static void setViewModel(CustomRecyclerView customRecyclerView, Object viewModel) {
        customRecyclerView.setViewModel(viewModel);
    }

    @BindingAdapter("rv_endlessScroll")
    public static void addEndlessScroll(CustomRecyclerView customRecyclerView, Runnable onScroll) {
        customRecyclerView.addEndlessScroll(onScroll);
    }

    @BindingAdapter("rv_resetEndlessScroll")
    public static void resetEndlessScrollState(CustomRecyclerView customRecyclerView, boolean unused) {
        customRecyclerView.resetEndlessScroll();
    }

    @BindingAdapter("rv_loading")
    public static void setEndlessScrollLoading(CustomRecyclerView customRecyclerView, boolean loading) {
        customRecyclerView.setLoading(loading);
    }

    @BindingAdapter("rv_itemDecoration")
    public static void addItemDecoration(CustomRecyclerView customRecyclerView, RecyclerView.ItemDecoration itemDecoration) {
        customRecyclerView.addItemDecoration(itemDecoration);
    }
}
