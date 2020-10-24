package com.kalazi.countdown.ui.widgets;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class WidgetItemDiff extends DiffUtil.ItemCallback<WidgetItem> {
    @Override
    public boolean areItemsTheSame(
            @NonNull WidgetItem oldWidget, @NonNull WidgetItem newWidget) {
        // User properties may have changed if reloaded from the DB, but ID is fixed
        Log.v("areItemsTheSame", Boolean.toString(newWidget.getId() == oldWidget.getId()));
        return newWidget.getId() == oldWidget.getId();
    }

    @Override
    public boolean areContentsTheSame(
            @NonNull WidgetItem oldWidget, @NonNull WidgetItem newWidget) {
        // NOTE: if you use equals, your object must properly override Object#equals()
        // Incorrectly returning false here will result in too many animations.
        Log.v("areItemsTheSame", Boolean.toString(oldWidget.equals(newWidget)));
        return oldWidget.equals(newWidget);
    }
}
