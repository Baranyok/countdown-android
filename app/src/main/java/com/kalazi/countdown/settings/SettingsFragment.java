package com.kalazi.countdown.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.kalazi.countdown.R;
import com.kalazi.countdown.calendar.CalendarItem;
import com.kalazi.countdown.calendar.CalendarManager;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends PreferenceFragmentCompat {
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    ////// Overrides

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // set up preferences from xml
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        fillCalendar();

        registerUIListeners();
    }

    ////// Private utility methods

    private void fillCalendar() {
        MultiSelectListPreference enabled_calendar_pref = findPreference("enabled_calendars");
        if (enabled_calendar_pref == null) {
            return;
        }

        // FIXME: Exception
        List<CalendarItem> calendarItemList = null;
        try {
            calendarItemList = CalendarManager.loadCalendars(requireContext());
        } catch (SecurityException ignored) {

        }

        List<String> entries = new ArrayList<>();
        List<String> values = new ArrayList<>();

        if (calendarItemList != null) {
            for (CalendarItem calendar : calendarItemList) {
                entries.add(calendar.displayName);
                values.add(Integer.toString(calendar.id));
            }
        }

        enabled_calendar_pref.setEntries(entries.toArray(new CharSequence[0]));
        enabled_calendar_pref.setEntryValues(values.toArray(new CharSequence[0]));
    }

    /**
     * Register all UI listeners (UI Interactivity)
     */
    private void registerUIListeners() {
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
