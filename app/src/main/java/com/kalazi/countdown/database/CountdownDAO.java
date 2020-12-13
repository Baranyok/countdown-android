package com.kalazi.countdown.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.kalazi.countdown.countdowns.CountdownItem;

import java.util.List;

@Dao
public interface CountdownDAO {

    @Query("SELECT * FROM countdown_table")
    LiveData<List<CountdownItem>> getAll();

    @Query("SELECT * FROM countdown_table WHERE id IN (:userIds)")
    List<CountdownItem> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM countdown_table WHERE id = :countdownId")
    LiveData<CountdownItem> loadById(int countdownId);

    @Insert
    void insertAll(CountdownItem... countdownItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CountdownItem countdownItem);

    @Delete
    void delete(CountdownItem countdownItem);

}
