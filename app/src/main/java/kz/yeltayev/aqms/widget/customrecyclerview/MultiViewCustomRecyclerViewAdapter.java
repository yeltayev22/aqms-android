package kz.yeltayev.aqms.widget.customrecyclerview;

import java.util.List;

public class MultiViewCustomRecyclerViewAdapter extends CustomRecyclerViewAdapter {

    private List mItems;

    MultiViewCustomRecyclerViewAdapter(List items) {
        super(items, 0);
        mItems = items;
    }

    @Override
    public int getItemLayoutId(int position) {
        int index = getItemIndex(position);
        if (index < mItems.size()) {
            CustomRecyclerViewItem item = (CustomRecyclerViewItem) mItems.get(index);
            return item.getLayoutId();
        } else {
            return super.getItemLayoutId(position);
        }
    }
}
