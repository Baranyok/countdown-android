package com.kalazi.countdown.ui.widgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.ListAdapter;
import com.kalazi.countdown.R;

import java.util.ArrayList;

public class WidgetsRecyclerViewAdapter extends ListAdapter<WidgetItem, WidgetItemViewHolder> {
    private ArrayList<String> mDataset;
    public static final WidgetItemDiff DIFF_CALLBACK = new WidgetItemDiff();

    //    public WidgetsRecyclerViewAdapter(ArrayList<String> myDataset) {
//        mDataset = myDataset;
//    }
    public WidgetsRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }

    // Create new item views (invoked by the layout manager)
    @Override
    public WidgetItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemContainerView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.widgets_item_row, parent, false);

        return new WidgetItemViewHolder(itemContainerView);
    }

    // Replace the contents of an item view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(WidgetItemViewHolder holder, int position) {
        holder.setData(getItem(position));
    }

}
