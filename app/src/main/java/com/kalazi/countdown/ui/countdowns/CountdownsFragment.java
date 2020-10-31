package com.kalazi.countdown.ui.countdowns;

import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kalazi.countdown.R;

public class CountdownsFragment extends Fragment {

    private CountdownsViewModel countdownsViewModel;
    private CountdownsRecyclerViewAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        countdownsViewModel = new ViewModelProvider(requireActivity()).get(CountdownsViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_countdowns, container, false);

        registerUIListeners(rootView);

        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(true);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.countdowns_recycler_view);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        mAdapter = new CountdownsRecyclerViewAdapter();
        mAdapter.setParentFragment(CountdownsFragment.this);
        recyclerView.setAdapter(mAdapter);

        registerDataObservers(view);
    }

    private void registerDataObservers(View view) {
        // register status text observer
        final TextView textView = view.findViewById(R.id.text_countdowns_status);
        countdownsViewModel.getStatusText().observe(getViewLifecycleOwner(), s -> {
            textView.setText(getString(s));
            if (s == R.string.empty) {
                textView.setVisibility(View.INVISIBLE);
            }
        });

        // observer for widget list change
        countdownsViewModel.getCountdowns().observe(getViewLifecycleOwner(), list -> mAdapter.updateDataset(list));
    }

    private void registerUIListeners(View view) {
        FloatingActionButton btn = view.findViewById(R.id.fab_cc);
        btn.setOnClickListener(v -> NavHostFragment.findNavController(CountdownsFragment.this)
                .navigate(R.id.action_edit_countdown));
    }
}
