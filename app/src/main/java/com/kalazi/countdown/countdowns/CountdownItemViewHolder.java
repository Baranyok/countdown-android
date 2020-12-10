package com.kalazi.countdown.countdowns;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.kalazi.countdown.R;
import com.kalazi.countdown.calendar.CalendarManager;
import com.kalazi.countdown.util.DateConverter;

import java.text.DateFormat;
import java.util.Date;

/**
 * Represents an Item View in the UI<br>
 * One ViewHolder references exactly one item
 */
public class CountdownItemViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    private final TextView nameView;
    private final TextView colorView;
    private final TextView opacityView;
    private final TextView remainingTimeView;
    private final TextView whenView;

    private CountdownItem countdownItem;
    private long nextInstance = 0;

    public CountdownItemViewHolder(View itemContainerView) {
        super(itemContainerView);
        nameView = itemContainerView.findViewById(R.id.ci_name);
        colorView = itemContainerView.findViewById(R.id.ci_color);
        opacityView = itemContainerView.findViewById(R.id.ci_opacity);
        remainingTimeView = itemView.findViewById(R.id.ci_remaining_time);
        whenView = itemView.findViewById(R.id.ci_since_until);
    }

    /**
     * Updates the View elements according to the give Item
     *
     * @param countdownItem updated Item
     */
    // TODO: bind instead?
    public void setData(CountdownItem countdownItem) {
        this.countdownItem = countdownItem;
        nameView.setText(countdownItem.getName());
        colorView.setText("#" + Integer.toHexString(countdownItem.getColor()));
        opacityView.setText(Integer.toString(countdownItem.getOpacity()));

        loadNextInstanceTime();
        updateDisplayedTime();
        startUpdateHandler();
    }

    private void startUpdateHandler() {
        final Handler handler = new Handler(Looper.getMainLooper());
        final int delay = 1000; // 1000 milliseconds == 1 second

        handler.postDelayed(new Runnable() {
            public void run() {
                updateDisplayedTime();
                if (itemView.isShown()) {
                    handler.postDelayed(this, delay);
                }
            }
        }, delay);

    }

    private void loadNextInstanceTime() {
        nextInstance = CalendarManager.getNextInstance(countdownItem.eventID, itemView.getContext());
    }

    private void updateDisplayedTime() {
        if (nextInstance == 0) {
            // TODO
            remainingTimeView.setText("N/A");
            return;
        }

        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        remainingTimeView.setText(dateFormat.format(new Date(nextInstance)));

        DateConverter dateConverter = new DateConverter();
        remainingTimeView.setText(dateConverter.timeDifferenceToString(nextInstance));

        whenView.setText((dateConverter.isInFuture(nextInstance)) ? "Until" : "Since"); // TODO: Resource
    }
}
