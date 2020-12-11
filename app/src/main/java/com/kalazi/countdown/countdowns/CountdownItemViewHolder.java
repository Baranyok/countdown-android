package com.kalazi.countdown.countdowns;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.kalazi.countdown.R;
import com.kalazi.countdown.calendar.CalendarManager;
import com.kalazi.countdown.events.EventItem;
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
    private final TextView eventNameView;
    private final TextView remainingTimeView;

    private final TextView whenView;
    private final TextView eventStaticView;

    private CountdownItem countdownItem;
    private EventItem eventItem;
    private long nextInstance = 0;

    public CountdownItemViewHolder(View itemContainerView) {
        super(itemContainerView);
        nameView = itemContainerView.findViewById(R.id.ci_title);
        colorView = itemContainerView.findViewById(R.id.ci_color);
        eventNameView = itemContainerView.findViewById(R.id.ci_event_name);
        remainingTimeView = itemContainerView.findViewById(R.id.ci_remaining_time);
        whenView = itemContainerView.findViewById(R.id.ci_since_until);
        eventStaticView = itemContainerView.findViewById(R.id.ci_event_static);
    }

    /**
     * Updates the View elements according to the give Item
     *
     * @param countdownItem updated Item
     */
    // TODO: bind instead?
    public void setData(CountdownItem countdownItem) {
        this.countdownItem = countdownItem;
        loadEvent();

        nameView.setText(countdownItem.getName());
        colorView.setText("#" + Integer.toHexString(countdownItem.getColor()));
        eventNameView.setText((eventItem == null) ? "" : eventItem.title); // TODO: resource

        loadNextInstanceTime();
        updateDisplayedTime();
        startUpdateHandler();

        hideEventIfNameIsSame();
    }

    private void hideEventIfNameIsSame() {
        if (eventItem == null || countdownItem == null) {
            return;
        }

        if (eventItem.title.equals(countdownItem.getName())) {
            eventStaticView.setVisibility(View.GONE);
            eventNameView.setVisibility(View.GONE);
        }
    }

    private void loadEvent() {
        eventItem = CalendarManager.loadEventFromID(countdownItem.eventID, itemView.getContext());
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

        whenView.setText((DateConverter.isInFuture(nextInstance)) ? "Until" : "Since"); // TODO: Resource
    }
}
