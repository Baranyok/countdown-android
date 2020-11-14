package com.kalazi.countdown.calendar;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CalendarPermissionManager {
    private static final int PERM_REQUEST_CODE = 2;
    private static final String[] perms = {
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
    };

    /**
     * Requests every permission
     *
     * @param ctx context activity on which the onRequestPermissionsResult will be called
     */
    static public void askForPermissions(Activity ctx) {
        ActivityCompat.requestPermissions(ctx, perms, PERM_REQUEST_CODE);
    }

    /**
     * Checks if every permission has been granted
     * @param ctx context
     * @return true if everything is granted, in any other case false
     */
    static public boolean checkPermissions(Context ctx) {
        for (String perm: perms) {
            if (ContextCompat.checkSelfPermission(ctx, perm) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }
}
