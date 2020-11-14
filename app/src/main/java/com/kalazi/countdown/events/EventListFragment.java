package com.kalazi.countdown.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.kalazi.countdown.R;

public class EventListFragment extends DialogFragment {

    private EventListViewModel mViewModel;

    public static EventListFragment newInstance() {
        return new EventListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(EventListViewModel.class);
        return inflater.inflate(R.layout.event_list_fragment, container, false);
    }

}
