package com.kalazi.countdown.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.kalazi.countdown.countdowns.CountdownItem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CountdownItem.class}, version = 1, exportSchema = false)
public abstract class CountdownDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract CountdownDAO countdownDAO();

}
