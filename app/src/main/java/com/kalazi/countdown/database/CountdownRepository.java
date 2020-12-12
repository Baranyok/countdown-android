package com.kalazi.countdown.database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.kalazi.countdown.countdowns.CountdownItem;

import java.util.List;

public class CountdownRepository {
    private CountdownDAO countdownDAO;
    private LiveData<List<CountdownItem>> countdowns;

    public CountdownRepository(Application application) {
        CountdownDatabase db = DBManager.getInstance(application).getCountdownDB();
        countdownDAO = db.countdownDAO();
        countdowns = countdownDAO.getAll();
    }

    public LiveData<List<CountdownItem>> getAll() {
        return countdowns;
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
