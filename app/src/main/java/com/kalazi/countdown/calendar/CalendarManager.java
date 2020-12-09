package com.kalazi.countdown.calendar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.CalendarContract;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.kalazi.countdown.events.CalendarEventItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarManager {

    // Projection
    public static final String[] PROJECTION = new String[]{
            CalendarContract.Events.TITLE,          // 0
            CalendarContract.Events.CALENDAR_ID,    // 1
    };

    private static final int INDEX_TITLE = 0;
    private static final int INDEX_CALENDAR_ID = 1;

    // event loading

    public static List<CalendarEventItem> loadEvents(@NonNull Activity context) {
        List<CalendarEventItem> events = new ArrayList<>();

        ContentResolver resolver = context.getContentResolver();

        Cursor cursor;

        cursor = resolver.query(CalendarContract.Events.CONTENT_URI, PROJECTION, null, null, null);

        while (cursor.moveToNext()) {
            CalendarEventItem eventItem = new CalendarEventItem();

            eventItem.title = cursor.getString(INDEX_TITLE);
            eventItem.calendar_id = cursor.getInt(INDEX_CALENDAR_ID);

            Log.i("Title", eventItem.title);
            Log.i("Calendar id", Integer.toString(eventItem.calendar_id));

            events.add(eventItem);
        }

        cursor.close();

        return events;
    }

    public static void addEvent(Fragment fragment) {
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2012, 0, 19, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2012, 0, 19, 8, 30);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Yoga")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Group class")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
        fragment.startActivity(intent);
    }
}
