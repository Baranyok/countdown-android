package com.kalazi.countdown.ui.countdowns;

import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.kalazi.countdown.R;
import com.thebluealliance.spectrum.SpectrumDialog;

public class CreateCountdownFragment extends Fragment {

    private CountdownsViewModel countdownsViewModel;

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

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        countdownsViewModel = new ViewModelProvider(requireActivity()).get(CountdownsViewModel.class);
        return inflater.inflate(R.layout.fragment_create_countdown, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireView().findViewById(R.id.cc_button_color).setOnClickListener(this::pickColor);
    }

    private String createCountdownObject() {
        EditText text = requireView().findViewById(R.id.cc_form_name);
        return text.getText().toString();
    }

    private boolean confirmEdits() {
        // hide the keyboard
        InputMethodManager keyboard = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        // publish the data
        NavController navController = NavHostFragment.findNavController(CreateCountdownFragment.this);
        countdownsViewModel.addItem(createCountdownObject());
        navController.popBackStack();
        return true;
    }

    private void pickColor(View view) {
        new SpectrumDialog.Builder(getContext())
                .setColors(R.array.colors_pick)
                .setSelectedColorRes(R.color.md_blue_500)
                .setDismissOnColorSelected(true)
                .setOutlineWidth(0)
                .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                        if (positiveResult) {
                            Toast.makeText(getContext(), "Color selected: #" + Integer.toHexString(color).toUpperCase(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Dialog cancelled", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).build().show(getParentFragmentManager(), "dialog_demo_1");
    }
}
