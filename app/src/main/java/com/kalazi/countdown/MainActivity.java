package com.kalazi.countdown;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;
import com.google.android.material.navigation.NavigationView;
import com.kalazi.countdown.permissions.PermissionViewModel;

import static com.kalazi.countdown.permissions.PermissionManager.PERM_REQUEST_CODE;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration barConfiguration;
    private NavController navController;
    private PermissionViewModel permissionViewModel;

    ////// Overrides

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateTheme();

        setContentView(R.layout.activity_main);

        allowToolbar();
        setupNavigation();

        permissionViewModel = new ViewModelProvider(this).get(PermissionViewModel.class);
    }

    /**
     * Used for inflating the action bar menu
     * -> add items to the action bar if it is present.
     *
     * @param menu Action Bar Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Used for automatic destination return handling (back arrow on the left side of the action bar)
     */
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, barConfiguration) || super.onSupportNavigateUp();
    }

    /**
     * Used for hiding the keyboard on action bar icon click<br>
     * <b>NOTE: return false from onMenuItemClickListener(s) to apply this after the listener is processed</b>
     *
     * @param item the clicked menu item
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERM_REQUEST_CODE) {
            for (int res : grantResults) {
                if (res == PackageManager.PERMISSION_DENIED) {
                    permissionViewModel.setPermsGranted(false);
                    return;
                }
            }
            permissionViewModel.setPermsGranted(true);
        }
    }

    ////// Private utility methods

    /**
     * Update app theme depending on the saved persistent preference
     */
    private void updateTheme() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("theme_dark", true)) {
            setTheme(R.style.AppThemeDark_NoActionBar);
        }
    }

    /**
     * Enable toolbar integration
     */
    private void allowToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Setup app navigation using the navigation drawer
     */
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
