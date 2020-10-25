package com.kalazi.countdown.ui.countdowns;

import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.kalazi.countdown.R;

public class CreateCountdownFragment extends Fragment {
    private String data = "hello"; // data placeholder

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem doneItem = menu.findItem(R.id.action_done);
        doneItem.setVisible(true);

        doneItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                confirmEdits();
                return true;
            }
        });

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_countdown, container, false);
    }

    private String createCountdownObject() {
        View rootView = getView();
        if (rootView != null) {
            EditText text = rootView.findViewById(R.id.editField);
            return text.getText().toString();
        }
        return "";
    }

    private void confirmEdits() {
        CreateCountdownFragmentDirections.ActionCreateCountdownSubmit action;
        action = CreateCountdownFragmentDirections.actionCreateCountdownSubmit();
        action.setCountdownData(createCountdownObject());
        NavHostFragment.findNavController(CreateCountdownFragment.this).navigate(action);
    }
}
