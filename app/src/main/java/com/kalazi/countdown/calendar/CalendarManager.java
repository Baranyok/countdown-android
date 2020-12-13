package com.kalazi.countdown.calendar;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.kalazi.countdown.events.EventItem;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarManager {

    ////// Projection [EVENTS table]

    public static final String[] EV_PROJECTION = new String[]{
            CalendarContract.Events._ID,            // 0
            CalendarContract.Events.TITLE,          // 1
            CalendarContract.Events.CALENDAR_ID,    // 2
            CalendarContract.Events.DTSTART,        // 3
            CalendarContract.Events.EVENT_TIMEZONE, // 4
    };

    private static final int EV_INDEX_ID = 0;
    private static final int EV_INDEX_TITLE = 1;
    private static final int EV_INDEX_CALENDAR_ID = 2;
    private static final int EV_START = 3;
    private static final int EV_TZ = 4;

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

        if (cursor.moveToNext()) {
            nextInstance = cursor.getLong(INST_INDEX_BEGIN);
//            Log.i("BEGIN", Long.toString(nextInstance));
        }

        cursor.close();

        // try to get the nextInstance as the event start
        if (nextInstance == 0) {
            EventItem event = loadEventFromID(event_id, context);
            if (event != null) {
                nextInstance = event.dt_start;
            }
        }

        return nextInstance;
    }

    public static EventItem loadEventFromID(int id, @NonNull Context context) {
        ContentResolver resolver = context.getContentResolver();
        String selection = CalendarContract.Events._ID + " = ?";
        String[] selectionArgs = new String[]{Integer.toString(id)};

        Cursor cursor;
        cursor = resolver.query(CalendarContract.Events.CONTENT_URI, EV_PROJECTION, selection, selectionArgs, null);

        EventItem eventItem = null;
        if (cursor.moveToNext()) {
            eventItem = new EventItem();
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
        eventItem.title = cursor.getString(EV_INDEX_TITLE).replace('Ŝ', 'Š');
        eventItem.calendar_id = cursor.getInt(EV_INDEX_CALENDAR_ID);
        eventItem.dt_start = cursor.getLong(EV_START);
        eventItem.timezone = cursor.getString(EV_TZ);

//        Log.i("ID", Integer.toString(eventItem.id));
//        Log.i("Title", eventItem.title);
//        Log.i("Calendar id", Integer.toString(eventItem.calendar_id));
//        Log.i("START", Long.toString(eventItem.dt_start));
    }

    ////// Not implemented

    public static void addEvent(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, Instant.now().toEpochMilli())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, Instant.now().toEpochMilli())
                .putExtra(CalendarContract.Events.TITLE, "Event name")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Countdown event")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_TENTATIVE);
        fragment.startActivity(intent);
    }
}
