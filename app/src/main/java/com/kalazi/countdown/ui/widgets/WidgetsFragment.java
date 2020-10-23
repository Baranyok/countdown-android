package com.kalazi.countdown.ui.widgets;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kalazi.countdown.R;

import java.util.Calendar;

public class WidgetsFragment extends Fragment {

    private WidgetsViewModel widgetsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        widgetsViewModel =
                ViewModelProviders.of(this).get(WidgetsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_widgets, container, false);
        final TextView textView = root.findViewById(R.id.text_widgets);
        widgetsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set button onClickListener
        FloatingActionButton btn = (FloatingActionButton) view.findViewById(R.id.fab2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_placeholder_things(v);
            }
        });

    }

    public void do_placeholder_things(View v) {
        // A date-time specified in milliseconds since the epoch.
        Calendar cal = Calendar.getInstance();

        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, cal.getTimeInMillis());
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(builder.build());
        startActivity(intent);

    }
}
