package com.kalazi.countdown.countdowns;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.kalazi.countdown.R;

import java.util.ArrayList;
import java.util.List;

public class CountdownsRecyclerViewAdapter extends RecyclerView.Adapter<CountdownItemViewHolder> implements Filterable {
    private List<CountdownItem> currentDataSet = null;
    private List<CountdownItem> fullDataset = null;

    private Fragment parentFragment = null;

    public void setParentFragment(Fragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    // Create new item views (invoked by the layout manager)
    @NonNull
    @Override
    public CountdownItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemContainerView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.countdown_item_row, parent, false);

        return new CountdownItemViewHolder(itemContainerView);
    }

    public void updateDataset(List<CountdownItem> mDataset) {
        // NOTE: Can be optimized using other notify calls
        this.currentDataSet = mDataset;
        this.fullDataset = new ArrayList<>(mDataset);
        this.notifyDataSetChanged();
    }

    // Replace the contents of an item view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CountdownItemViewHolder holder, int position) {
        holder.setData(currentDataSet.get(position));

        // set onClick listener
        if (parentFragment != null) {
            CountdownsFragmentDirections.ActionEditCountdown action =
                    CountdownsFragmentDirections.actionEditCountdown();
            action.setCountdownArrayIndex(position);
            holder.itemView.setOnClickListener(v -> NavHostFragment.findNavController(parentFragment)
                    .navigate(action));
        }
    }

    // Return the size of dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (currentDataSet == null) ? 0 : currentDataSet.size();
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    private final Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CountdownItem> filtered = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filtered.addAll(fullDataset);
            } else {
                String searchPattern = constraint.toString().toLowerCase().trim();

                for (CountdownItem item : fullDataset) {
                    if (item.getName().toLowerCase().contains(searchPattern)) {
                        filtered.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filtered;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            currentDataSet = (List<CountdownItem>) results.values;
            notifyDataSetChanged();
        }
    };

}
