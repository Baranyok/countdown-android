package com.kalazi.countdown.ui.widgets;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.kalazi.countdown.R;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public class WidgetItemViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    private TextView txtName;
    private TextView txtDistance;
    private TextView txtGravity;
    private TextView txtDiameter;

    public WidgetItemViewHolder(View itemContainerView) {
        super(itemContainerView);
        txtName = itemContainerView.findViewById(R.id.txtName);
        txtDistance = itemContainerView.findViewById(R.id.txtDistance);
        txtGravity = itemContainerView.findViewById(R.id.txtGravity);
        txtDiameter = itemContainerView.findViewById(R.id.txtDiameter);
    }

    public void setData(WidgetItem widgetItem) {
        txtName.setText(widgetItem.getWidgetName());
    }
}
