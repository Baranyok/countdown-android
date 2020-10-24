package com.kalazi.countdown.ui.widgets;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kalazi.countdown.R;

public class WidgetsFragment extends Fragment {

    private WidgetsViewModel widgetsViewModel;
    private RecyclerView recyclerView;
    private WidgetsRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


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

        recyclerView = view.findViewById(R.id.widgets_recycler_view);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new WidgetsRecyclerViewAdapter();
        recyclerView.setAdapter(mAdapter);

        // observer for widget list change
        widgetsViewModel.getWidgets().observe(getViewLifecycleOwner(), list -> mAdapter.submitList(list));

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
