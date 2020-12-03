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

/**
 * Dialog fragment used for editing a Countdown Item<br>
 * If no argument is given to this Fragment from nav, a new Countdown Item is created
 * After confirmation the Countdown Item reference is updated or a new one is added to the ViewModel
 * Common ViewModel with CountdownsFragment (with the Activity as lifecycle owner)
 */
public class EditCountdownFragment extends Fragment {

    private CountdownsViewModel viewModel;
    private CountdownItem item;
    private boolean existedBefore;

    ////// Overrides

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // enable options in the action bar
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        updateActionBarItems(menu);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initViewModel();
        return inflater.inflate(R.layout.fragment_edit_countdown, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadItem();
        registerUIListeners(view);
    }

    ////// Private

    /// Utility methods

    /**
     * Initializes the viewModel with the proper lifecycle owner
     */
    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(CountdownsViewModel.class);
    }

    /**
     * Changes the visibility and binds actions to the Action Bar items
     *
     * @param menu Action Bar Menu
     */
    private void updateActionBarItems(@NonNull Menu menu) {
        MenuItem doneItem = menu.findItem(R.id.action_done);
        doneItem.setVisible(true);
        doneItem.setOnMenuItemClickListener(item -> confirmEdits());

        if (existedBefore) {
            MenuItem deleteItem = menu.findItem(R.id.action_delete);
            deleteItem.setVisible(true);
            deleteItem.setOnMenuItemClickListener(item -> deleteCountdown());
        }
    }

    /**
     * Loads the correct item depending on the Fragment call argument (given from navigation)
     * If no argument is given to this Fragment from nav, a new Countdown Item is created
     */
    private void loadItem() {
        int arrayIndex = EditCountdownFragmentArgs.fromBundle(requireArguments()).getCountdownArrayIndex();
        if (arrayIndex == -1) {
            item = new CountdownItem(viewModel.getLastIndex());
            existedBefore = false;
        } else {
            item = viewModel.getItemReference(arrayIndex);
            existedBefore = true;
            updateUIFromItem();
        }
    }

    /**
     * Register all UI listeners (UI Interactivity)
     *
     * @param view Root view of this fragment
     */
    private void registerUIListeners(@NonNull View view) {
        view.findViewById(R.id.cc_form_event).setOnClickListener(v ->
                EventListFragment.newInstance().show(getParentFragmentManager(), "EVENT_LIST"));
    }

    /// Action methods

    /**
     * Updates the UI items to match the current Item
     */
    // TODO: bind instead?
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

    /**
     * Updates the current Item to match the UI values
     */
    // TODO: bind instead?
    private void updateItemFromUI() {
        EditText name = requireView().findViewById(R.id.cc_form_name);
        ColorPickSelectableItem color = requireView().findViewById(R.id.cc_form_color);
        SeekBar opacity = requireView().findViewById(R.id.cc_form_opacity);
        ColorPickSelectableItem fontColor = requireView().findViewById(R.id.cc_form_font_color);

        item.setName(name.getText().toString());
        item.setColor(color.getColor());
        item.setOpacity(opacity.getProgress());
        item.setFontColor(fontColor.getColor());
    }

    /**
     * Update/Add the confirmed item in viewModel<br>
     * This method is a callback
     *
     * @return false -> DON'T consume this click (onOptionsItemSelected from MainActivity will be executed after this)
     */
    private boolean confirmEdits() {
        updateItemFromUI();
        if (!existedBefore) {
            viewModel.addItem(item);
        } else {
            viewModel.notifyItemChanged();
        }

        // return to previous fragment
        NavController navController = NavHostFragment.findNavController(EditCountdownFragment.this);
        navController.popBackStack();
        return false;
    }

    /**
     * If updating an existing item, this should be executed on the trash icon click<br>
     * This method is a callback
     *
     * @return false -> DON'T consume this click (onOptionsItemSelected from MainActivity will be executed after this)
     */
    private boolean deleteCountdown() {
        viewModel.deleteItem(item);

        // return to previous fragment
        NavController navController = NavHostFragment.findNavController(EditCountdownFragment.this);
        navController.popBackStack();
        return false;
    }

}
