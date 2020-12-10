package com.kalazi.countdown.calendar;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.kalazi.countdown.events.EventItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarManager {

    ////// Projection [EVENTS table]

    public static final String[] EV_PROJECTION = new String[]{
            CalendarContract.Events._ID,            // 0
            CalendarContract.Events.TITLE,          // 1
            CalendarContract.Events.CALENDAR_ID,    // 2
    };

    private static final int EV_INDEX_ID = 0;
    private static final int EV_INDEX_TITLE = 1;
    private static final int EV_INDEX_CALENDAR_ID = 2;

    ////// Projection [INSTANCES table]

    public static final String[] INST_PROJECTION = new String[]{
            CalendarContract.Instances._ID,         // 0
            CalendarContract.Instances.BEGIN,       // 1
    };

    private static final int INST_INDEX_ID = 0;
    private static final int INST_INDEX_BEGIN = 1;

    ////// Event loading

    public static long getNextInstance(int event_id, @NonNull Context context) {
        long nextInstance = 0;

        ContentResolver resolver = context.getContentResolver();
        String selection = CalendarContract.Instances.EVENT_ID + " = ?";
        String[] selectionArgs = new String[]{Integer.toString(event_id)};

        long startMillis = Calendar.getInstance().getTimeInMillis();
        long endMillis = Long.MAX_VALUE;
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        Cursor cursor;
        cursor = resolver.query(builder.build(), INST_PROJECTION, selection, selectionArgs, null);

        while (cursor.moveToNext()) {
            nextInstance = cursor.getLong(INST_INDEX_BEGIN);
        }

        cursor.close();

        return nextInstance;
    }

    public static EventItem loadEventFromID(int id, @NonNull Context context) {
        EventItem eventItem = new EventItem();

        ContentResolver resolver = context.getContentResolver();
        String selection = CalendarContract.Events._ID + " = ?";
        String[] selectionArgs = new String[]{Integer.toString(id)};

        Cursor cursor;
        cursor = resolver.query(CalendarContract.Events.CONTENT_URI, EV_PROJECTION, selection, selectionArgs, null);

        while (cursor.moveToNext()) {
            cursorToEventItem(cursor, eventItem);
        }

        cursor.close();

        return eventItem;
    }

    public static List<EventItem> loadEvents(@NonNull Context context) {
        List<EventItem> events = new ArrayList<>();

        ContentResolver resolver = context.getContentResolver();

        Cursor cursor;
        cursor = resolver.query(CalendarContract.Events.CONTENT_URI, EV_PROJECTION, null, null, null);

        while (cursor.moveToNext()) {
            EventItem eventItem = new EventItem();

            cursorToEventItem(cursor, eventItem);

            events.add(eventItem);
        }

        cursor.close();

        return events;
    }

    ////// Event loading - private

    private static void cursorToEventItem(Cursor cursor, EventItem eventItem) {
        eventItem.id = cursor.getInt(EV_INDEX_ID);
        eventItem.title = cursor.getString(EV_INDEX_TITLE);
        eventItem.calendar_id = cursor.getInt(EV_INDEX_CALENDAR_ID);

        Log.i("ID", Integer.toString(eventItem.id));
        Log.i("Title", eventItem.title);
        Log.i("Calendar id", Integer.toString(eventItem.calendar_id));
    }

    ////// Not implemented

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
