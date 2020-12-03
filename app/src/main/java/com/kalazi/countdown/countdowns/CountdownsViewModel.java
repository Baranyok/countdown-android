package com.kalazi.countdown.countdowns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.kalazi.countdown.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the ViewModel of the CountdownFragment<br>
 * Primarily contains a list of Countdown Items
 */
public class CountdownsViewModel extends ViewModel {

    private final List<CountdownItem> countdownsArray = new ArrayList<>();
    private final MutableLiveData<Integer> statusText = new MutableLiveData<>();
    private final MutableLiveData<List<CountdownItem>> countdowns = new MutableLiveData<>(null);

    ////// Constructors

    public CountdownsViewModel() {
        if (countdowns.getValue() == null) {
            countdowns.setValue(countdownsArray);
        }

        updateStatusText();
    }

    ////// Public interface -> LiveData + Item operations

    /**
     * LiveData interface for connecting an observer to the status text
     *
     * @return LiveData for the status text
     */
    public LiveData<Integer> getStatusText() {
        return statusText;
    }

    /**
     * LiveData interface for connecting an observer to the list of Countdown Items
     *
     * @return LiveData for the countdown list
     */
    public LiveData<List<CountdownItem>> getCountdowns() {
        return countdowns;
    }

    /// Item operations

    /**
     * Adds an item to the dataset
     *
     * @param item The item reference to be added
     */
    public void addItem(CountdownItem item) {
        countdownsArray.add(item);
        notifyItemChanged();
        updateStatusText();
    }

    /**
     * Deletes an item from the dataset
     *
     * @param item The item reference to be deleted
     */
    public void deleteItem(CountdownItem item) {
        countdownsArray.remove(item);
        notifyItemChanged();
        updateStatusText();
    }

    /**
     * Get an Item reference from the dataset
     *
     * @param index index of the item (not item ID!)
     * @return Item CountdownItem reference
     */
    public CountdownItem getItemReference(int index) {
        return countdownsArray.get(index);
    }

    /**
     * Notify the LiveData holding the dataset that an item has changed
     */
    public void notifyItemChanged() {
        countdowns.setValue(countdownsArray);
    }

    /// Temporary section

    /**
     * Temporary function
     *
     * @return Available index (not item ID!) that can be inserted into a RecyclerView
     */
    public int getLastIndex() {
        int new_id = 0;
        for (CountdownItem item : countdownsArray) {
            if (item.getId() == new_id) {
                new_id = item.getId() + 1;
            }
        }
        return new_id;
    }

    ////// Private utility functions

    /**
     * Displays a text if no countdowns are present
     */
    private void updateStatusText() {
        statusText.setValue((countdownsArray.isEmpty()) ? R.string.frag_countdowns_none : R.string.empty);
    }
}
