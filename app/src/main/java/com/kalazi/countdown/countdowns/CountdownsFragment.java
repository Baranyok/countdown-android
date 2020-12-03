package com.kalazi.countdown.countdowns;

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

/**
 * Represents the Fragment within which a list (RecyclerView) of CountdownItem-s is displayed
 * Common ViewModel with EditCountdownFragment (with the Activity as lifecycle owner)
 */
public class CountdownsFragment extends Fragment {

    private CountdownsViewModel viewModel;
    private CountdownsRecyclerViewAdapter rvAdapter;

    ////// Overrides

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // enable options in the action bar
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initViewModel();
        return inflater.inflate(R.layout.fragment_countdowns, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        updateActionBarItems(menu);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView(view);

        registerUIListeners(view);
        registerDataObservers(view);
    }

    ////// Private utility methods

    /**
     * Initializes the viewModel with the proper lifecycle owner
     */
    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(CountdownsViewModel.class);
    }

    /**
     * Initializes the RecyclerView holding the Countdown Items<br>
     * Sets the correct LayoutManager and Adapter for the RecyclerView
     *
     * @param view Root view of this fragment
     */
    private void initRecyclerView(@NonNull View view) {
        RecyclerView recyclerView = view.findViewById(R.id.countdowns_recycler_view);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        rvAdapter = new CountdownsRecyclerViewAdapter();
        rvAdapter.setParentFragment(CountdownsFragment.this);
        recyclerView.setAdapter(rvAdapter);
    }

    /**
     * Changes the visibility and binds actions to the Action Bar items
     *
     * @param menu Action Bar Menu
     */
    private void updateActionBarItems(@NonNull Menu menu) {
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
                rvAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    /**
     * Register all data observers (Data binding section)
     *
     * @param view Root view of this fragment
     */
    private void registerDataObservers(@NonNull View view) {
        // register status text observer
        final TextView textView = view.findViewById(R.id.text_countdowns_status);
        viewModel.getStatusText().observe(getViewLifecycleOwner(), s -> {
            textView.setText(getString(s));
            if (s == R.string.empty) {
                textView.setVisibility(View.INVISIBLE);
            }
        });

        // observer for widget list change
        viewModel.getCountdowns().observe(getViewLifecycleOwner(), list -> rvAdapter.updateDataset(list));
    }

    /**
     * Register all UI listeners (UI Interactivity)
     *
     * @param view Root view of this fragment
     */
    private void registerUIListeners(@NonNull View view) {
        FloatingActionButton btn = view.findViewById(R.id.fab_cc);
        btn.setOnClickListener(v -> NavHostFragment.findNavController(CountdownsFragment.this)
                .navigate(R.id.action_edit_countdown));
    }
}
