package com.kalazi.countdown.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.kalazi.countdown.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // set up preferences from xml
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        // initialize listeners
        Preference pref_theme = findPreference("theme_dark");
        if (pref_theme != null) {
            pref_theme.setOnPreferenceChangeListener((preference, newValue) -> {
                if (getActivity() != null) {
                    getActivity().recreate();
                }
                return true;
            });
        }

    }
}
