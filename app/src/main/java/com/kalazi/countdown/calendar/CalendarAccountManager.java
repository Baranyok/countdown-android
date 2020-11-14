package com.kalazi.countdown.calendar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import java.util.ArrayList;

public class CalendarAccountManager {

    // DEFINES
    // Projection array. Creating indices for this array instead of doing
    // dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION_CALENDARS = new String[]{
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    public static final String[] EVENT_PROJECTION_ACCOUNTS = new String[] {
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.ACCOUNT_TYPE,
            CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
            CalendarContract.Calendars.IS_PRIMARY
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    public CalendarAccountManager(Activity ctx) {
        // NOTE: The permissions to access the calendar should be asked
        // when the option to sync with google calendar is turned on
    }

    public String getFirstItem(Activity ctx) {
        if (!CalendarPermissionManager.checkPermissions(ctx)) {
            CalendarPermissionManager.askForPermissions(ctx);
            return "Permission denied";
        }

        ArrayList<String> accounts = getAccounts(ctx);
        if (accounts.isEmpty()) {
            return "Empty";
        }

        return accounts.get(0);
    }

    private ArrayList<String> getAccounts(Context ctx) {
        ArrayList <String> accounts = new ArrayList<String>();
        Cursor cur = null;
        ContentResolver cr = ctx.getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;

        // the actual query
        cur = cr.query(uri, EVENT_PROJECTION_ACCOUNTS, "", null, null);

        while (cur.moveToNext()) {
            accounts.add(cur.getString(1)); // Todo: Hardcoded index
        }

        cur.close();

        return accounts;
    }

//    private void query() {
//        Cursor cur = null;
//        ContentResolver cr = getContentResolver();
//        Uri uri = CalendarContract.Calendars.CONTENT_URI;
//        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
//                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
//                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
//        String[] selectionArgs = new String[]{"hera@example.com", "com.example",
//                "hera@example.com"};
//        // Submit the query and get a Cursor object back.
//        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
//
//        // Use the cursor to step through the returned records
//        while (cur.moveToNext()) {
//            long calID = 0;
//            String displayName = null;
//            String accountName = null;
//            String ownerName = null;
//
//            // Get the field values
//            calID = cur.getLong(PROJECTION_ID_INDEX);
//            displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
//            accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
//            ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
//
//            // Do something with the values...
//
//            text.setText(String.format("This: %s", calID));
//            break;
//
//        }
//    }

}
