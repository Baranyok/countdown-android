package com.kalazi.countdown;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration barConfiguration;
    private NavController navController;

    ////// Overrides

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateTheme();

        setContentView(R.layout.activity_main);

        allowToolbar();
        setupNavigation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        // this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, barConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // hide the keyboard on action bar icon click
        // NOTE: return false from onMenuItemClickListener(s) to apply this after the listener is processed
        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        return super.onOptionsItemSelected(item);
    }

    ////// private utility functions
    private void updateTheme() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("theme_dark", true)) {
            setTheme(R.style.AppThemeDark_NoActionBar);
        }
    }

    private void allowToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupNavigation() {
        // Build an application bar configuration
        // Passing each menu ID as a set of Ids because each
        // menu should be considered a top level destination.
        barConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_settings, R.id.nav_countdowns)
                .setOpenableLayout(findViewById(R.id.drawer_layout))
                .build();

        // find the nav controller
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // find the navigation view
        NavigationView navigationView = findViewById(R.id.nav_view);

        // setup navigation
        NavigationUI.setupActionBarWithNavController(this, navController, barConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

}
