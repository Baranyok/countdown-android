package com.kalazi.countdown.ui.countdowns;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.kalazi.countdown.R;

import java.util.ArrayList;
import java.util.List;

public class CountdownsViewModel extends ViewModel {

    private final MutableLiveData<Integer> statusText = new MutableLiveData<>();
    private final MutableLiveData<List<CountdownItem>> countdowns = new MutableLiveData<>(null);

    // debug
    private int current_id = 0;

    public CountdownsViewModel() {
        if (countdowns.getValue() == null) {
            countdowns.setValue(new ArrayList<>());
            Log.v("This", "shit happened");
        }

        updateStatusText();
    }

    // displays a text if no countdowns are present
    private void updateStatusText() {
        List<CountdownItem> arrayList = countdowns.getValue();
        statusText.setValue((arrayList == null || arrayList.isEmpty()) ? R.string.frag_countdowns_none : R.string.empty);
    }

    public LiveData<Integer> getStatusText() {
        return statusText;
    }

    public LiveData<List<CountdownItem>> getCountdowns() {
        Log.v("ok1", countdowns.toString());
        return countdowns;
    }

    public void addItem(String s) {
        List<CountdownItem> arrayList = countdowns.getValue();
        if (arrayList != null) {
            arrayList.add(new CountdownItem(current_id++, s));
            updateStatusText();
            Log.v("Data model", arrayList.toString());
            countdowns.setValue(arrayList);
            Log.v("Data model after", countdowns.getValue().toString());
        }
        Log.v("ok2", countdowns.toString());
    }
}
