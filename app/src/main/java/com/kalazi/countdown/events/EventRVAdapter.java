package com.kalazi.countdown.events;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kalazi.countdown.R;

import java.util.ArrayList;
import java.util.List;

public class EventRVAdapter extends RecyclerView.Adapter<EventItemViewHolder> implements Filterable {
    private List<EventItem> currentDataSet = null;
    private List<EventItem> fullDataSet = null;

    ////// Overrides

    @NonNull
    @Override
    public EventItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemContainerView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_event, parent, false);

        return new EventItemViewHolder(itemContainerView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventItemViewHolder holder, int position) {
        holder.setData(currentDataSet.get(position));
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
            List<EventItem> filtered = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filtered.addAll(fullDataSet);
            } else {
                String searchPattern = constraint.toString().toLowerCase().trim();

                for (EventItem item : fullDataSet) {
                    if (item.title.toLowerCase().contains(searchPattern)) {
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
            currentDataSet = (List<EventItem>) results.values;
            notifyDataSetChanged();
        }
    };

    ////// Public interface methods

    public void updateDataset(List<EventItem> newDataset) {
        // NOTE: Can be optimized using other notify calls
        if (newDataset == null) {
            return;
        }

        this.currentDataSet = newDataset;
        this.fullDataSet = new ArrayList<>(newDataset);
        this.notifyDataSetChanged();
    }

}
