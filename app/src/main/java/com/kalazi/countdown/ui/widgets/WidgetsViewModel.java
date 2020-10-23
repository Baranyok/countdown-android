package com.kalazi.countdown.ui.widgets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WidgetsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WidgetsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
