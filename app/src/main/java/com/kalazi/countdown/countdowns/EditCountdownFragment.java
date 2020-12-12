package com.kalazi.countdown.countdowns;

import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.kalazi.countdown.R;
import com.kalazi.countdown.util.CalendarEventPickItem;
import com.kalazi.countdown.util.ColorConverter;
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

    private EditText titleView;
    private ColorPickSelectableItem colorView;
    private SeekBar opacityView;
    private ColorPickSelectableItem fontColorView;
    private CalendarEventPickItem eventPickItemView;
    private TextView titleLockView;
    private SwitchCompat showEventNameSwitch;

    // in case editing is finished and lock is immediately clicked
    private boolean editLocking = false;
    private boolean titleLocked = false;

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

        titleView = view.findViewById(R.id.cc_form_title);
        colorView = view.findViewById(R.id.cc_form_color);
        opacityView = view.findViewById(R.id.cc_form_opacity);
        fontColorView = view.findViewById(R.id.cc_form_font_color);
        eventPickItemView = view.findViewById(R.id.cc_form_event);
        titleLockView = view.findViewById(R.id.cc_title_lock);
        showEventNameSwitch = view.findViewById(R.id.cc_form_show_event);

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
            item = new CountdownItem();
            existedBefore = false;
        } else {
            item = viewModel.getItem(arrayIndex);
            existedBefore = true;
            if (!"".equals(item.title)) {
                lockTitle(true);
            }
            updateUIFromItem();
        }
    }

    /**
     * Register all UI listeners (UI Interactivity)
     *
     * @param view Root view of this fragment
     */
    private void registerUIListeners(@NonNull View view) {
        eventPickItemView = view.findViewById(R.id.cc_form_event);
        eventPickItemView.registerDataObservers(getViewLifecycleOwner());
        eventPickItemView.getEvent().observe(getViewLifecycleOwner(), eventItem -> {
            if (!titleLocked) {
                titleView.setText(eventItem.title);
            }
        });

        titleLockView.setOnClickListener(l -> {
            if (!editLocking) {
                lockTitle(!titleLocked);
            } else {
                editLocking = false;
            }
        });

        titleView.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                lockTitle(true);
                editLocking = true;
            }
        });
    }

    private void lockTitle(boolean newState) {
        titleLocked = newState;
        titleLockView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,
                (titleLocked) ? R.drawable.ic_lock_closed : R.drawable.ic_lock_open);
        titleView.setEnabled(!newState);
    }

    /// Action methods

    /**
     * Updates the UI items to match the current Item
     */
    // TODO: bind instead?
    private void updateUIFromItem() {
        titleView.setText(item.title);
        colorView.setColor(ColorConverter.removeColorAlpha(item.color));
        opacityView.setProgress(ColorConverter.colorToOpacity(item.color));
        fontColorView.setColor(item.fontColor);
        eventPickItemView.setEventFromID(item.eventID);
        showEventNameSwitch.setChecked(item.showEventName);
    }

    /**
     * Updates the current Item to match the UI values
     */
    // TODO: bind instead?
    private void updateItemFromUI() {
        item.title = titleView.getText().toString();
        item.color = ColorConverter.combineColorOpacity(colorView.getColor(), opacityView.getProgress());
        item.fontColor = fontColorView.getColor();
        item.eventID = eventPickItemView.getEventID();
        item.showEventName = showEventNameSwitch.isChecked();
    }

    /**
     * Update/Add the confirmed item in viewModel<br>
     * This method is a callback
     *
     * @return false -> DON'T consume this click (onOptionsItemSelected from MainActivity will be executed after this)
     */
    private boolean confirmEdits() {
        updateItemFromUI();

        // in case the item with id exists, it will be overwritten
        viewModel.addItem(item);

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
