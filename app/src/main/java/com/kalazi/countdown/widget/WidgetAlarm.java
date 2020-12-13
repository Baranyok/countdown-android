package com.kalazi.countdown.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class WidgetAlarm {
    private static final int ALARM_ID = 0;
    private static final int INTERVAL_MILLIS = 60000;

    public static void start(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, INTERVAL_MILLIS);

        Intent alarmIntent = new Intent(context, CountdownWidget.class);
        alarmIntent.setAction(CountdownWidget.ACTION_AUTO_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), INTERVAL_MILLIS, pendingIntent);
    }

    public static void stop(Context context) {
        Intent alarmIntent = new Intent(CountdownWidget.ACTION_AUTO_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
