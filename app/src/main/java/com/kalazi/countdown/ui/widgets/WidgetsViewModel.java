package com.kalazi.countdown.ui.widgets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class WidgetsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<WidgetItem>> widgets;

    // debug
    private int current_id = 0;

    public WidgetsViewModel() {
        mText = new MutableLiveData<>();
        widgets = new MutableLiveData<>();
        mText.setValue("No widgets exist");

        ArrayList<WidgetItem> sampleData = new ArrayList<>();
        sampleData.add(new WidgetItem(current_id++, "head 1"));
        sampleData.add(new WidgetItem(current_id++, "head 2"));

        widgets.setValue(sampleData);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<ArrayList<WidgetItem>> getWidgets() {
        // do asynchronous operation to get widgets
        return widgets;
    }

    public void addItem(String s) {
        ArrayList<WidgetItem> arrayList = widgets.getValue();
        if (arrayList != null) {
            arrayList.add(new WidgetItem(current_id++, s));
            widgets.setValue(arrayList);
        }
    }
}
