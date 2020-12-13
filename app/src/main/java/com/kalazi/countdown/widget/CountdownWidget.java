package com.kalazi.countdown.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.kalazi.countdown.R;
import com.kalazi.countdown.calendar.CalendarManager;
import com.kalazi.countdown.countdowns.CountdownItem;
import com.kalazi.countdown.countdowns.CountdownItemViewHolder;
import com.kalazi.countdown.database.CountdownRepository;
import com.kalazi.countdown.events.EventItem;
import com.kalazi.countdown.util.ColorConverter;
import com.kalazi.countdown.util.DateConverter;

import java.time.Instant;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link CountdownWidgetConfigureActivity CountdownWidgetConfigureActivity}
 */
public class CountdownWidget extends AppWidgetProvider {

    public static final String ACTION_AUTO_UPDATE = "AUTO_UPDATE";
    private static final String TAG = "CountdownWidget";

    ////// Overrides

    // receive custom update intent from AlarmManager
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getAction().equals(ACTION_AUTO_UPDATE)) {
            Log.i(TAG, Instant.now().toString());
            Log.i(TAG, "received");
            updateCountdownWidgets(context);
        }
    }

    @Override
    public void onDisabled(Context context) {
        Log.i(TAG, "disabled");
        // disable alarm when all of the widgets are removed
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidgetComponentName = new ComponentName(context.getPackageName(), getClass().getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName);
        if (appWidgetIds.length == 0) {
            WidgetAlarm.stop(context);
        }
    }

    ////// Private utility methods

    public static void updateCountdownWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.i(TAG, "update");
        int countdownId = CountdownWidgetConfigureActivity.loadPersistentPref(context, appWidgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.countdown_widget);

        if (countdownId == -1) {
            remoteViews.setTextViewText(R.id.ci_remaining_time, "Invalid countdown ID");
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
            return;
        }

        CountdownRepository repository = new CountdownRepository(context);
        LiveData<CountdownItem> countdown = repository.getById(countdownId);

        Observer<CountdownItem> observer = new Observer<CountdownItem>() {
            @Override
            public void onChanged(CountdownItem countdownItem) {
                if (countdownItem == null) {
                    remoteViews.setTextViewText(R.id.ci_remaining_time, "Invalid database access");
                    appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
                    return;
                }

                // calculate times
                EventItem eventItem = null;
                long nextInstance = 0;
                try {
                    eventItem = CalendarManager.loadEventFromID(countdownItem.eventID, context);
                    nextInstance = CalendarManager.getNextInstance(countdownItem.eventID, context);
                } catch (SecurityException ignored) {

                }

                // things dependent on event
                if (eventItem != null) {
                    DateConverter dateConverter = new DateConverter(context.getResources());
                    String remTime = dateConverter.timeDifferenceToFormattedString(nextInstance, eventItem.timezone, false);
                    remoteViews.setTextViewText(R.id.ci_remaining_time, remTime);

                    String since = (DateConverter.isInFuture(nextInstance, eventItem.timezone)) ? "Until" : "Since"; // TODO: Resource
                    remoteViews.setTextViewText(R.id.ci_since_until, since);

                    //
                    if (countdownItem.showEventName) {
                        remoteViews.setTextViewText(R.id.ci_event_name, eventItem.title);
                    } else {
                        remoteViews.setViewVisibility(R.id.ci_event_name, View.GONE);
                        remoteViews.setViewVisibility(R.id.ci_event_static, View.GONE);
                    }
                } else {
                    remoteViews.setViewVisibility(R.id.ci_event_name, View.GONE);
                    remoteViews.setViewVisibility(R.id.ci_event_static, View.GONE);
                    remoteViews.setTextViewText(R.id.ci_remaining_time, "N/A");
                }

                // set title
                if (!"".equals(countdownItem.title)) {
                    remoteViews.setTextViewText(R.id.ci_title, countdownItem.title);
                } else {
                    remoteViews.setViewVisibility(R.id.ci_title, View.GONE);
                    remoteViews.setViewVisibility(R.id.ci_since_until, View.GONE);
                }

                // set colors

                remoteViews.setTextColor(R.id.ci_remaining_time, countdownItem.fontColor);
                remoteViews.setTextColor(R.id.ci_title, countdownItem.fontColor);
                remoteViews.setTextColor(R.id.ci_event_name, countdownItem.fontColor);

                int lightColor = ColorConverter.combineColorOpacity(countdownItem.fontColor, CountdownItemViewHolder.labelOpacity);
                remoteViews.setTextColor(R.id.ci_event_static, lightColor);
                remoteViews.setTextColor(R.id.ci_since_until, lightColor);

                // update widget

                remoteViews.setInt(R.id.ci_immersed_layout, "setBackgroundColor", countdownItem.color);

                remoteViews.setOnClickPendingIntent(R.id.ci_immersed_layout,
                        getPendingSelfIntent(context, ACTION_AUTO_UPDATE));

                appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
                countdown.removeObserver(this);
            }
        };

        countdown.observeForever(observer);
    }

    private static PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, CountdownWidget.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public void updateCountdownWidgets(Context context) {
        Log.i(TAG, "pre-update");

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidgetComponentName = new ComponentName(context.getPackageName(), getClass().getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName);

        for (int widgetId : appWidgetIds) {
            updateCountdownWidget(context, appWidgetManager, widgetId);
        }
    }

    ////// Old code

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.i(TAG, "restart");
        updateCountdownWidgets(context);
        WidgetAlarm.restart(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            CountdownWidgetConfigureActivity.deletePersistentPref(context, appWidgetId);
        }
    }
}

