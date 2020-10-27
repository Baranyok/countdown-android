package com.kalazi.countdown.ui.countdowns;

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
        return countdowns;
    }

    public void addItem(String s) {
        addItem(new CountdownItem(current_id++, s));
    }

    public void addItem(CountdownItem item) {
        List<CountdownItem> arrayList = countdowns.getValue();
        if (arrayList != null) {
            arrayList.add(item);
            updateStatusText();
            countdowns.setValue(arrayList);
        }
    }

    public int getLastIndex() {
        int last_id = 0;
        List<CountdownItem> arrayList = countdowns.getValue();
        if (arrayList != null) {
            for (CountdownItem item : arrayList) {
                if (item.getId() > last_id) {
                    last_id = item.getId();
                }
            }
        }
        return last_id;
    }
}
