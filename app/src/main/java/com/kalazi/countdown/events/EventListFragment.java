package com.kalazi.countdown.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.kalazi.countdown.R;
import com.kalazi.countdown.permissions.PermissionManager;
import com.kalazi.countdown.permissions.PermissionViewModel;

public class EventListFragment extends DialogFragment {

    private EventListViewModel viewModel;
    private PermissionViewModel permissionViewModel;
    private Button askPermButton;
    private LinearLayout noPermsBlock;
    private EventRVAdapter rvAdapter;
    public MutableLiveData<EventItem> event;

    ////// Constructors

    public EventListFragment(MutableLiveData<EventItem> event) {
        this.event = event;
    }

    public static EventListFragment newInstance(MutableLiveData<EventItem> event) {
        return new EventListFragment(event);
    }

    ////// Overrides

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(EventListViewModel.class);
        permissionViewModel = new ViewModelProvider(requireActivity()).get(PermissionViewModel.class);
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        askPermButton = view.findViewById(R.id.ask_perm_button);
        noPermsBlock = view.findViewById(R.id.no_perms_block);

        initRecyclerView(view);

        registerDataObservers(view);
        registerUIListeners(view);

        permissionViewModel.checkPerms(requireActivity());
    }

    private void initRecyclerView(@NonNull View view) {
        RecyclerView recyclerView = view.findViewById(R.id.events_recycler_view);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        rvAdapter = new EventRVAdapter();
        rvAdapter.setParentFragment(this);
        recyclerView.setAdapter(rvAdapter);
    }

    /**
     * Register all data observers (Data binding section)
     */
    private void registerDataObservers(@NonNull View view) {
        SearchView searchView = view.findViewById(R.id.event_search);

        permissionViewModel.getPermsGranted().observe(getViewLifecycleOwner(), perms -> {
            if (perms) {
                viewModel.load(getActivity());
            }
        });

        permissionViewModel.getPermsGranted().observe(getViewLifecycleOwner(), perms -> {
            // display button for perm asking
            noPermsBlock.setVisibility((perms) ? View.GONE : View.VISIBLE);
        });

        permissionViewModel.getPermsGranted().observe(getViewLifecycleOwner(), perms -> {
            // display button for perm asking
            searchView.setVisibility((perms) ? View.VISIBLE : View.GONE);
        });

        viewModel.getEvents().observe(getViewLifecycleOwner(), eventList -> rvAdapter.updateDataset(eventList));
    }

    /**
     * Register all UI listeners (UI Interactivity)
     *
     * @param view Root view of this fragment
     */
    private void registerUIListeners(@NonNull View view) {
        askPermButton.setOnClickListener(v -> PermissionManager.askForPermissions(getActivity()));

        SearchView searchView = view.findViewById(R.id.event_search);
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
}
