package com.kalazi.countdown.database;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.kalazi.countdown.countdowns.CountdownItem;

import java.util.List;

public class CountdownRepository {
    private CountdownDAO countdownDAO;
    private LiveData<List<CountdownItem>> countdowns;

    public CountdownRepository(Context appContext) {
        CountdownDatabase db = DBManager.getInstance(appContext).getCountdownDB();
        countdownDAO = db.countdownDAO();
        countdowns = countdownDAO.getAll();
    }

    public LiveData<List<CountdownItem>> getAll() {
        return countdowns;
    }

    public LiveData<CountdownItem> getById(int countdownId) {
        return countdownDAO.loadById(countdownId);
    }

    public void insert(CountdownItem countdownItem) {
        CountdownDatabase.databaseWriteExecutor.execute(() -> {
            countdownDAO.insert(countdownItem);
        });
    }

    public void delete(CountdownItem countdownItem) {
        CountdownDatabase.databaseWriteExecutor.execute(() -> {
            countdownDAO.delete(countdownItem);
        });
    }
}
