package kz.yeltayev.aqms.widget.customrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kz.yeltayev.aqms.R;

public class CustomRecyclerView extends RecyclerView {

    private static final int DEFAULT_SMOOTH_SCROLL_SPEED = 400;

    private static final int ORIENTATION_VERTICAL = 1;
    private static final int ORIENTATION_HORIZONTAL = 2;

    protected List items = new ArrayList();
    protected Adapter adapter;
    protected int itemLayoutId;

    private DividerItemDecoration mItemDecoration;

    private Runnable mOnScroll;
    private EndlessRecyclerViewScrollListener mEndlessScrollListener;

    private List<Function0<Unit>> mDataChangeListeners = new ArrayList<>();

    private CustomLinearLayoutManager layoutManager;

    public CustomRecyclerView(Context context) {
        this(context, null);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        int dividerDrawableId;
        boolean scrollable;
        boolean multiType;
        int orientation;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomRecyclerView, 0, 0);
        try {
            itemLayoutId = a.getResourceId(R.styleable.CustomRecyclerView_rv_itemLayout, 0);
            dividerDrawableId = a.getResourceId(R.styleable.CustomRecyclerView_rv_dividerDrawable, 0);
            scrollable = a.getBoolean(R.styleable.CustomRecyclerView_rv_scrollable, true);
            multiType = a.getBoolean(R.styleable.CustomRecyclerView_rv_multiType, false);
            orientation = a.getInt(R.styleable.CustomRecyclerView_rv_orientation, ORIENTATION_VERTICAL);
        } finally {
            a.recycle();
        }

        int layoutOrientation = orientation == ORIENTATION_VERTICAL
                ? LinearLayoutManager.VERTICAL
                : LinearLayoutManager.HORIZONTAL;

        layoutManager = new CustomLinearLayoutManager(
                context,
                DEFAULT_SMOOTH_SCROLL_SPEED,
                layoutOrientation,
                false
        );

        setNestedScrollingEnabled(scrollable);
        if (!scrollable) {
            layoutManager.setAutoMeasureEnabled(true);
        }

        setLayoutManager(layoutManager);

        if (orientation == ORIENTATION_VERTICAL) {
            mItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);

            if (dividerDrawableId != 0) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), dividerDrawableId);
                if (drawable != null) {
                    mItemDecoration.setDrawable(drawable);
                }
            }
            addItemDecoration(mItemDecoration);
        }

        mEndlessScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (mOnScroll != null) {
                    mOnScroll.run();
                }
            }
        };

        if (!(this instanceof HasCustomRecyclerViewAdapter)) {
            CustomRecyclerViewAdapter adapter = null;
            if (multiType) {
                adapter = new MultiViewCustomRecyclerViewAdapter(items);
            } else if (itemLayoutId != 0) {
                adapter = new CustomRecyclerViewAdapter(items, itemLayoutId);
            }

            if (adapter != null) {
                setAdapter(adapter);
            }
        }
    }

    public void addDataChangeListener(Function0<Unit> dataChangeListener) {
        mDataChangeListeners.add(dataChangeListener);
    }

    public void smoothScrollToPositionAtVelocity(int position, int dpPerSecond) {
        layoutManager.setSmoothScrollSpeed(dpPerSecond);
        smoothScrollToPosition(position);
        layoutManager.setSmoothScrollSpeed(DEFAULT_SMOOTH_SCROLL_SPEED);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        this.adapter = adapter;
    }

    @SuppressWarnings("unchecked")
    public void setItems(List items) {
        this.items.clear();
        if (items != null) {
            this.items.addAll(items);
        }
        adapter.notifyDataSetChanged();

        scheduleAnimation();

        invokeDataChangeListeners();
    }

    public void clear() {
        items.clear();
        adapter.notifyDataSetChanged();
        scheduleAnimation();
        invokeDataChangeListeners();
    }

    @SuppressWarnings("unchecked")
    public void addItems(List items) {
        if (items != null && items.size() > 0) {
            int oldSize = this.items.size();
            this.items.addAll(items);
            adapter.notifyItemRangeInserted(oldSize, items.size());

            if (oldSize == 0) {
                scheduleAnimation();
            }

            invokeDataChangeListeners();
        }
    }

    private void invokeDataChangeListeners() {
        for (Function0<Unit> callback : mDataChangeListeners) {
            callback.invoke();
        }
    }

    private void scheduleAnimation() {
        LayoutAnimationController layoutAnimation = getLayoutAnimation();
        if (layoutAnimation != null) {
            scheduleLayoutAnimation();
        }
    }

    public void setViewModel(Object viewModel) {
        if (adapter instanceof CustomRecyclerViewAdapterContract) {
            ((CustomRecyclerViewAdapterContract) adapter).setViewModel(viewModel);
        }
    }

    public void addEndlessScroll(Runnable onScroll) {
        mOnScroll = onScroll;
        if (mOnScroll == null) {
            removeOnScrollListener(mEndlessScrollListener);
        } else {
            addOnScrollListener(mEndlessScrollListener);
        }
    }

    public void resetEndlessScroll() {
        mEndlessScrollListener.resetState();
    }

    void setLoading(boolean loading) {
        if (adapter instanceof CustomRecyclerViewAdapterContract) {
            ((CustomRecyclerViewAdapterContract) adapter).setLoading(loading);
        }
    }

    protected void removeDividerDecoration() {
        if (mItemDecoration != null) {
            removeItemDecoration(mItemDecoration);
        }
    }
}
