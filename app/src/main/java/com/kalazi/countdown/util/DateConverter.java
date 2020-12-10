package com.kalazi.countdown.util;

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

        long delta = nextTime - Calendar.getInstance(timeZone).getTimeInMillis();
        if (delta == 0) {
            return "NOW"; // TODO: Make a resource
        } else if (delta < 0) {
            delta = -delta;
        }

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long days = delta / daysInMilli;
        delta = delta % daysInMilli;

        long hours = delta / hoursInMilli;
        delta = delta % hoursInMilli;

        long minutes = delta / minutesInMilli;
        delta = delta % minutesInMilli;

        long seconds = delta / secondsInMilli;

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
