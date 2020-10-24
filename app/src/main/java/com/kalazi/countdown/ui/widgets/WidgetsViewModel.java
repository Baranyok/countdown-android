package com.kalazi.countdown.ui.widgets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class WidgetsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<ArrayList<WidgetItem>> widgets;

    // debug
    private int current_id = 0;

    public WidgetsViewModel() {
        mText = new MutableLiveData<>();
        widgets = new MutableLiveData<>();

        ArrayList<WidgetItem> sampleData = new ArrayList<>();

        widgets.setValue(sampleData);
        updateStatusText();
    }

    private void updateStatusText() {
        String newText;
        ArrayList<WidgetItem> arrayList = widgets.getValue();
        if (arrayList == null || arrayList.isEmpty()) {
            newText = "No widgets exist";
        } else {
            newText = "";
        }
        if (!newText.equals(mText.getValue())) {
            mText.setValue(newText);
        }
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<ArrayList<WidgetItem>> getWidgets() {
        return widgets;
    }

    public void addItem(String s) {
        ArrayList<WidgetItem> arrayList = widgets.getValue();
        if (arrayList != null) {
            arrayList.add(new WidgetItem(current_id++, s));
            updateStatusText();
            widgets.setValue(arrayList);
        }
    }
}
