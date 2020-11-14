package com.kalazi.countdown.countdowns;

import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.SeekBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.kalazi.countdown.R;
import com.kalazi.countdown.events.EventListFragment;
import com.kalazi.countdown.util.ColorPickSelectableItem;

public class EditCountdownFragment extends Fragment {

    private CountdownsViewModel countdownsViewModel;
    private CountdownItem item;
    private boolean existedBefore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem doneItem = menu.findItem(R.id.action_done);
        doneItem.setVisible(true);
        doneItem.setOnMenuItemClickListener(item -> confirmEdits());

        if (existedBefore) {
            MenuItem deleteItem = menu.findItem(R.id.action_delete);
            deleteItem.setVisible(true);
            deleteItem.setOnMenuItemClickListener(item -> deleteCountdown());
        }

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        countdownsViewModel = new ViewModelProvider(requireActivity()).get(CountdownsViewModel.class);
        return inflater.inflate(R.layout.fragment_edit_countdown, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int arrayIndex = EditCountdownFragmentArgs.fromBundle(requireArguments()).getCountdownArrayIndex();
        if (arrayIndex == -1) {
            item = new CountdownItem(countdownsViewModel.getLastIndex());
            existedBefore = false;
        } else {
            item = countdownsViewModel.getItemReference(arrayIndex);
            existedBefore = true;
            updateUIFromItem();
        }

        requireView().findViewById(R.id.cc_form_event).setOnClickListener(v -> {
            EventListFragment.newInstance().show(getParentFragmentManager(), "EVENT_LIST");
        });
    }

    private void updateUIFromItem() {
        EditText name = requireView().findViewById(R.id.cc_form_name);
        ColorPickSelectableItem color = requireView().findViewById(R.id.cc_form_color);
        SeekBar opacity = requireView().findViewById(R.id.cc_form_opacity);
        ColorPickSelectableItem fontColor = requireView().findViewById(R.id.cc_form_font_color);

        name.setText(item.getName());
        color.setColor(item.getColor());
        opacity.setProgress(item.getOpacity());
        fontColor.setColor(item.getFontColor());
    }

    private void updateItemFromUI(CountdownItem item) {
        EditText name = requireView().findViewById(R.id.cc_form_name);
        ColorPickSelectableItem color = requireView().findViewById(R.id.cc_form_color);
        SeekBar opacity = requireView().findViewById(R.id.cc_form_opacity);
        ColorPickSelectableItem fontColor = requireView().findViewById(R.id.cc_form_font_color);

        item.setName(name.getText().toString());
        item.setColor(color.getColor());
        item.setOpacity(opacity.getProgress());
        item.setFontColor(fontColor.getColor());
    }

    private boolean confirmEdits() {
        updateItemFromUI(item);
        if (!existedBefore) {
            countdownsViewModel.addItem(item);
        } else {
            countdownsViewModel.notifyItemChanged();
        }

        // return to previous fragment
        NavController navController = NavHostFragment.findNavController(EditCountdownFragment.this);
        navController.popBackStack();
        return false;
    }

    private boolean deleteCountdown() {
        countdownsViewModel.deleteItem(item);

        // return to previous fragment
        NavController navController = NavHostFragment.findNavController(EditCountdownFragment.this);
        navController.popBackStack();
        return false;
    }

}
