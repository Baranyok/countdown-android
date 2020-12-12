package com.kalazi.countdown.countdowns;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.kalazi.countdown.R;
import com.kalazi.countdown.database.CountdownRepository;

import java.util.List;

/**
 * Represents the ViewModel of the CountdownFragment<br>
 * Primarily contains a list of Countdown Items
 */
public class CountdownsViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> statusText = new MutableLiveData<>();

    private final CountdownRepository repository;
    private final LiveData<List<CountdownItem>> countdowns;

    ////// Constructors

    public CountdownsViewModel(Application application) {
        super(application);

        repository = new CountdownRepository(application);
        countdowns = repository.getAll();

        updateStatusText();
    }

    ////// Public interface -> LiveData + Item operations

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
        repository.insert(item);
        updateStatusText();
    }

    /**
     * Deletes an item from the dataset
     *
     * @param item The item reference to be deleted
     */
    public void deleteItem(CountdownItem item) {
        repository.delete(item);
        updateStatusText();
    }

    /**
     * Get an Item reference from the dataset
     *
     * @param index index of the item (not item ID!)
     * @return Item CountdownItem reference
     */
    public CountdownItem getItem(int index) {
        if (countdowns.getValue() != null) {
            return countdowns.getValue().get(index);
        }

        return null;
    }

    ////// Private utility functions

    /**
     * Displays a text if no countdowns are present
     */
    private void updateStatusText() {
        if (countdowns.getValue() == null) {
            statusText.setValue(R.string.frag_countdowns_none);
        } else {
            statusText.setValue((countdowns.getValue().isEmpty()) ? R.string.frag_countdowns_none : R.string.empty);
        }
    }
}
