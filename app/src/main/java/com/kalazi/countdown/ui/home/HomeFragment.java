package com.kalazi.countdown.ui.home;

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
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kalazi.countdown.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        registerDataObservers(root);

        return root;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.action_search).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_home).setOnClickListener(view1 -> {
            HomeFragmentDirections.ActionHomeFragmentToHomeSecondFragment action =
                    HomeFragmentDirections.actionHomeFragmentToHomeSecondFragment
                            ("From HomeFragment");
            NavHostFragment.findNavController(HomeFragment.this)
                    .navigate(action);
        });

        // set button onClickListener
        FloatingActionButton btn = view.findViewById(R.id.fab);
        btn.setOnClickListener(this::do_placeholder_things);
    }

    private void registerDataObservers(View view) {
        final TextView textView = view.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
    }

    public void do_placeholder_things(View v) {

    }
}
