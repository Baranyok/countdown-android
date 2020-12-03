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

/**
 * Represents countdowns as a list (Adapter)<br><br>
 * What this does:<br>
 * - Binds Items to ViewHolders<br>
 * - Updates Individual items on change (onBindViewHolder)<br>
 * - Search filter
 */
public class CountdownsRecyclerViewAdapter extends RecyclerView.Adapter<CountdownItemViewHolder> implements Filterable {
    private List<CountdownItem> currentDataSet = null;
    private List<CountdownItem> fullDataset = null;

    private Fragment parentFragment = null;

    public void setParentFragment(Fragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    ////// Overrides

    /**
     * Create a ViewHolder (inflate layout)
     * -> invoked by the layout manager
     */
    @NonNull
    @Override
    public CountdownItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemContainerView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.countdown_item_row, parent, false);

        return new CountdownItemViewHolder(itemContainerView);
    }

    /**
     * Replace the contents of an item ViewHolder
     * -> invoked by the layout manager
     *
     * @param holder   the ViewHolder of an Item
     * @param position The position of the item within the adapter's data set.
     */
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

    ////// Necessary overrides

    @Override
    public int getItemCount() {
        return (currentDataSet == null) ? 0 : currentDataSet.size();
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    ////// Filter

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

    ////// Public interface methods

    /**
     * Updates the dataset and notifies all items to update
     *
     * @param newDataset new dataset
     */
    public void updateDataset(List<CountdownItem> newDataset) {
        // NOTE: Can be optimized using other notify calls
        this.currentDataSet = newDataset;
        this.fullDataset = new ArrayList<>(newDataset);
        this.notifyDataSetChanged();
    }

}
