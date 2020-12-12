package com.kalazi.countdown.countdowns;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.kalazi.countdown.R;
import com.kalazi.countdown.calendar.CalendarManager;
import com.kalazi.countdown.events.EventItem;
import com.kalazi.countdown.util.ColorConverter;
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

    private final CardView cardView;

    private CountdownItem countdownItem;
    private EventItem eventItem;
    private long nextInstance = 0;

    private final int labelOpacity = 80;

    private volatile boolean isCounting = false;

    public CountdownItemViewHolder(View itemContainerView) {
        super(itemContainerView);
        nameView = itemContainerView.findViewById(R.id.ci_title);
        colorView = itemContainerView.findViewById(R.id.ci_color);
        eventNameView = itemContainerView.findViewById(R.id.ci_event_name);
        remainingTimeView = itemContainerView.findViewById(R.id.ci_remaining_time);
        whenView = itemContainerView.findViewById(R.id.ci_since_until);
        eventStaticView = itemContainerView.findViewById(R.id.ci_event_static);
        cardView = itemContainerView.findViewById(R.id.ci_card_view);
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

        nameView.setText(countdownItem.title);
        colorView.setText("#" + Integer.toHexString(countdownItem.color));
        eventNameView.setText((eventItem == null) ? "" : eventItem.title); // TODO: resource

        loadNextInstanceTime();
        updateDisplayedTime();
        startUpdateHandler();

        hideEventIfNameIsSame();
        customizeView();
    }

    private void customizeView() {
        // set text color (foreground)
        remainingTimeView.setTextColor(countdownItem.fontColor);
        nameView.setTextColor(countdownItem.fontColor);
        eventNameView.setTextColor(countdownItem.fontColor);

        // determine and set the label colors
        whenView.setTextColor(ColorConverter.combineColorOpacity(countdownItem.fontColor, labelOpacity));
        eventStaticView.setTextColor(ColorConverter.combineColorOpacity(countdownItem.fontColor, labelOpacity));

        // set background color
        cardView.setCardBackgroundColor(countdownItem.color);
    }

    private void hideEventIfNameIsSame() {
        if (countdownItem == null) {
            return;
        }

        if ("".equals(countdownItem.title)) {
            whenView.setVisibility(View.GONE);
        }

        // TODO: Add setting to apply || eventItem.title.equals(countdownItem.getName())
        if (eventItem == null || !countdownItem.showEventName) {
            eventStaticView.setVisibility(View.GONE);
            eventNameView.setVisibility(View.GONE);
        }
    }

    private void loadEvent() {
        try {
            eventItem = CalendarManager.loadEventFromID(countdownItem.eventID, itemView.getContext());
        } catch (SecurityException ignored) {

        }
    }

    public void startUpdateHandler() {
        isCounting = true;

        final Handler handler = new Handler(Looper.getMainLooper());
        final int delay = 1000; // 1000 milliseconds == 1 second

        handler.postDelayed(new Runnable() {
            public void run() {
                updateDisplayedTime();
                if (isCounting) {
                    handler.postDelayed(this, delay);
                }
            }
        }, delay);

    }

    public void stopUpdateHandler() {
        isCounting = false;
    }

    private void loadNextInstanceTime() {
        try {
            nextInstance = CalendarManager.getNextInstance(countdownItem.eventID, itemView.getContext());
        } catch (SecurityException ignored) {

        }
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
        remainingTimeView.setText(dateConverter.timeDifferenceToFormattedString(nextInstance, eventItem.timezone));

        whenView.setText((DateConverter.isInFuture(nextInstance, eventItem.timezone)) ? "Until" : "Since"); // TODO: Resource
    }
}
