package com.kalazi.countdown.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.kalazi.countdown.R;
import com.kalazi.countdown.permissions.PermissionManager;
import com.kalazi.countdown.permissions.PermissionViewModel;

public class EventListFragment extends DialogFragment {

    private EventListViewModel viewModel;
    private PermissionViewModel permissionViewModel;
    private Button askPermButton;

    public static EventListFragment newInstance() {
        return new EventListFragment();
    }

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
        registerDataObservers();
        registerUIListeners(view);

        permissionViewModel.checkPerms(requireActivity());
    }

    /**
     * Register all data observers (Data binding section)
     */
    private void registerDataObservers() {
        permissionViewModel.getPermsGranted().observe(getViewLifecycleOwner(), perms -> {
            if (perms) {
                viewModel.load(getActivity());
            }
        });

        permissionViewModel.getPermsGranted().observe(getViewLifecycleOwner(), perms -> {
            // display button for perm asking
            askPermButton.setVisibility((perms) ? View.INVISIBLE : View.VISIBLE);
        });
    }

    /**
     * Register all UI listeners (UI Interactivity)
     *
     * @param view Root view of this fragment
     */
    private void registerUIListeners(@NonNull View view) {
        askPermButton = view.findViewById(R.id.ask_perm_button);
        askPermButton.setOnClickListener(v -> PermissionManager.askForPermissions(getActivity()));
    }
}
