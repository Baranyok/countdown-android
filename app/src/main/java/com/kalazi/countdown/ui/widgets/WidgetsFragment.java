package com.kalazi.countdown.ui.widgets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kalazi.countdown.R;

public class WidgetsFragment extends Fragment {

    private WidgetsViewModel widgetsViewModel;
    private WidgetsRecyclerViewAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        widgetsViewModel = new ViewModelProvider(this).get(WidgetsViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_widgets, container, false);

        registerUIListeners(rootView);

        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.action_search).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.widgets_recycler_view);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        mAdapter = new WidgetsRecyclerViewAdapter();
        recyclerView.setAdapter(mAdapter);

        registerDataObservers(view);
    }

    private void registerDataObservers(View view) {
        // register status text observer
        final TextView textView = view.findViewById(R.id.text_widgets);
        widgetsViewModel.getText().observe(getViewLifecycleOwner(), s -> {
            textView.setText(s);
            if ("".equals(s)) {
                textView.setVisibility(View.INVISIBLE);
            }
        });

        // observer for widget list change
        widgetsViewModel.getWidgets().observe(getViewLifecycleOwner(), list -> mAdapter.updateDataset(list));
    }

    private void registerUIListeners(View view) {
        FloatingActionButton btn = (FloatingActionButton) view.findViewById(R.id.fab2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_placeholder_things(v);
            }
        });
    }

    private void do_placeholder_things(View v) {
        widgetsViewModel.addItem("Another head");
    }
}
