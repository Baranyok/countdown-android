package com.kalazi.countdown.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateConverter {

    boolean hit = false;

    public static String millisToFormattedString(long millis, String timeZone) {
        if (timeZone == null) {
            timeZone = "UTC";
        }

        Date date = new Date(millis);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
        return formatter.format(date);
    }

    /**
     * Checks if the UTC timestamp is in the future
     *
     * @param nextTime       next occurrence in reference to which the time delta will be calculated (in UTC millis)
     * @param sourceTimeZone timezone in which the date should be displayed (I don't want to touch this shit again)
     * @return true if the timestamp is in the future
     */
    public static boolean isInFuture(long nextTime, String sourceTimeZone) {
        if (sourceTimeZone == null) {
            sourceTimeZone = "UTC";
        }

        long delta = nextTime - Instant.now().toEpochMilli() + tzOffsetToLocal(nextTime, sourceTimeZone);
        return delta > 0;
    }

    /**
     * Calculates and formats the time delta value from now until <code>nextTime</code>
     *
     * @param nextTime       next occurrence in reference to which the time delta will be calculated (in UTC millis)
     * @param sourceTimeZone timezone in which the date should be displayed (I don't want to touch this shit again)
     * @return formatted string
     */
    public String timeDifferenceToFormattedString(long nextTime, String sourceTimeZone) {
        if (nextTime <= 0) {
            // TODO implement count-up
            return "Negative or zero";
        }

        String retString = "";

        long delta = nextTime - Instant.now().toEpochMilli() + tzOffsetToLocal(nextTime, sourceTimeZone);
        if ((delta / 1000) == 0) {
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

    private static long tzOffsetToLocal(long timeStamp, String sourceTimeZone) {
        // translate nextTime from the given timezone
        TimeZone sourceTz = TimeZone.getTimeZone(sourceTimeZone);
        long sourceOffset = sourceTz.getOffset(timeStamp);
        long localOffset = TimeZone.getDefault().getOffset(new Date().getTime());
        return sourceOffset - localOffset;
    }

}
