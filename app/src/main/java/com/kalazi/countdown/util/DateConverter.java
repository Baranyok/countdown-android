package com.kalazi.countdown.util;

import android.util.Log;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateConverter {

    boolean hit = false;

    /**
     * Calculates and formats the time delta value from now until <code>nextTime</code>
     *
     * @param nextTime next occurrence in reference to which the time delta will be calculated
     * @return formatted string
     */
    public String timeDeltaToString(long nextTime) {
        if (nextTime <= 0) {
            // TODO implement count-up
            return "Negative or zero";
        }

        String retString = "";

        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar epoch = Calendar.getInstance(timeZone);
        epoch.setTimeInMillis(0);
        Calendar dateDelta = Calendar.getInstance(timeZone);
        dateDelta.setTimeInMillis(nextTime - Calendar.getInstance(timeZone).getTimeInMillis());

        long delta = (nextTime - Calendar.getInstance(timeZone).getTimeInMillis()) / 1000;

        Log.i("next", Long.toString(nextTime));
        Log.i("now", Long.toString(Calendar.getInstance(timeZone).getTimeInMillis()));

        long seconds = delta % 60;
        int minutes = dateDelta.get(Calendar.MINUTE) - epoch.get(Calendar.MINUTE);
        int hours = dateDelta.get(Calendar.HOUR_OF_DAY) - epoch.get(Calendar.HOUR_OF_DAY);
        int days = dateDelta.get(Calendar.DAY_OF_MONTH) - epoch.get(Calendar.DAY_OF_MONTH);
        int months = dateDelta.get(Calendar.MONTH) - epoch.get(Calendar.MONTH);
        int years = dateDelta.get(Calendar.YEAR) - epoch.get(Calendar.YEAR);

        retString += formatVal("%d Years, ", years);
        retString += formatVal("%d Months, ", months);
        retString += formatVal("%d Days, ", days);
        retString += formatVal("%d Hours, ", hours);
        retString += formatVal("%d Minutes, ", minutes);
        retString += formatVal("%d Seconds", seconds);

        return retString;
    }

    private String formatVal(String pattern, long value) {
        if (hit || value > 0) {
            hit = true;
            return String.format(Locale.US, pattern, value);
        }
        return "";
    }

}
