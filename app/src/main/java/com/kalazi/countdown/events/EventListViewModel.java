package com.kalazi.countdown.events;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.preference.PreferenceManager;
import com.kalazi.countdown.calendar.CalendarManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventListViewModel extends ViewModel {

    private List<EventItem> eventList = null;
    private final MutableLiveData<List<EventItem>> events = new MutableLiveData<>(null);

    public boolean load(Activity context) {
        if (context == null) {
            return false;
        }

        try {
            eventList = CalendarManager.loadEvents(context, getIds(context));
            events.setValue(eventList);
        } catch (SecurityException e) {
            return false;
        }

        return true;
    }

    public LiveData<List<EventItem>> getEvents() {
        return events;
    }

    ////// Private utility methods

    private Set<Integer> getIds(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> baseSet = prefs.getStringSet("enabled_calendars", new HashSet<>());
        return baseSet.stream().map(Integer::parseInt).collect(Collectors.toSet());
    }
}
