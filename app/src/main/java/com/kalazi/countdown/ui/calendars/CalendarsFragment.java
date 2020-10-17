package com.kalazi.countdown.ui.calendars;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.kalazi.countdown.R;

public class CalendarsFragment extends Fragment {

    private CalendarsViewModel calendarsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarsViewModel =
                ViewModelProviders.of(this).get(CalendarsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendars, container, false);
        final TextView textView = root.findViewById(R.id.text_calendars);
        calendarsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
