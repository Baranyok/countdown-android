package com.kalazi.countdown.ui.countdowns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.kalazi.countdown.R;

import java.util.ArrayList;

public class CountdownsViewModel extends ViewModel {

    private final MutableLiveData<Integer> statusText;
    private final MutableLiveData<ArrayList<CountdownItem>> countdowns;

    // debug
    private int current_id = 0;

    public CountdownsViewModel() {
        statusText = new MutableLiveData<>();
        countdowns = new MutableLiveData<>();

        ArrayList<CountdownItem> sampleData = new ArrayList<>();

        countdowns.setValue(sampleData);
        updateStatusText();
    }

    // displays a text if no countdowns are present
    private void updateStatusText() {
        ArrayList<CountdownItem> arrayList = countdowns.getValue();
        statusText.setValue((arrayList == null || arrayList.isEmpty()) ? R.string.frag_countdowns_none : R.string.empty);
    }

    public LiveData<Integer> getStatusText() {
        return statusText;
    }

    public LiveData<ArrayList<CountdownItem>> getCountdowns() {
        return countdowns;
    }

    public void addItem(String s) {
        ArrayList<CountdownItem> arrayList = countdowns.getValue();
        if (arrayList != null) {
            arrayList.add(new CountdownItem(current_id++, s));
            updateStatusText();
            countdowns.setValue(arrayList);
        }
    }
}
