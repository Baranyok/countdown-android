package com.kalazi.countdown.ui.widgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.kalazi.countdown.R;

import java.util.ArrayList;

public class WidgetsRecyclerViewAdapter extends RecyclerView.Adapter<WidgetItemViewHolder> {
    private ArrayList<WidgetItem> mDataset = null;

//    public WidgetsRecyclerViewAdapter(ArrayList<WidgetItem> mDataset) {
//        this.mDataset = mDataset;
//    }

    // Create new item views (invoked by the layout manager)
    @Override
    public WidgetItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemContainerView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.widgets_item_row, parent, false);

        return new WidgetItemViewHolder(itemContainerView);
    }

    public void updateDataset(ArrayList<WidgetItem> mDataset) {
        // NOTE: Can be optimized using other notify calls
        this.mDataset = mDataset;
        this.notifyDataSetChanged();
    }

    // Replace the contents of an item view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(WidgetItemViewHolder holder, int position) {
        holder.setData(mDataset.get(position));
    }

    // Return the size of dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (mDataset == null) ? 0 : mDataset.size();
    }
}
