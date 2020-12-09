package com.kalazi.countdown.events;

import android.app.Activity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.kalazi.countdown.calendar.CalendarManager;

import java.util.List;

public class EventListViewModel extends ViewModel {

    private List<EventItem> eventList = null;
    private final MutableLiveData<List<EventItem>> events = new MutableLiveData<>(null);

    // TODO: Implement the ViewModel

    public boolean load(Activity context) {
        if (context == null) {
            return false;
        }

        try {
            eventList = CalendarManager.loadEvents(context);
            events.setValue(eventList);
        } catch (SecurityException e) {
            return false;
        }

        return true;
    }

    public LiveData<List<EventItem>> getEvents() {
        return events;
    }
}
