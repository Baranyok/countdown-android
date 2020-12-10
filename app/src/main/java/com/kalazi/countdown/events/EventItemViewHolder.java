package com.kalazi.countdown.events;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kalazi.countdown.R;

public class EventItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView titleView;
    private final TextView calendarIdView;
    private EventItem eventItem;

    public EventItemViewHolder(@NonNull View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.ei_title);
        calendarIdView = itemView.findViewById(R.id.ei_cal_id);
    }

    public void setData(EventItem eventItem) {
        this.eventItem = eventItem;
        titleView.setText(eventItem.title);
        calendarIdView.setText(Integer.toString(eventItem.calendar_id));
    }

    public EventItem getEventItem() {
        return eventItem;
    }
}
