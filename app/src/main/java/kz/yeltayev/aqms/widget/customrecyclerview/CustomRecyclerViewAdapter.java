package kz.yeltayev.aqms.widget.customrecyclerview;

import androidx.annotation.LayoutRes;
import androidx.databinding.ViewDataBinding;

import java.util.List;

import kz.yeltayev.aqms.BR;

public class CustomRecyclerViewAdapter extends BaseDataBoundAdapter<ViewDataBinding>
		implements CustomRecyclerViewAdapterContract {

	private int itemLayoutId;
	protected List items;
	private Object viewModel;

	private boolean isLoading;

	CustomRecyclerViewAdapter(List items, @LayoutRes int itemLayoutId) {
		this.items = items;
		this.itemLayoutId = itemLayoutId;
	}

	@Override
	public void setViewModel(Object viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public int getItemLayoutId(int position) {

		int index = getItemIndex(position);

		if (itemLayoutId != 0) {
			return itemLayoutId;
		} else {
			CustomRecyclerViewItem item = (CustomRecyclerViewItem) items.get(index);
			return item.getLayoutId();
		}
	}

	@Override
	protected void bindItem(DataBoundViewHolder<ViewDataBinding> holder, int position, List<Object> payloads) {
		int index = getItemIndex(position);
		if (viewModel != null) {
			holder.binding.setVariable(BR.vm, viewModel);
		}
		holder.binding.setVariable(BR.item, items.get(index));
	}

	protected int getItemIndex(int adapterPosition) {
		return adapterPosition;
	}

	@Override
	public int getItemCount() {
		int increment = 0;
		if (isLoading) {
			// Accounting for the loading spinner.
			increment++;
		}

		if (items != null) {
			return items.size() + increment;
		} else {
			return 0;
		}
	}

	@Override
	public void setLoading(boolean loading) {
		if (isLoading != loading) {
			isLoading = loading;
			if (loading) {
				notifyItemInserted(items.size());
			} else {
				notifyItemRemoved(items.size());
			}
		}
	}
}
