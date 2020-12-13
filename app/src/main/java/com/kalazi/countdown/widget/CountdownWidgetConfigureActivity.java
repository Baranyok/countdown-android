package com.kalazi.countdown.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.kalazi.countdown.R;
import com.kalazi.countdown.countdowns.CountdownsRVAdapter;
import com.kalazi.countdown.countdowns.CountdownsViewModel;

/**
 * The configuration screen for the {@link CountdownWidget CountdownWidget} AppWidget.
 */
public class CountdownWidgetConfigureActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "com.kalazi.countdown.widget.CountdownWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            TextView countdownIdView = v.findViewById(R.id.ci_countdown_id);

            final Context context = CountdownWidgetConfigureActivity.this;

            // When the button is clicked, store the string locally
            int countdownId = Integer.parseInt(countdownIdView.getText().toString());
            saveTitlePref(context, mAppWidgetId, countdownId);

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            CountdownWidget.updateCountdownWidget(context, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };
    private CountdownsViewModel viewModel;

    public CountdownWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, int countdownId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId, countdownId);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static int loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getInt(PREF_PREFIX_KEY + appWidgetId, -1);
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        updateTheme();
        setContentView(R.layout.countdown_widget_configure);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        // setup other things

        viewModel = new ViewModelProvider(this).get(CountdownsViewModel.class);

        initRecyclerView();
    }

    ////// Private utility methods

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.widget_recycler_view);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        CountdownsRVAdapter rvAdapter = new CountdownsRVAdapter();
        rvAdapter.setItemOnClickListener(mOnClickListener);
        recyclerView.setAdapter(rvAdapter);

        viewModel.getCountdowns().observe(this, rvAdapter::updateDataset);
    }

    private void updateTheme() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("theme_dark", true)) {
            setTheme(R.style.AppThemeDark_NoActionBar);
        }
    }
}

