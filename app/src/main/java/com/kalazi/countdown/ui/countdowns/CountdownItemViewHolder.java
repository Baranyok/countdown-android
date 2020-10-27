package com.kalazi.countdown.ui.countdowns;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.kalazi.countdown.R;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public class CountdownItemViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    private final TextView nameView;
    private final TextView colorView;
    private final TextView opacityView;
    private final TextView fontColorView;

    public CountdownItemViewHolder(View itemContainerView) {
        super(itemContainerView);
        nameView = itemContainerView.findViewById(R.id.ci_name);
        colorView = itemContainerView.findViewById(R.id.ci_color);
        opacityView = itemContainerView.findViewById(R.id.ci_opacity);
        fontColorView = itemContainerView.findViewById(R.id.ci_fontcolor);
    }

    public void setData(CountdownItem countdownItem) {
        nameView.setText(countdownItem.getName());
        colorView.setText("#" + Integer.toHexString(countdownItem.getColor()));
        opacityView.setText(Integer.toString(countdownItem.getOpacity()));
        fontColorView.setText("#" + Integer.toHexString(countdownItem.getFontColor()));
    }
}
