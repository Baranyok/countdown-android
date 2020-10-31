package com.kalazi.countdown.ui.countdowns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.kalazi.countdown.R;

import java.util.ArrayList;
import java.util.List;

public class CountdownsViewModel extends ViewModel {

    private final List<CountdownItem> countdownsArray = new ArrayList<>();
    private final MutableLiveData<Integer> statusText = new MutableLiveData<>();
    private final MutableLiveData<List<CountdownItem>> countdowns = new MutableLiveData<>(null);

    public CountdownsViewModel() {
        if (countdowns.getValue() == null) {
            countdowns.setValue(countdownsArray);
        }

        updateStatusText();
    }

    // displays a text if no countdowns are present
    private void updateStatusText() {
        statusText.setValue((countdownsArray.isEmpty()) ? R.string.frag_countdowns_none : R.string.empty);
    }

    public LiveData<Integer> getStatusText() {
        return statusText;
    }

    public LiveData<List<CountdownItem>> getCountdowns() {
        return countdowns;
    }

    public void addItem(CountdownItem item) {
        countdownsArray.add(item);
        notifyItemChanged();
        updateStatusText();
    }

    public CountdownItem getItemReference(int index) {
        return countdownsArray.get(index);
    }

    public void notifyItemChanged() {
        countdowns.setValue(countdownsArray);
    }

    public void deleteItem(CountdownItem item) {
        countdownsArray.remove(item);
        notifyItemChanged();
        updateStatusText();
    }

    public int getLastIndex() {
        int new_id = 0;
        for (CountdownItem item : countdownsArray) {
            if (item.getId() == new_id) {
                new_id = item.getId() + 1;
            }
        }
        return new_id;
    }
}
