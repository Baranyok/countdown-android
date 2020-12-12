package com.kalazi.countdown.database;

import android.content.Context;
import androidx.room.Room;

public class DBManager {
    private static volatile DBManager instance;

    private final CountdownDatabase countdownDB;

    private DBManager(Context appContext) {
        countdownDB = Room.databaseBuilder(appContext,
                CountdownDatabase.class, "countdown-database").build();
    }

    public CountdownDatabase getCountdownDB() {
        return countdownDB;
    }

    public static DBManager getInstance(Context appContext) {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager(appContext);
                }
            }
        }
        return instance;
    }
}
